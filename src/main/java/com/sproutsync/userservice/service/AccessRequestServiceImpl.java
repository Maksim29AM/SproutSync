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

    public AccessRequestServiceImpl(AccessRequestRepository accessRequestRepository) {
        this.accessRequestRepository = accessRequestRepository;
    }

    @Override
    @Transactional
    public AccessRequest createRequest(AccessRequest request) {
        if (request.getId() != null) {
            throw new IllegalArgumentException("Request already exists");
        }
        request.setAccessStatus(AccessStatus.PENDING);
        return accessRequestRepository.save(request);
    }

    @Override
    @Transactional
    public AccessRequest updateRequestStatus(Long id, AccessStatus newStatus) {
        AccessRequest request = accessRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccessRequest with id " + id + " not found"));

        if (request.getAccessStatus() == newStatus) {
            throw new IllegalStateException("Request already has status: " + newStatus);
        }
        request.setAccessStatus(newStatus);
        return accessRequestRepository.save(request);
    }

    @Override
    public Optional<AccessRequest> findById(Long id) {
        return accessRequestRepository.findById(id);
    }

    @Override
    public List<AccessRequest> findRequestsByParent(User parent) {
        return accessRequestRepository.findAllByParent(parent);
    }

    @Override
    public List<AccessRequest> findRequestsByGroup(Group group) {
        return accessRequestRepository.findAllByGroup(group);
    }

    @Override
    public List<AccessRequest> findAllByStatus(AccessStatus status) {
        return accessRequestRepository.findAllByAccessStatus(status);
    }

    @Override
    public List<AccessRequest> findAll() {
        return accessRequestRepository.findAll();
    }

    @Override
    public Optional<AccessRequest> findByUserAndGroup(Long userId, Long groupId) {
        return accessRequestRepository.findByParentIdAndGroupId(userId, groupId);
    }
}
