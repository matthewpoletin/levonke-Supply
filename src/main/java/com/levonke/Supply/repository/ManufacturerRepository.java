package com.levonke.Supply.repository;

import com.levonke.Supply.domain.Manufacturer;

import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository
		extends CrudRepository<Manufacturer, Integer> {
}
