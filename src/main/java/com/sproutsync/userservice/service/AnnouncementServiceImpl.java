package com.sproutsync.userservice.service;

import com.sproutsync.userservice.dto.AnnouncementUpdateDto;
import com.sproutsync.userservice.model.Announcement;
import com.sproutsync.userservice.model.Group;
import com.sproutsync.userservice.repository.AnnouncementRepository;
import com.sproutsync.userservice.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository repository;
    private final GroupRepository groupRepository;

    public AnnouncementServiceImpl(AnnouncementRepository repository, GroupRepository groupRepository) {
        this.repository = repository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement) {
        return repository.save(announcement);
    }

    @Override
    public Announcement updateAnnouncement(Long id, AnnouncementUpdateDto updateDto) {
        Announcement updateAnnouncement = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement with id:" + id + " not found"));
        if (updateDto.getTitle() != null) {
            updateAnnouncement.setTitle(updateDto.getTitle());
        }
        if (updateDto.getMessage() != null) {
            updateAnnouncement.setMessage(updateDto.getMessage());
        }
        if (updateDto.getPhoto() != null) {
            updateAnnouncement.setPhoto(updateDto.getPhoto());
        }
        return repository.save(updateAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        Announcement isExist = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement with id:" + id + " not found"));
        repository.deleteById(isExist.getId());
    }

    @Override
    public Optional<Announcement> getAnnouncement(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        return repository.findAll();
    }

    @Override
    public List<Announcement> findByGroup_Id(Long groupId) {
        Group group  = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id:" + groupId + " not found"));

        return repository.findAllByGroup(group);
    }
}
