package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ManufacturerRepository;
import com.levonke.Supply.web.model.ManufacturerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
	
	private final ManufacturerRepository manufacturerRepository;
	
	@Autowired
	public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
		this.manufacturerRepository = manufacturerRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Manufacturer> getManufacturers(Integer page, Integer size) {
		return manufacturerRepository.findAll(PageRequest.of(page, size));
	}
	
	@Override
	@Transactional
	public Manufacturer createManufacturer(ManufacturerRequest manufacturerRequest) {
		Manufacturer manufacturer = new Manufacturer()
			.setName(manufacturerRequest.getName())
			.setDescription(manufacturerRequest.getDescription())
			.setWebsite(manufacturerRequest.getWebsite());
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Manufacturer getManufacturerById(Integer manufacturerId) {
		return manufacturerRepository.findById(manufacturerId)
			.orElseThrow(() -> new EntityNotFoundException("Manufacturer '{" + manufacturerId + "}' not found"));
	}
	
	@Override
	@Transactional
	public Manufacturer updateManufacturerById(Integer manufacturerId, ManufacturerRequest manufacturerRequest) {
		Manufacturer manufacturer = this.getManufacturerById(manufacturerId);
		manufacturer.setName(manufacturerRequest.getName() != null ? manufacturerRequest.getName() : manufacturer.getName());
		manufacturer.setDescription(manufacturerRequest.getDescription() != null ? manufacturerRequest.getDescription() : manufacturer.getDescription());
		manufacturer.setWebsite(manufacturerRequest.getWebsite() != null ? manufacturerRequest.getWebsite() : manufacturer.getWebsite());
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	@Transactional
	public void deleteManufacturerById(Integer manufacturerId) {
		manufacturerRepository.deleteById(manufacturerId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Component> getComponents(Integer manufacturerId, Integer page, Integer size) {
		Manufacturer manufacturer = this.getManufacturerById(manufacturerId);
		List<Component> components = new ArrayList<>(manufacturer.getComponents());
		return new PageImpl<>(components, PageRequest.of(page, size), components.size());
	}
	
}
