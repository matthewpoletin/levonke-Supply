package com.levonke.Supply.web.model;

import com.levonke.Supply.domain.Component;
import lombok.Data;

@Data
public class ComponentResponse {
	
	Integer id;
	String manufacturerPartNumber;
	Integer manufacturerId;
	
	public ComponentResponse(Component component) {
		this.id = component.getId();
		this.manufacturerPartNumber = component.getManufacturerPartNumber();
		this.manufacturerId = component.getManufacturer().getId();
	}
	
}
