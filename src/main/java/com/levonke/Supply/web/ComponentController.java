package com.levonke.Supply.web;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.service.ComponentServiceImpl;
import com.levonke.Supply.web.model.ComponentRequest;
import com.levonke.Supply.web.model.ComponentResponse;
import com.levonke.Supply.web.model.ManufacturerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ComponentController.componentBaseURI)
public class ComponentController {
	
	public static final String componentBaseURI = "/api/supply";
	
	private ComponentServiceImpl componentService;
	
	@Autowired
	public ComponentController(ComponentServiceImpl componentService) {
		this.componentService = componentService;
	}
	
	@RequestMapping(value = "/components", method = RequestMethod.GET)
	public List<ComponentResponse> getComponents(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
		return componentService.getComponents(page, size)
			.stream()
			.map(ComponentResponse::new)
			.collect(Collectors.toList());
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/components", method = RequestMethod.POST)
	public ComponentResponse createComponent(@RequestBody ComponentRequest componentRequest, HttpServletResponse response) {
		Component component = componentService.createComponent(componentRequest);
		response.addHeader(HttpHeaders.LOCATION, componentBaseURI + "/components/" + component.getId());
		return new ComponentResponse(component);
	}
	
	@RequestMapping(value = "/components/{componentId}", method = RequestMethod.GET)
	public ComponentResponse getComponent(@PathVariable("componentId") final Integer componentId) {
		return new ComponentResponse(componentService.getComponentById(componentId));
	}
	
	@RequestMapping(value = "/component", method = RequestMethod.GET)
	public ComponentResponse getComponent(@RequestParam(value = "uuid") final String uuid) {
		return new ComponentResponse(componentService.getComponentByUUID(UUID.fromString(uuid)));
	}
	
	@RequestMapping(value = "/components/{componentId}", method = RequestMethod.PATCH)
	public ComponentResponse updateComponent(@PathVariable("componentId") final Integer componentId, @RequestBody ComponentRequest componentRequest) {
		return new ComponentResponse(componentService.updateComponentById(componentId, componentRequest));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/components/{componentId}", method = RequestMethod.DELETE)
	public void deleteComponent(@PathVariable("componentId") final Integer componentId) {
		componentService.deleteComponentById(componentId);
	}
	
	@RequestMapping(value = "/components/{componentId}/manufacturers/{manufacturerId}", method = RequestMethod.POST)
	public void setManufacturerToComponent(@PathVariable("componentId") final Integer componentId, @PathVariable("manufacturerId") final Integer manufacturerId) {
		componentService.setManufacturer(componentId, manufacturerId);
	}
	
	@RequestMapping(value = "/components/{componentId}/manufacturers", method = RequestMethod.GET)
	public ManufacturerResponse getManufacturerOfComponent(@PathVariable("componentId") final Integer componentId) {
		return new ManufacturerResponse(componentService.getManufacturer(componentId));
	}
	
}
