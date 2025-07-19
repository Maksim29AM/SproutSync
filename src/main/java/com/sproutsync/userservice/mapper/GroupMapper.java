package com.sproutsync.userservice.mapper;

import com.sproutsync.userservice.dto.GroupDto;
import com.sproutsync.userservice.model.Group;

import java.util.Collections;

public class GroupMapper {

    private GroupMapper() {}

    public static Group toEntity(GroupDto dto) {
        return new Group(null, dto.getName(), dto.getDescription(), dto.getMainFoto(), Collections.emptyList());
    }

    public static GroupDto toGroupDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setMainFoto(group.getMainFoto());
        return dto;
    }
}
