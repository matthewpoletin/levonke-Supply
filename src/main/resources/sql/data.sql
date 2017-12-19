INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_description, manufacturers_website)
	VALUES ('STMicroelectronics', 'STMicroelectronics is a global independent semiconductor company and is a leader in developing and delivering semiconductor solutions across the spectrum of microelectronics applications. An unrivaled combination of silicon and system expertise, manufacturing strength, Intellectual Property (IP) portfolio and strategic partners positions the Company at the forefront of System-on-Chip (SoC) technology and its products play a key role in enabling today''s convergence trends.', 'www.st.com');

INSERT INTO supply.manufacturers (manufacturers_name, manufacturers_description, manufacturers_website)
	VALUES ('Microchip Technology', 'Microchip Technology Inc. is a leading provider of microcontroller and analog semiconductors, providing low-risk product development, lower total system cost and faster time to market for thousands of diverse customer applications worldwide. Headquartered in Chandler, Arizona, Microchip offers outstanding technical support along with dependable delivery and quality.', 'www.microchip.com');

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
