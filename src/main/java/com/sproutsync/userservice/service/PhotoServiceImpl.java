package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.PhotoUploadDto;
import com.sproutsync.userservice.mapper.PhotoMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Photo;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.GroupRepository;
import com.sproutsync.userservice.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final GroupRepository groupRepository;
    private final S3Service s3Service;
    private final UserService userService;

    public PhotoServiceImpl(PhotoRepository photoRepository, GroupRepository groupRepository, S3Service s3Service, UserService userService) {
        this.photoRepository = photoRepository;
        this.groupRepository = groupRepository;
        this.s3Service = s3Service;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Photo uploadPhoto(MultipartFile file, Long groupId, PhotoUploadDto uploadDto, String uploaderEmail) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
        User user = userService.findByEmail(uploaderEmail);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + uploaderEmail);
        }
        String url;
        try {
            url = s3Service.uploadFileAndGetUrl(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        Photo photo = PhotoMapper.toEntity(uploadDto, group, user);
        photo.setUrl(url);
        return photoRepository.save(photo);
    }


    @Override
    @Transactional
    public void deletePhoto(Long groupId, Long photoId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
        Photo photo = photoRepository.findByIdAndGroupId(photoId, groupId)
                .orElseThrow(() -> new EntityNotFoundException("Photo with id:" + photoId + " not found in group:" + group));

        s3Service.deleteFile(photo.getUrl());
        photoRepository.deleteByIdAndGroupId(photoId, groupId);
    }

    @Override
    public List<Photo> getAllPhotosByGroupId(Long idGroup) {
        Group group = groupRepository.findById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + idGroup + " not found"));
        return photoRepository.findByGroupId(group.getId());
    }
}