CREATE table "PRODUCT" (
    "PRODUCTID"    NUMBER NOT NULL,
    "PRODUCTNAME"  VARCHAR2(100) NOT NULL,
    "PRODUCTPRICE" NUMBER NOT NULL,
    "PRODUCTDESC"  VARCHAR2(2000),
    constraint  "PRODUCT_PK" primary key ("PRODUCTID")
);