package com.levonke.Supply.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "manufacturers", schema = "supply")
public class Manufacturer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manufacturers_id")
	Integer id;
	
	@Column(name = "manufacturers_name", unique = true, nullable = false)
	String name;
	
	@Column(name = "manufacturers_website")
	String website;
	
}
