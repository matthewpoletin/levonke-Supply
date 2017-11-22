package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ComponentRepository;
import com.levonke.Supply.web.model.ComponentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
public class ComponentServiceImpl implements ComponentService {
	
	private final ComponentRepository componentRepository;
	
	private final ManufacturerServiceImpl manufacturerService;
	
	@Autowired
	public ComponentServiceImpl(ComponentRepository componentRepository, ManufacturerServiceImpl manufacturerService) {
		this.componentRepository = componentRepository;
		this.manufacturerService = manufacturerService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Component> getComponents(Integer page, Integer size) {
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 25;
		}
		return componentRepository.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@Override
	@Transactional
	public Component createComponent(ComponentRequest componentRequest) {
		Component component = componentRepository.findComponentByManufacturerPartNumber(componentRequest.getManufacturerPartNumber());
		if (component == null) {
			component = new Component()
				.setManufacturerPartNumber(componentRequest.getManufacturerPartNumber());
			component.setUuid(UUID.randomUUID());
		}
		return componentRepository.save(component);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Component getComponentById(Integer componentId) {
		return componentRepository.findById(componentId)
			.orElseThrow(() -> new EntityNotFoundException("Component '{" + componentId + "}' not found"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Component getComponentByUUID(UUID uuid) {
		return componentRepository.findComponentByUuid(uuid);
	}
	
	@Override
	@Transactional
	public Component updateComponentById(Integer componentId, ComponentRequest componentRequest) {
		Component component = this.getComponentById(componentId);
		component.setManufacturerPartNumber(componentRequest.getManufacturerPartNumber() != null ? componentRequest.getManufacturerPartNumber() : component.getManufacturerPartNumber());
		return componentRepository.save(component);
	}
	
	@Override
	@Transactional
	public void deleteComponentById(Integer componentId) {
		componentRepository.deleteById(componentId);
	}
	
	@Override
	@Transactional
	public void setManufacturer(Integer componentId, @NotNull Integer manufacturerId) {
		if (manufacturerId != null) {
			Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);
			Component component = this.getComponentById(componentId);
			component.setManufacturer(manufacturer);
			componentRepository.save(component);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Manufacturer getManufacturer(Integer componentId) {
		return this.getComponentById(componentId).getManufacturer();
	}
	
}
