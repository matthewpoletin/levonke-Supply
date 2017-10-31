package com.levonke.Supply.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "manufacturers", schema = "supply")
public class Manufacturer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manufacturers_id")
	private Integer id;
	
	@Column(name = "manufacturers_name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "manufacturers_website")
	private String website;
	
	@JsonIgnore
	@OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
	private Collection<Component> components = new ArrayList<Component>();
	
}
