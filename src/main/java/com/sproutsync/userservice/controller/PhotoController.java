package com.sproutsync.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sproutsync.userservice.dto.PhotoDto;
import com.sproutsync.userservice.dto.PhotoUploadDto;
import com.sproutsync.userservice.mapper.PhotoMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.Photo;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.PhotoService;
import com.sproutsync.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups/{idGroup}/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final GroupService groupService;
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoDto uploadPhoto(@PathVariable Long idGroup, @RequestParam("file") MultipartFile file, @RequestParam("uploadDto") String uploadDtoJson, Authentication authentication) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PhotoUploadDto uploadDto = objectMapper.readValue(uploadDtoJson, PhotoUploadDto.class);
        Group group = groupService.getGroupById(idGroup)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + idGroup));
        User user = userService.findByEmail(authentication.getName());
        Photo saved = photoService.uploadPhoto(file, idGroup, uploadDto, user.getEmail());
        return PhotoMapper.toDto(saved);
    }

    @GetMapping()
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<PhotoDto> getGroupPhotos(@PathVariable Long idGroup) {
        return photoService.getAllPhotosByGroupId(idGroup)
                .stream()
                .map(PhotoMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{photoId}")
    public void deletePhoto(@PathVariable Long idGroup, @PathVariable Long photoId) {
        photoService.deletePhoto(idGroup, photoId);
    }
}
