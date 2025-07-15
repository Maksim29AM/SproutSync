package com.sproutsync.userservice.service;

import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.util.AccessStatus;

import java.util.List;
import java.util.Optional;

public interface AccessRequestService {

    AccessRequest createRequest(AccessRequest request);

    AccessRequest updateRequestStatus(Long id, AccessStatus newStatus);

    Optional<AccessRequest> findById(Long id);

    List<AccessRequest> findRequestsByParent(User parent);

    List<AccessRequest> findRequestsByGroup(Group group);

    List<AccessRequest> findAllByStatus(AccessStatus status);

    List<AccessRequest> findAll();

}
