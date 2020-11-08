


-- KATEGORIE PRINTER --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 1;

-- KATEGORIE NOTEBOOK --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 2;

-- KATEGORIE TABLET --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 3;

-- KATEGORIE COMPUTER --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 4;

-- KATEGORIE MONITOR --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 5;

-- KATEGORIE TELEVISION --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 6;

-- KATEGORIE GAMING CONSOLE --
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
JOIN CategoryProduct AS cp ON p.id = cp.ProductID
JOIN Category AS c ON c.id = cp.CategoryID
WHERE c.id = 7;

--  VŠE --

SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id;

-- FILTER ZNACKA CANON -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 1;

-- FILTER ZNACKA HP -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 2;

-- FILTER ZNACKA EPSON -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 3;

-- FILTER ZNACKA DELL -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 4;

-- FILTER ZNACKA LENOVO -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 5;

-- FILTER ZNACKA APPLE -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 6;

-- FILTER ZNACKA SONY -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 7;

-- FILTER ZNACKA SAMSUNG -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 8;

-- FILTER ZNACKA LG -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 9;

-- FILTER ZNACKA MICROSOFT -- 
SELECT DISTINCT * 
FROM Product AS p
JOIN Brand AS b ON p.BrandId = b.id
WHERE b.id = 10;

-- SELECT ALL ORDERS --
SELECT DISTINCT * 
FROM `Order`;

-- SELECT ALL ORDERED ORDERS --
SELECT DISTINCT * 
FROM `Order` AS o
JOIN State AS s ON s.id = o.StateID
WHERE s.Ordered = true;

-- SELECT ALL COMPLETED ORDERS --
SELECT DISTINCT * 
FROM `Order` AS o
JOIN State AS s ON s.id = o.StateID	
WHERE s.Completed = true;


