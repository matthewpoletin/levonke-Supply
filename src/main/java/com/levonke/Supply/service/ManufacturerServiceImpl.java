package com.levonke.Supply.service;

import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ManufacturerRepository;
import com.levonke.Supply.web.model.ManufacturerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Manufacturer> getManufacturers(Integer page, Integer size) {
		if (page == null) {
			page = 0;
		}
		if (size == null) {
			size = 25;
		}
		return manufacturerRepository.findAll(new PageRequest(page, size)).getContent();
	}
	
	@Override
	@Transactional
	public Manufacturer create(ManufacturerRequest manufacturerRequest) {
		Manufacturer manufacturer = new Manufacturer()
			.setName(manufacturerRequest.getName())
			.setWebsite(manufacturerRequest.getWebsite());
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Manufacturer read(Integer manufacturerId) {
		Manufacturer manufacturer = manufacturerRepository.findOne(manufacturerId);
		if (manufacturer == null) {
			throw new EntityNotFoundException("Manufacturer '{" + manufacturerId + "}' not found");
		}
		return manufacturer;
	}
	
	@Override
	@Transactional
	public Manufacturer update(Integer manufacturerId, ManufacturerRequest manufacturerRequest) {
		Manufacturer manufacturer = manufacturerRepository.findOne(manufacturerId);
		if (manufacturer == null) {
			throw new EntityNotFoundException("Manufacturer '{" + manufacturerId + "}' not found");
		}
		manufacturer.setName(manufacturerRequest.getName() != null ? manufacturerRequest.getName() : manufacturer.getName());
		manufacturer.setWebsite(manufacturerRequest.getWebsite() != null ? manufacturerRequest.getWebsite() : manufacturer.getWebsite());
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	@Transactional
	public void delete(Integer manufacturerId) {
		manufacturerRepository.delete(manufacturerId);
	}
	
}
