DROP SCHEMA IF EXISTS warehouse CASCADE;

CREATE SCHEMA warehouse;

DROP TABLE IF EXISTS warehouse.product;

CREATE TABLE IF NOT EXISTS warehouse.product (
    identifier INTEGER,
    name VARCHAR(20),
    price INTEGER
);