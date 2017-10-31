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
import java.util.ArrayList;

@RestController
@RequestMapping(value = ManufacturerController.MANUFACTURERS_BASE_URI)
public class ManufacturerController {
	
	
	public static final String MANUFACTURERS_BASE_URI = "/api/supply/manufacturers";
	
	@Autowired
	ManufacturerServiceImpl manufacturerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<ManufacturerResponse> getManufacturer() {
		ArrayList<ManufacturerResponse> manufacturerResponses = new ArrayList<ManufacturerResponse>();
		manufacturerService.getManufacturers().forEach(manufacturer -> manufacturerResponses.add(new ManufacturerResponse(manufacturer)));
		return manufacturerResponses;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void createManufacturer(@RequestBody ManufacturerRequest manufacturerRequest, HttpServletResponse response) {
		Manufacturer manufacturer = manufacturerService.create(manufacturerRequest);
		response.addHeader(HttpHeaders.LOCATION, this.MANUFACTURERS_BASE_URI + "/" + manufacturer.getId());
	}
	
	@RequestMapping(value = "/{manufacturerId}", method = RequestMethod.GET)
	public ManufacturerResponse getManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId) {
		return new ManufacturerResponse(manufacturerService.read(manufacturerId));
	}
	
	@RequestMapping(value = "{manufacturerId}", method = RequestMethod.PATCH)
	public ManufacturerResponse updateManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId, @RequestBody ManufacturerRequest manufacturerRequest) {
		return new ManufacturerResponse(manufacturerService.update(manufacturerId, manufacturerRequest));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "{manufacturerId}", method = RequestMethod.DELETE)
	public void deleteManufacturer(@PathVariable("manufacturerId") final Integer manufacturerId) {
		manufacturerService.delete(manufacturerId);
	}
	
}
