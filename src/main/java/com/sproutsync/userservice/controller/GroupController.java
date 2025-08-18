package com.sproutsync.userservice.controller;


import com.sproutsync.userservice.dto.groupDto.request.GroupRequestDto;
import com.sproutsync.userservice.dto.groupDto.response.GroupResponseDto;
import com.sproutsync.userservice.mapper.GroupMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Groups", description = "CRUD operations for groups")
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    public final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Create group", description = "Creates a new group")
    @PostMapping
    public GroupResponseDto create(@RequestBody @Valid GroupRequestDto dto) {
        Group saved = groupService.createGroup(GroupMapper.toEntity(dto));
        return GroupMapper.toGroupDto(saved);
    }

    @Operation(summary = "Update group", description = "Updates an existing group by ID")
    @PutMapping("/{id}")
    public GroupResponseDto update(@PathVariable Long id, @RequestBody @Valid GroupRequestDto dto) {
        Group updated = groupService.updateGroup(id, GroupMapper.toEntity(dto));
        return GroupMapper.toGroupDto(updated);
    }

    @Operation(summary = "Delete group", description = "Deletes a group by ID")
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable long id) {
        groupService.deleteGroup(id);
    }

    @Operation(summary = "List all groups", description = "Returns all groups")
    @GetMapping
    public List<GroupResponseDto> getAllGroups() {
        return groupService.getAllGroups()
                .stream()
                .map(GroupMapper::toGroupDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get group by ID", description = "Returns a group by its ID")
    @GetMapping("/{id}")
    public GroupResponseDto getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(GroupMapper::toGroupDto)
                .orElseThrow(()->new EntityNotFoundException("Group not found"));
    }

}
