package com.schedule.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schedule.model.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel,Long> {
	Optional<ImageModel> findByName(String name);
	Optional<ImageModel> findAllByName(String name);
}
