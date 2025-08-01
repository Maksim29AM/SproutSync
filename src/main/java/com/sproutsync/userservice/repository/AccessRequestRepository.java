package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.AccessRequest;

import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.util.AccessStatus;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface AccessRequestRepository extends JpaRepository<AccessRequest, Long> {

    List<AccessRequest> findAllByParent(User parent);

    List<AccessRequest> findAllByGroup(Group group);

    List<AccessRequest> findAllByAccessStatus(AccessStatus status);

    Optional<AccessRequest> findByParentIdAndGroupId(Long userId, Long groupId);
}

