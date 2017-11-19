package com.levonke.Supply.repository;

import com.levonke.Supply.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComponentRepository
	extends JpaRepository<Component, Integer> {
	
	Component findComponentByUuid(UUID uuid);
	
}
