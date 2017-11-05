package com.levonke.Supply.web;

import com.levonke.Supply.domain.Component;
import com.levonke.Supply.service.ComponentServiceImpl;
import com.levonke.Supply.web.model.ComponentRequest;
import com.levonke.Supply.web.model.ComponentResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ComponentController.COMPONENTS_BASE_URI)
public class ComponentController {

	public static final String COMPONENTS_BASE_URI = "/api/supply/components";

	private ComponentServiceImpl componentService;
	
	@Autowired
	public ComponentController(ComponentServiceImpl componentService) {
		this.componentService = componentService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<ComponentResponse> getComponents(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size) {
		return componentService.getComponents(page, size)
			.stream()
			.map(ComponentResponse::new)
			.collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public void createComponent(@RequestBody ComponentRequest componentRequest, HttpServletResponse response) {
		Component component = componentService.create(componentRequest);
		response.addHeader(HttpHeaders.LOCATION, this.COMPONENTS_BASE_URI + "/" + component.getId());
	}

	@RequestMapping(value = "/{componentId}", method = RequestMethod.GET)
	public ComponentResponse getComponent(@PathVariable("componentId") final Integer componentId) {
		return new ComponentResponse(componentService.read(componentId));
	}

	@RequestMapping(value = "/{componentId}", method = RequestMethod.PATCH)
	public ComponentResponse updateComponent(@PathVariable("componentId") final Integer componentId, @RequestBody ComponentRequest componentRequest) {
		return new ComponentResponse(componentService.update(componentId, componentRequest));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{componentId}", method = RequestMethod.DELETE)
	public void deleteComponent(@PathVariable("componentId") final Integer componentId) {
		componentService.delete(componentId);
	}

}
