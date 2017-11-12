package com.levonke.Supply.service;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ManufacturerRepository;
import com.levonke.Supply.web.model.ManufacturerRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Manufacturer> getManufacturers(Integer page, Integer size) {
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 25;
		}
		return manufacturerRepository.findAll(PageRequest.of(page, size)).getContent();
	}
	
	@Override
	@Transactional
	public Manufacturer createManufacturer(ManufacturerRequest manufacturerRequest) {
		Manufacturer manufacturer = new Manufacturer()
			.setName(manufacturerRequest.getName())
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
		manufacturer.setWebsite(manufacturerRequest.getWebsite() != null ? manufacturerRequest.getWebsite() : manufacturer.getWebsite());
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	@Transactional
	public void deleteManufacturerById(Integer manufacturerId) {
		manufacturerRepository.deleteById(manufacturerId);
	}
	
	@Override
	@Transactional
	public List<Component> getComponents(Integer manufacturerId) {
		Manufacturer manufacturer = this.getManufacturerById(manufacturerId);
		return new ArrayList<>(manufacturer.getComponents());
	}
	
}
