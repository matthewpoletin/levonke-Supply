package com.levonke.Supply.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ComponentRequest {
	
	private String manufacturerPartNumber;
	String uuid;
	
}
