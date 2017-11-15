package com.levonke.Supply.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
@Table(	name = "components", schema = "supply",
		uniqueConstraints = @UniqueConstraint(columnNames = {"components_manufacturer_id", "components_manufacturer_part_number"})
)
public class Component {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "components_id")
	private Integer id;
	
	@Column(name = "components_manufacturer_part_number", unique = true, nullable = false)
	private String manufacturerPartNumber;
	
	@ManyToOne
	@JoinColumn(name = "components_manufacturer_id")
	Manufacturer manufacturer;
	
	@Column(name  = "components_uuid", unique = true/*, nullable = false*/)
	// TODO: make values trigger generated
//	@GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	UUID uuid;
	
}
