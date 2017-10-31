CREATE TABLE supply.components
(
	components_id integer DEFAULT nextval('supply.components_components_id_seq'::regclass) PRIMARY KEY NOT NULL,
	components_manufacturer_part_number varchar(255),
	components_manufacturer_id integer,
	CONSTRAINT fk536ojdwhc3ggfle4fg7nrclo0 FOREIGN KEY (components_manufacturer_id) REFERENCES manufacturers (manufacturers_id)
);
