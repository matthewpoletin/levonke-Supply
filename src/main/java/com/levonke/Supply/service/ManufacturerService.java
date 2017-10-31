package com.levonke.Supply.service;

import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.web.model.ManufacturerRequest;

public interface ManufacturerService {
	
	Iterable<Manufacturer> getManufacturers();
	Manufacturer create(ManufacturerRequest manufacturerRequest);
	Manufacturer read(Integer manufacturerId);
	Manufacturer update(Integer manufacturerId, ManufacturerRequest manufacturerRequest);
	void delete(Integer manufacturerId);
	
}
