package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.AccessRequestRepository;
import com.sproutsync.userservice.util.AccessStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccessRequestServiceImpl implements AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;
    private final UserService userService;
    private final GroupService groupService;

    public AccessRequestServiceImpl(AccessRequestRepository accessRequestRepository, UserService userService, GroupService groupService) {
        this.accessRequestRepository = accessRequestRepository;
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    @Transactional
    public AccessRequest createRequest(String email, Long groupId) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        Group group = groupService.getGroupById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        Optional<AccessRequest> existingRequest = accessRequestRepository.findByParentIdAndGroupId(user.getId(), groupId);
        if (existingRequest.isPresent()) {
            throw new IllegalArgumentException("Access request already exists");
        }
        AccessRequest request = new AccessRequest();
        request.setParent(user);
        request.setGroup(group);
        request.setAccessStatus(AccessStatus.PENDING);
        return accessRequestRepository.save(request);
    }


    @Override
    @Transactional
    public AccessRequest updateRequestStatus(Long id, AccessStatus status) {
        AccessRequest request = accessRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccessRequest with id " + id + " not found"));
        if (request.getAccessStatus() == status) {
            throw new IllegalStateException("Request already has status: " + status);
        }
        request.setAccessStatus(status);
        return accessRequestRepository.save(request);
    }



    @Override
    public Optional<AccessRequest> findById(Long id) {
        return accessRequestRepository.findById(id);
    }

    @Override
    public List<AccessRequest> findRequestsByParentId(Long parentId) {
        User user = userService.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("Parent not found with id: " + parentId));
        return accessRequestRepository.findAllByParent(user);
    }


    @Override
    public List<AccessRequest> findRequestsByGroupId(Long groupId) {
        Group group = groupService.getGroupById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        return accessRequestRepository.findAllByGroup(group);
    }


    @Override
    public List<AccessRequest> findAllByStatus(String statusRaw) {
        AccessStatus status;
        try {
            status = AccessStatus.valueOf(statusRaw.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Invalid status: " + statusRaw);
        }

        List<AccessRequest> requests = accessRequestRepository.findAllByAccessStatus(status);

        if (requests.isEmpty()) {
            throw new EntityNotFoundException("No requests with status: " + statusRaw);
        }

        return requests;
    }
    @Override
    public Optional<AccessRequest> findByUserAndGroup(Long userId, Long groupId) {
        return accessRequestRepository.findByParentIdAndGroupId(userId, groupId);
    }
}
