package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.accessRequestDto.request.AccessCreateRequestDto;
import com.sproutsync.userservice.dto.accessRequestDto.request.AccessUpdateRequestDto;
import com.sproutsync.userservice.dto.accessRequestDto.response.AccessResponseDto;
import com.sproutsync.userservice.mapper.AccessRequestMapper;
import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.service.AccessRequestService;
import com.sproutsync.userservice.util.AccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@Tag(name = "Access Requests", description = "CRUD operations for access requests to groups")
@RestController
@RequestMapping("/api/access-requests")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    public AccessRequestController(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }


    @Operation(summary = "Get requests for parent", description = "Returns all access requests created by a parent")
    @GetMapping("/parent/{id}")
    public List<AccessResponseDto> getRequestsForParent(@PathVariable Long id) {
        return accessRequestService.findRequestsByParentId(id).stream()
                .map(AccessRequestMapper::toDto)
                .toList();
    }

    @Operation(summary = "Get requests for group", description = "Returns all access requests for a given group")
    @GetMapping("/group/{id}")
    public List<AccessResponseDto> getRequestsByGroup(@PathVariable Long id) {
        return accessRequestService.findRequestsByGroupId(id).stream()
                .map(AccessRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get requests by status", description = "Returns all access requests filtered by status")
    @GetMapping
    public List<AccessResponseDto> getRequestsByStatus(@RequestParam String status) {
        List<AccessRequest> requests = accessRequestService.findAllByStatus(status);
        return requests.stream()
                .map(AccessRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Create access request", description = "Creates a new access request for a group")
    @PostMapping
    public AccessResponseDto createRequest(@RequestBody AccessCreateRequestDto dto, Authentication authentication) {
        AccessRequest saved = accessRequestService.createRequest(authentication.getName(), dto.getGroupId());
        return AccessRequestMapper.toDto(saved);
    }

    @Operation(summary = "Update access request status", description = "Updates the status of an access request")
    @PutMapping("/status")
    public AccessResponseDto updateRequest(@RequestBody AccessUpdateRequestDto dto) {
        AccessRequest updated = accessRequestService.updateRequestStatus(
                dto.getId(),
                AccessStatus.fromString(dto.getAccessStatus()));
        return AccessRequestMapper.toDto(updated);
    }


}


