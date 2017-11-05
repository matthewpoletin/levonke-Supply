package com.levonke.Supply.repository;

import com.levonke.Supply.domain.Manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository
		extends JpaRepository<Manufacturer, Integer> {
}
