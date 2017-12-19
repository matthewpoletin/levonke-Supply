package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.web.model.ManufacturerRequest;

import org.springframework.data.domain.Page;

public interface ManufacturerService {
	Page<Manufacturer> getManufacturers(Integer page, Integer size);
	Manufacturer createManufacturer(ManufacturerRequest manufacturerRequest);
	Manufacturer getManufacturerById(Integer manufacturerId);
	Manufacturer updateManufacturerById(Integer manufacturerId, ManufacturerRequest manufacturerRequest);
	void deleteManufacturerById(Integer manufacturerId);
	
	Page<Component> getComponents(Integer manufacturerId, Integer page, Integer size);
}
