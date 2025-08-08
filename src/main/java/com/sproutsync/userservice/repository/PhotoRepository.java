package com.sproutsync.userservice.repository;

import com.sproutsync.userservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByGroupId(Long groupId);

    Optional<Photo> findByIdAndGroupId(Long id, Long groupId);

    void deleteByIdAndGroupId(Long id, Long groupId);
}