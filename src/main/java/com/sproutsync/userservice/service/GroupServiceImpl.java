package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Long id, Group group) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
        existingGroup.setName(group.getName());
        existingGroup.setDescription(group.getDescription());
        existingGroup.setMainFoto(group.getMainFoto());
        return groupRepository.save(existingGroup);
    }

    @Override
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group not found");
        }
        groupRepository.deleteById(id);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> getGroupById(Long id) {
        return groupRepository.findById(id);
    }
}
