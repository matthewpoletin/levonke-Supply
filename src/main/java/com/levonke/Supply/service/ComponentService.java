package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.web.model.ComponentRequest;

import java.util.List;
import java.util.UUID;

public interface ComponentService {
	List<Component> getComponents(Integer page, Integer size);
	Component createComponent(ComponentRequest componentRequest);
	Component getComponentById(Integer componentId);
	Component getComponentByUUID(UUID uuid);
	Component updateComponentById(Integer componentId, ComponentRequest componentRequest);
	void deleteComponentById(Integer componentId);
	
	void setManufacturer(Integer componentId, Integer manufacturerId);
	Manufacturer getManufacturer(Integer componentId);
}
