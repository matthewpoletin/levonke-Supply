package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.repository.ComponentRepository;
import com.levonke.Supply.web.model.ComponentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;

@Service
public class ComponentServiceImpl implements ComponentService {
	
	private final ComponentRepository componentRepository;
	
	@Autowired
	public ComponentServiceImpl(ComponentRepository componentRepository) {
		this.componentRepository = componentRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Component> getComponents() {
		return componentRepository.findAll();
	}
	
	@Override
	@Transactional
	public Component create(ComponentRequest componentRequest) {
		Component component = new Component()
			.setManufacturerPartNumber(componentRequest.getManufacturerPartNumber());
		return componentRepository.save(component);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Component read(Integer componentId) {
		Component component = componentRepository.findOne(componentId);
		if (component == null) {
			throw new EntityNotFoundException("Component '{" + componentId + "}' not found");
		}
		return component;
	}
	
	@Override
	@Transactional
	public Component update(Integer componentId, ComponentRequest componentRequest) {
		Component component = componentRepository.findOne(componentId);
		if (component == null) {
			throw new EntityNotFoundException("Component '{" + componentId + "}' not found");
		}
		component.setManufacturerPartNumber(componentRequest.getManufacturerPartNumber() != null ? componentRequest.getManufacturerPartNumber() : component.getManufacturerPartNumber());
		return componentRepository.save(component);
	}
	
	@Override
	@Transactional
	public void delete(Integer componentId) {
		componentRepository.delete(componentId);
	}
	
}
