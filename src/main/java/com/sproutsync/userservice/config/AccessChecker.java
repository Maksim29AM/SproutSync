package com.sproutsync.userservice.config;

import com.sproutsync.userservice.model.AccessRequest;
import com.sproutsync.userservice.model.User;
import com.sproutsync.userservice.service.AccessRequestService;
import com.sproutsync.userservice.service.UserService;
import com.sproutsync.userservice.util.AccessStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accessChecker")
public class AccessChecker {

    private final UserService userService;
    private final AccessRequestService request;

    public AccessChecker(UserService userService, AccessRequestService request) {
        this.userService = userService;
        this.request = request;
    }

    public boolean hasApprovedAccess(String email, Long groupId) {
        User isExist = userService.findByEmail(email);
        if (isExist == null) {
            return false;
        }
//        System.out.println("User --->  "+isExist);
        Optional<AccessRequest> accessRequestOpt = request.findByUserAndGroup(isExist.getId(), groupId);
//        if(accessRequestOpt.get().getAccessStatus() != AccessStatus.APPROVED){
//            System.out.println(isExist.getEmail()+" not access for this group!");
//        }
        return accessRequestOpt.isPresent() && accessRequestOpt.get().getAccessStatus() == AccessStatus.APPROVED;
    }
}
