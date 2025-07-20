package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.AccessRequestDto;
import com.sproutsync.userservice.mapper.AccessRequestMapper;
import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.AccessRequestService;
import com.sproutsync.userservice.service.GroupService;
import com.sproutsync.userservice.service.UserService;
import com.sproutsync.userservice.util.AccessStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;
    private final GroupService groupService;
    private final UserService userService;

    public AccessRequestController(AccessRequestService accessRequestService, GroupService groupService, UserService userService) {
        this.accessRequestService = accessRequestService;
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping("/parent/{id}")
    public List<AccessRequestDto> requestsParent(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() -> new EntityNotFoundException("Parent not found"));
        return accessRequestService.findRequestsByParent(user).stream().map(AccessRequestMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/group/{id}")
    public List<AccessRequestDto> requestsByGroup(@PathVariable Long id) {
        Group group = groupService.getGroupById(id).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        return accessRequestService.findRequestsByGroup(group).stream().map(AccessRequestMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping()
    public List<AccessRequestDto> requestsByStatus(@RequestParam String status) {
        try {
            AccessStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new EntityNotFoundException("Invalid status: " + status);
        }
        List<AccessRequest> request = accessRequestService.findAllByStatus(AccessStatus.valueOf(status.toUpperCase()));
        if (request.isEmpty()) {
            throw new EntityNotFoundException("No requests with status: " + status);
        }

        return request.stream().map(AccessRequestMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping()
    public AccessRequestDto createRequest(@RequestBody AccessRequestDto dto, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Group group = groupService.getGroupById(dto.getGroupId()).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        AccessRequest saved = accessRequestService.createRequest(AccessRequestMapper.toEntity(user, group));
        return AccessRequestMapper.toDto(saved);
    }

    @PutMapping("/{id}/status")
    public AccessRequestDto updateRequest(@PathVariable Long id, @RequestParam String status) {

        AccessStatus newStatus;
        try {
            newStatus = AccessStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Invalid status: " + status);
        }

        AccessRequest request = accessRequestService.findById(id).orElseThrow(() -> new EntityNotFoundException("Request not found"));

        if (request.getAccessStatus() == newStatus) {
            throw new IllegalArgumentException("This request already has status: " + status);
        }

        AccessRequest saved = accessRequestService.updateRequestStatus(id, newStatus);
        return AccessRequestMapper.toDto(saved);
    }

}


