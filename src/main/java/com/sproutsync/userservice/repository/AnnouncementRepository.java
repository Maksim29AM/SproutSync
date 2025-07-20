package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByGroup(Group group);
}
