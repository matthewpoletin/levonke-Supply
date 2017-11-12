package com.levonke.Supply.web.model;

import com.levonke.Supply.domain.Manufacturer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ManufacturerResponse {
	
	Integer id;
	String name;
	String website;
	List<Integer> componentsId = new ArrayList<>();
	
	public ManufacturerResponse(Manufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
		this.website = manufacturer.getWebsite();
		manufacturer.getComponents().forEach(component -> this.componentsId.add(component.getId()));
	}
	
}
