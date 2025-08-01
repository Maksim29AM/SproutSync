package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.repository.AnnouncementRepository;
import com.sproutsync.userservice.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final GroupRepository groupRepository;

    public AnnouncementServiceImpl(AnnouncementRepository repository, GroupRepository groupRepository) {
        this.announcementRepository = repository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Announcement createAnnouncement(Long groupId,Announcement announcement) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id:" + groupId + " not found"));
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement updateAnnouncement(Long groupId, Long announcementId, AnnouncementUpdateDto updateDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id:" + groupId + " not found"));
        Announcement updateAnnouncement = announcementRepository.findByGroupIdAndId(group.getId(), announcementId)
                .orElseThrow(() -> new EntityNotFoundException("Announcement with id:" + announcementId + " not found"));
        if (updateDto.getTitle() != null) {
            updateAnnouncement.setTitle(updateDto.getTitle());
        }
        if (updateDto.getMessage() != null) {
            updateAnnouncement.setMessage(updateDto.getMessage());
        }
        if (updateDto.getPhoto() != null) {
            updateAnnouncement.setPhoto(updateDto.getPhoto());
        }
        return announcementRepository.save(updateAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long groupId,Long announcementId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id:" + groupId + " not found"));
        Announcement existing = announcementRepository.findByGroupIdAndId(group.getId(), announcementId)
                .orElseThrow(() -> new EntityNotFoundException("Announcement with id:" + announcementId + " not found"));
        announcementRepository.deleteById(existing.getId());
    }

    @Override
    public Announcement getAnnouncementByGroup(Long groupId, Long announcementId) {
        return announcementRepository.findByGroupIdAndId(groupId, announcementId)
                .orElseThrow(() -> new EntityNotFoundException("Announcement with id: " + announcementId + " not found"));
    }


    @Override
    public List<Announcement> getAllAnnouncementsByGroupId(Long groupId) {
        return announcementRepository.findAllByGroupId(groupId);
    }
}
