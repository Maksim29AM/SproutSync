package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.groupDto.request.GroupRequestDto;
import com.sproutsync.userservice.dto.groupDto.response.GroupResponseDto;
import com.sproutsync.userservice.model.Group;

import java.util.Collections;

public class GroupMapper {

    private GroupMapper() {}

    public static Group toEntity(GroupRequestDto dto) {
        return new Group(null, dto.getName(), dto.getDescription(), dto.getMainFoto(), Collections.emptyList());
    }

    public static GroupResponseDto toGroupDto(Group group) {
        GroupResponseDto dto = new GroupResponseDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setMainFoto(group.getMainFoto());
        return dto;
    }
}
