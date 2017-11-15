INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_website)
	VALUES ('STMicroelectronics', 'www.st.com');

INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_website)
	VALUES ('Microchip Technology', 'www.microchip.com');

INSERT INTO supply.components (components_manufacturer_part_number, components_uuid, components_manufacturer_id)
	VALUES ('STM32F100RBT6B', 'd1098e96-ca3d-11e7-abc4-cec278b6b50a',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);

INSERT INTO supply.components (components_manufacturer_part_number, components_uuid, components_manufacturer_id)
	VALUES ('STM32F101C8T6', 'd10991fc-ca3d-11e7-abc4-cec278b6b50a',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);

INSERT INTO supply.components (components_manufacturer_part_number, components_uuid, components_manufacturer_id)
	VALUES ('STM32F103RCT6', 'd10993a0-ca3d-11e7-abc4-cec278b6b50a',
			(SELECT (manufacturers_id) FROM supply.manufacturers WHERE manufacturers_name = 'STMicroelectronics')
	);
