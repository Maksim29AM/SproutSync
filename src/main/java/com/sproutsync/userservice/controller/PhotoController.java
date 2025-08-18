package com.sproutsync.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sproutsync.userservice.dto.photoDto.response.PhotoResponseDto;
import com.sproutsync.userservice.dto.photoDto.request.PhotoUploadRequestDto;
import com.sproutsync.userservice.mapper.PhotoMapper;
import com.sproutsync.userservice.model.Photo;
import com.sproutsync.userservice.service.PhotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Photos", description = "Photo upload and management for groups")
@RestController
@RequestMapping("/api/groups/{idGroup}/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @Operation(summary = "Upload photo", description = "Uploads a photo to the specified group (multipart/form-data)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoResponseDto uploadPhoto(@PathVariable Long idGroup, @RequestParam("file") MultipartFile file, @RequestParam("uploadDto") String uploadDtoJson, Authentication authentication) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PhotoUploadRequestDto uploadDto = mapper.readValue(uploadDtoJson, PhotoUploadRequestDto.class);
        Photo saved = photoService.uploadPhoto(file, idGroup, uploadDto, authentication.getName());
        return PhotoMapper.toDto(saved);
    }

    @Operation(summary = "List group photos", description = "Returns all photos for the specified group")
    @GetMapping()
    @PreAuthorize("@accessChecker.hasApprovedAccess(authentication.name, #idGroup)")
    public List<PhotoResponseDto> getGroupPhotos(@PathVariable Long idGroup) {
        return photoService.getAllPhotosByGroupId(idGroup).stream().map(PhotoMapper::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Delete photo", description = "Deletes a photo by ID for the specified group")
    @DeleteMapping("/{photoId}")
    public void deletePhoto(@PathVariable Long idGroup, @PathVariable Long photoId) {
        photoService.deletePhoto(idGroup, photoId);
    }
}
