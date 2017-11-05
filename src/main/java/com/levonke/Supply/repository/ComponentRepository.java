package com.levonke.Supply.repository;

import com.levonke.Supply.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository
	extends JpaRepository<Component, Integer> {
}
