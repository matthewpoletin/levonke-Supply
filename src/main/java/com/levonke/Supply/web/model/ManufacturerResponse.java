package com.levonke.Supply.web.model;

import com.levonke.Supply.domain.Manufacturer;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ManufacturerResponse {
	
	Integer id;
	String name;
	String description;
	String website;
	List<Integer> componentsId = new ArrayList<>();
	
	public ManufacturerResponse(Manufacturer manufacturer) {
		this.id = manufacturer.getId();
		this.name = manufacturer.getName();
		this.description = manufacturer.getDescription();
		this.website = manufacturer.getWebsite();
		manufacturer.getComponents().forEach(component -> this.componentsId.add(component.getId()));
	}
	
}
