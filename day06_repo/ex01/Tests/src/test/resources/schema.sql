DROP SCHEMA IF EXISTS warehouse;

CREATE SCHEMA warehouse;

CREATE TABLE IF NOT EXISTS warehouse.product (
    identifier INTEGER,
    name VARCHAR(20),
    price INTEGER
);