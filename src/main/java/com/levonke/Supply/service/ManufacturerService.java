package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.web.model.ManufacturerRequest;

import java.util.List;

public interface ManufacturerService {
	List<Manufacturer> getManufacturers(Integer page, Integer size);
	Manufacturer createManufacturer(ManufacturerRequest manufacturerRequest);
	Manufacturer getManufacturerById(Integer manufacturerId);
	Manufacturer updateManufacturerById(Integer manufacturerId, ManufacturerRequest manufacturerRequest);
	void deleteManufacturerById(Integer manufacturerId);
	
	List<Component> getComponents(Integer manufacturerId);
}
