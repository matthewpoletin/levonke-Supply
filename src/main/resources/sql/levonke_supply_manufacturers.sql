CREATE TABLE supply.manufacturers
(
    manufacturers_id integer DEFAULT nextval('supply.manufacturers_manufacturers_id_seq'::regclass) PRIMARY KEY NOT NULL,
    manufacturers_name varchar(255) NOT NULL,
    manufacturers_website varchar(255)
);
CREATE UNIQUE INDEX uk_723u0v7nna802n0bt2ikg7pf7 ON supply.manufacturers (manufacturers_name);