package com.levonke.Supply.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ComponentRequest {
	
	@NotEmpty(message = "Not valid manufacturerPartNumber")
	private String manufacturerPartNumber;
	
	@Length(min = 36, max = 36, message = "Not valid UUID length")
	String uuid;
	
}
