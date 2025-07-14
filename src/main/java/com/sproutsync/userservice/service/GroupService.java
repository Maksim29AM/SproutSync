package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    Group createGroup(Group group);

    Group updateGroup(Long id, Group group);

    void deleteGroup(Long id);

    List<Group> getAllGroups();

    Optional <Group> getGroupById(Long id);
}
