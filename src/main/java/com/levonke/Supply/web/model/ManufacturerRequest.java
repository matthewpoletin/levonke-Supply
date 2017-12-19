package com.levonke.Supply.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ManufacturerRequest {
	
	@NotEmpty(message = "Not valid name")
	String name;
	
	String description;
	
	String website;
	
}
