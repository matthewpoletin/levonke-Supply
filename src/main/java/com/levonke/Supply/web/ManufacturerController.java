package com.levonke.Supply.web;

import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.service.ManufacturerServiceImpl;
import com.levonke.Supply.web.model.ManufacturerRequest;
import com.levonke.Supply.web.model.ManufacturerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ManufacturerController.manufacturerBaseURI)
public class ManufacturerController {
	
	static final String manufacturerBaseURI = "/api/supply";
	
	private ManufacturerServiceImpl manufacturerService;
	
	@Autowired
	public ManufacturerController(ManufacturerServiceImpl manufacturerService) {
		this.manufacturerService = manufacturerService;
	}
	
	@RequestMapping(value = "/manufacturers", method = RequestMethod.GET)
	public List<ManufacturerResponse> getManufacturer(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
		return manufacturerService.getManufacturers(page, size)
			.stream()
			.map(ManufacturerResponse::new)
			.collect(Collectors.toList());
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/manufacturers", method = RequestMethod.POST)
	public ManufacturerResponse createManufacturer(@Valid @RequestBody ManufacturerRequest manufacturerRequest, HttpServletResponse response) {
		Manufacturer manufacturer = manufacturerService.createManufacturer(manufacturerRequest);
		response.addHeader(HttpHeaders.LOCATION, manufacturerBaseURI + "/manufacturers/" + manufacturer.getId());
		return new ManufacturerResponse(manufacturer);
	}

	@RequestMapping(value = "/manufacturers/{manufacturerId}", method = RequestMethod.GET)
	public ManufacturerResponse getManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId) {
		return new ManufacturerResponse(manufacturerService.getManufacturerById(manufacturerId));
	}

	@RequestMapping(value = "/manufacturers/{manufacturerId}", method = RequestMethod.PATCH)
	public ManufacturerResponse updateManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId, @Valid @RequestBody ManufacturerRequest manufacturerRequest) {
		return new ManufacturerResponse(manufacturerService.updateManufacturerById(manufacturerId, manufacturerRequest));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/manufacturers/{manufacturerId}", method = RequestMethod.DELETE)
	public void deleteManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId) {
		manufacturerService.deleteManufacturerById(manufacturerId);
	}
	
	@RequestMapping(value = "/manufacturers/{manufacturerId}/components", method = RequestMethod.GET)
	public List<Integer> getComponents(@PathVariable("manufacturerId") final Integer manufacturerId) {
		List<Integer> componentsId = new ArrayList<>();
		manufacturerService.getComponents(manufacturerId).forEach(component -> componentsId.add(component.getId()));
		return componentsId;
	}
	
}
