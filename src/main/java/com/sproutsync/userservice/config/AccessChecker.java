package com.sproutsync.userservice.config;

import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.repository.GroupRepository;
import com.sproutsync.userservice.service.AccessRequestService;
import com.sproutsync.userservice.service.UserService;
import com.sproutsync.userservice.util.AccessStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accessChecker")
public class AccessChecker {

    private final UserService userService;
    private final AccessRequestService request;
    private final GroupRepository groupRepository;

    public AccessChecker(UserService userService, AccessRequestService request, GroupRepository groupRepository) {
        this.userService = userService;
        this.request = request;
        this.groupRepository = groupRepository;
    }

    public boolean hasApprovedAccess(String email, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id: " + groupId + " not found"));
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return true;
        }

        Optional<AccessRequest> accessRequestOpt = request.findByUserAndGroup(user.getId(), groupId);
        boolean hasAccess = accessRequestOpt.isPresent() && accessRequestOpt.get().getAccessStatus() == AccessStatus.APPROVED;

        if (!hasAccess) {
            System.out.println("Access denied: User '" + email + "' does not have APPROVED access to group with id " + groupId);
        }
        return hasAccess;
    }
}
