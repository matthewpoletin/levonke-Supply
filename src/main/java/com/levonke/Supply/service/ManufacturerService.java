package com.levonke.Supply.service;

import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.web.model.ManufacturerRequest;

import java.util.List;

public interface ManufacturerService {
	List<Manufacturer> getManufacturers(Integer page, Integer size);
	Manufacturer create(ManufacturerRequest manufacturerRequest);
	Manufacturer read(Integer manufacturerId);
	Manufacturer update(Integer manufacturerId, ManufacturerRequest manufacturerRequest);
	void delete(Integer manufacturerId);
}
