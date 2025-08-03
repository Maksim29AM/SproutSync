package com.sproutsync.userservice.controller;

import com.sproutsync.userservice.dto.AccessRequestDto;
import com.sproutsync.userservice.mapper.AccessRequestMapper;
import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.service.AccessRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/access-requests")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    public AccessRequestController(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }


    @GetMapping("/parent/{id}")
    public List<AccessRequestDto> getRequestsForParent(@PathVariable Long id) {
        return accessRequestService.findRequestsByParentId(id).stream()
                .map(AccessRequestMapper::toDto)
                .toList();
    }

    @GetMapping("/group/{id}")
    public List<AccessRequestDto> getRequestsByGroup(@PathVariable Long id) {
        return accessRequestService.findRequestsByGroupId(id).stream()
                .map(AccessRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<AccessRequestDto> getRequestsByStatus(@RequestParam String status) {
        List<AccessRequest> requests = accessRequestService.findAllByStatus(status);
        return requests.stream()
                .map(AccessRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public AccessRequestDto createRequest(@RequestBody AccessRequestDto dto, Authentication authentication) {
        AccessRequest saved = accessRequestService.createRequest(authentication.getName(), dto.getGroupId());
        return AccessRequestMapper.toDto(saved);
    }

    @PutMapping("/{id}/status")
    public AccessRequestDto updateRequest(@PathVariable Long id, @RequestParam String status) {
        AccessRequest updated = accessRequestService.updateRequestStatus(id, status);
        return AccessRequestMapper.toDto(updated);
    }


}


