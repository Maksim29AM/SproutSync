package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.photoDto.request.PhotoUploadRequestDto;
import com.sproutsync.userservice.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    Photo uploadPhoto(MultipartFile file, Long groupId, PhotoUploadRequestDto uploadDto, String uploaderEmail) ;

    void deletePhoto(Long groupId, Long photoId);

    List<Photo> getAllPhotosByGroupId(Long idGroup);

}