
CREATE SCHEMA IF NOT EXISTS KZDB;
--------------------------------------------------------
--  DDL for Table TOY_DETAILS
--------------------------------------------------------
DROP TABLE if EXISTS KZDB.TOY_DETAILS;

  CREATE TABLE IF NOT EXISTS KZDB.TOY_DETAILS
   (ID VARCHAR2(100),
	PRICE VARCHAR2(100),
	AGE VARCHAR2(100),
	NAME VARCHAR2(100),
	IMAGEURL VARCHAR2(250)
);----

