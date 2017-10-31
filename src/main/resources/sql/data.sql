INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_website)
	VALUES ('STMicroelectronics', 'www.st.com');

INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_website)
	VALUES ('Microchip Technology', 'www.microchip.com');

INSERT INTO supply.components (components_manufacturer_part_number, components_manufacturer_id)
	VALUES ('STM32F100RBT6B',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);

INSERT INTO supply.components (components_manufacturer_part_number, components_manufacturer_id)
	VALUES ('STM32F101C8T6',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);

INSERT INTO supply.components (components_manufacturer_part_number, components_manufacturer_id)
	VALUES ('STM32F103RCT6',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);
