package com.sproutsync.userservice.controller;


import com.sproutsync.userservice.dto.groupDto.request.GroupRequestDto;
import com.sproutsync.userservice.dto.groupDto.response.GroupResponseDto;
import com.sproutsync.userservice.mapper.GroupMapper;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    public final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public GroupResponseDto create(@RequestBody @Valid GroupRequestDto dto) {
        Group saved = groupService.createGroup(GroupMapper.toEntity(dto));
        return GroupMapper.toGroupDto(saved);
    }

    @PutMapping("/{id}")
    public GroupResponseDto update(@PathVariable Long id, @RequestBody @Valid GroupRequestDto dto) {
        Group updated = groupService.updateGroup(id, GroupMapper.toEntity(dto));
        return GroupMapper.toGroupDto(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable long id) {
        groupService.deleteGroup(id);
    }

    @GetMapping
    public List<GroupResponseDto> getAllGroups() {
        return groupService.getAllGroups()
                .stream()
                .map(GroupMapper::toGroupDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GroupResponseDto getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(GroupMapper::toGroupDto)
                .orElseThrow(()->new EntityNotFoundException("Group not found"));
    }

}
