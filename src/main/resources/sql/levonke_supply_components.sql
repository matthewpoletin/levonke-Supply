CREATE TABLE supply.components
(
	components_id integer DEFAULT nextval('supply.components_components_id_seq'::regclass) PRIMARY KEY NOT NULL,
	components_manufacturer_part_number varchar(255)
);
