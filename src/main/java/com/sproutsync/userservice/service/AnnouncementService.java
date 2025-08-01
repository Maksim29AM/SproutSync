package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.model.Announcement;

import java.util.List;

public interface AnnouncementService {

    Announcement createAnnouncement(Long groupId, Announcement announcement);

    Announcement updateAnnouncement(Long groupId, Long announcementId, AnnouncementUpdateDto announcementDto);

    void deleteAnnouncement(Long groupId, Long activityId);

    Announcement getAnnouncementByGroup(Long groupId, Long announcementId);

    List<Announcement> getAllAnnouncementsByGroupId(Long groupId);

}
