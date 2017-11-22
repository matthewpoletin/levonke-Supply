package com.levonke.Supply.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ManufacturerRequest {
	
	String name;
	String website;
	
}
