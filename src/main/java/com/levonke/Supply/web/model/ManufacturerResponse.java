package com.levonke.Supply.web.model;

import com.levonke.Supply.domain.Manufacturer;

import lombok.Data;

@Data
public class ManufacturerResponse {
	
	Integer id;
	String name;
	String website;
	
	public ManufacturerResponse(Manufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
		this.website = manufacturer.getWebsite();
	}
	
}
