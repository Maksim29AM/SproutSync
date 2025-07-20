package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.model.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    Announcement createAnnouncement(Announcement announcement);

    Announcement updateAnnouncement(Long id, AnnouncementUpdateDto announcementDto);

    void deleteAnnouncement(Long id);

    Optional<Announcement> getAnnouncement(Long id);

    List<Announcement> getAllAnnouncements();

    List<Announcement> findByGroup_Id(Long groupId);

}
