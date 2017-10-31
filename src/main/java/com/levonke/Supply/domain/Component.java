package com.levonke.Supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "components", schema = "supply")
public class Component {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "components_id")
	private Integer id;
	
	@Column(name = "components_manufacturer_part_number", unique = true)
	private String manufacturerPartNumber;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "components_manufacturer_id")
	Manufacturer manufacturer;
	
}
