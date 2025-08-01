package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByGroupId(Long groupId);

    Optional<Announcement> findByGroupIdAndId(Long groupId, Long id);
}
