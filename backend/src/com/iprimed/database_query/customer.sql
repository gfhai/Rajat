CREATE table "CUSTOMER" (
    "CUSTID"       NUMBER NOT NULL,
    "FIRSTNAME"    VARCHAR2(50) NOT NULL,
    "LASTNAME"     VARCHAR2(50) NOT NULL,
    "EMAIL"        VARCHAR2(50) NOT NULL,
    "AADHAARNO"     NUMBER(12) NOT NULL,
    "PASSWORD"     NUMBER(8) NOT NULL,
    "MOBILENUMBER" NUMBER(10) NOT NULL,
    "ADDRESS"      VARCHAR2(255) NOT NULL,
    "CITY"         VARCHAR2(50) NOT NULL,
    "STATE"        VARCHAR2(50) NOT NULL,
    "PINCODE"      NUMBER(6) NOT NULL,
    constraint  "CUSTOMER_PK" primary key ("EMAIL")
);


CREATE SEQUENCE auto_increment
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  
  