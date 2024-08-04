-- CUSTOM ------------------------------

-- banio
CREATE OR REPLACE VIEW banio_custom AS
SELECT * FROM banio WHERE eliminado = '0' ORDER BY fecha_creacion DESC;

-- boleta
CREATE OR REPLACE VIEW boleta_custom AS
SELECT * FROM boleta ORDER BY fecha_creacion DESC;

-- boleta_producto
CREATE OR REPLACE VIEW boleta_producto_custom AS
SELECT * FROM boleta_producto ORDER BY boleta_id DESC;

-- pedido
CREATE OR REPLACE VIEW pedido_custom AS
SELECT * FROM pedido ORDER BY fecha_emision DESC;

-- pedido_producto
CREATE OR REPLACE VIEW pedido_producto_custom AS
SELECT * FROM pedido_producto ORDER BY pedido_id DESC;

-- categoria
CREATE OR REPLACE VIEW categoria_custom AS
SELECT * FROM categoria WHERE eliminado = '0' ORDER BY nombre;

-- cita
CREATE OR REPLACE VIEW cita_custom AS
SELECT * FROM cita WHERE eliminado = '0' ORDER BY fecha_programada DESC;

-- cliente
CREATE OR REPLACE VIEW cliente_custom AS
SELECT * FROM cliente WHERE eliminado = '0' ORDER BY fecha_creacion DESC;

-- especie
CREATE OR REPLACE VIEW especie_custom AS
SELECT * FROM especie WHERE eliminado = '0' ORDER BY id;

-- mascota
CREATE OR REPLACE VIEW mascota_custom AS
SELECT * FROM mascota WHERE eliminado = '0' ORDER BY fecha_creacion DESC;

-- producto
CREATE OR REPLACE VIEW producto_custom AS
SELECT * FROM producto WHERE eliminado = '0' ORDER BY fecha_creacion DESC;

-- producto_proveedor
CREATE OR REPLACE VIEW producto_proveedor_custom AS
SELECT * FROM producto_proveedor ORDER BY proveedor_id;

-- proveedor
CREATE OR REPLACE VIEW proveedor_custom AS
SELECT * FROM proveedor WHERE eliminado = '0' ORDER BY nombre;

-- raza
CREATE OR REPLACE VIEW raza_custom AS
SELECT * FROM raza WHERE eliminado = '0' ORDER BY especie_id, nombre;

-- rol
CREATE OR REPLACE VIEW rol_custom AS
SELECT * FROM rol WHERE eliminado = '0' ORDER BY id;

-- tratamiento
CREATE OR REPLACE VIEW tratamiento_custom AS
SELECT * FROM tratamiento WHERE eliminado = '0' ORDER BY fecha_creacion DESC;

-- usuario
CREATE OR REPLACE VIEW usuario_custom AS
SELECT * FROM usuario WHERE eliminado = '0' ORDER BY id;

-- usuario_rol
CREATE OR REPLACE VIEW usuario_rol_custom AS
SELECT * FROM usuario_rol ORDER BY usuario_id;


-- MAPPER ------------------------------

-- banio
CREATE OR REPLACE VIEW banio_mapper AS
SELECT b.id, CONCAT(c.nombre, ' ', c.apellidos) AS cliente, c.foto AS foto_cliente, c.celular, m.nombre AS mascota, m.foto AS foto_mascota, b.tipo, b.fecha_creacion AS fecha, CASE WHEN b.cita_id IS NOT NULL THEN 'SI' ELSE 'NO' END AS citado, b.precio
FROM banio AS b 
INNER JOIN mascota AS m ON b.mascota_id = m.id
INNER JOIN cliente AS c ON m.cliente_id = c.id
WHERE b.eliminado = '0'
ORDER BY b.fecha_creacion DESC;

-- boleta
CREATE OR REPLACE VIEW boleta_mapper AS
SELECT bo.id, CONCAT(c.nombre,' ',c.apellidos) AS cliente, c.foto AS foto_cliente, u.username AS usuario, bo.fecha_creacion AS fecha, bo.tipo_pago, bo.precio_final AS total, bo.estado, bo.mensaje
FROM boleta AS bo
INNER JOIN cliente AS c ON bo.cliente_id = c.id
INNER JOIN usuario AS u ON bo.usuario_id = u.id
ORDER BY bo.fecha_creacion DESC;

-- boleta_producto
CREATE OR REPLACE VIEW boleta_producto_mapper AS
SELECT bp.boleta_id as numero_boleta, p.nombre as producto, bp.cantidad, bp.precio, bp.total 
FROM boleta_producto as bp
INNER JOIN producto AS p ON bp.producto_id = p.id;

-- pedido
CREATE OR REPLACE VIEW pedido_mapper AS
SELECT pe.id, pr.nombre AS proveedor, pr.foto AS foto_proveedor, u.username AS usuario, pe.fecha_emision, pe.fecha_entrega , pe.tipo_pago, pe.total, pe.estado, pe.mensaje
FROM pedido AS pe
INNER JOIN proveedor AS pr ON pe.proveedor_id = pr.id
INNER JOIN usuario AS u ON pe.usuario_id = u.id
ORDER BY pe.fecha_emision DESC;

-- pedido_producto
CREATE OR REPLACE VIEW pedido_producto_mapper AS
SELECT pp.pedido_id as numero_pedido, p.nombre as producto, pp.cantidad, pp.precio_compra, pp.importe 
FROM pedido_producto as pp
INNER JOIN producto AS p ON pp.producto_id = p.id;

-- categoria
CREATE OR REPLACE VIEW categoria_mapper AS
SELECT ca.id, ca.nombre
FROM categoria AS ca
WHERE ca.eliminado = '0'
ORDER BY ca.id;

-- cita
CREATE OR REPLACE VIEW cita_mapper AS
SELECT ci.id, CONCAT(c.nombre,' ',c.apellidos) AS cliente, c.foto AS foto_cliente, c.celular, m.nombre AS mascota, m.foto AS foto_mascota, motivo, ci.estado, ci.fecha_programada, ci.fecha_atendida
FROM cita AS ci
INNER JOIN mascota AS m ON ci.mascota_id = m.id
INNER JOIN cliente AS c ON m.cliente_id = c.id
WHERE ci.eliminado = '0'
ORDER BY ci.fecha_programada DESC;

-- cliente
CREATE OR REPLACE VIEW cliente_mapper AS
SELECT c.id, CONCAT(c.nombre,' ',c.apellidos) AS cliente, c.genero, c.fecha_creacion AS fecha ,c.celular, c.foto
FROM cliente AS c 
WHERE c.eliminado = '0'
ORDER BY fecha_creacion DESC;

-- especie
CREATE OR REPLACE VIEW especie_mapper AS
SELECT e.id, e.nombre 
FROM especie AS e 
WHERE e.eliminado = '0'
ORDER BY e.id;

-- mascota
CREATE OR REPLACE VIEW mascota_mapper AS
SELECT m.id, m.nombre, CONCAT(c.nombre,' ',c.apellidos) AS cliente, m.fecha_creacion AS fecha, m.sexo, e.nombre AS especie, r.nombre AS raza, m.foto
FROM mascota AS m
INNER JOIN cliente AS c ON m.cliente_id = c.id
INNER JOIN raza AS r ON m.raza_id = r.id
INNER JOIN especie AS e ON r.especie_id = e.id
WHERE m.eliminado = '0'
ORDER BY m.fecha_creacion DESC;

-- producto
CREATE OR REPLACE VIEW producto_mapper AS
SELECT p.id, p.nombre, ca.nombre AS categoria, p.marca, p.descripcion, fecha_creacion AS fecha, p.stock, p.precio_venta, p.precio_compra, p.unidad_medida, p.foto  
FROM producto AS p
INNER JOIN categoria AS ca ON p.categoria_id = ca.id
WHERE p.eliminado = '0'
ORDER BY fecha_creacion DESC;

-- producto_proveedor
CREATE OR REPLACE VIEW producto_proveedor_mapper AS
SELECT p.nombre AS producto, pr.nombre AS proveedor
FROM producto_proveedor AS pp
INNER JOIN producto AS p ON pp.producto_id = p.id
INNER JOIN proveedor AS pr ON pp.proveedor_id = pr.id
ORDER BY pr.id;

-- proveedor
CREATE OR REPLACE VIEW proveedor_mapper AS
SELECT pr.id, pr.nombre, pr.correo, pr.celular, pr.foto
FROM proveedor AS pr 
WHERE pr.eliminado = '0'
ORDER BY pr.id;

-- raza
CREATE OR REPLACE VIEW raza_mapper AS
SELECT r.id, r.nombre, e.nombre AS especie
FROM raza AS r
INNER JOIN especie AS e ON r.especie_id = e.id
WHERE r.eliminado = '0'
ORDER BY r.especie_id, r.nombre;

-- rol
CREATE OR REPLACE VIEW rol_mapper AS
SELECT ro.id, ro.nombre
FROM rol AS ro
WHERE ro.eliminado = '0'
ORDER BY ro.id;

-- tratamiento
CREATE OR REPLACE VIEW tratamiento_mapper AS
SELECT t.id, CONCAT(c.nombre, ' ', c.apellidos) AS cliente, c.foto AS foto_cliente, c.celular, m.nombre AS mascota, m.foto AS foto_mascota, t.tipo, t.fecha_creacion AS fecha, CASE WHEN t.cita_id IS NOT NULL THEN 'SI' ELSE 'NO' END AS citado, t.precio
FROM tratamiento AS t 
INNER JOIN mascota AS m ON t.mascota_id = m.id
INNER JOIN cliente AS c ON m.cliente_id = c.id
WHERE t.eliminado = '0'
ORDER BY t.fecha_creacion DESC;

-- usuario
CREATE OR REPLACE VIEW usuario_mapper AS
SELECT u.id, u.username, u.correo
FROM usuario AS u
WHERE u.eliminado = '0'
ORDER BY u.id;

-- usuario_rol
CREATE OR REPLACE VIEW usuario_rol_mapper AS
SELECT u.username AS usuario, ro.nombre AS rol
FROM usuario_rol AS ur
INNER JOIN usuario AS u ON ur.usuario_id = u.id
INNER JOIN rol AS ro ON ur.rol_id = ro.id
ORDER BY u.id;

-- Functions-Mapper ------------------------------

CREATE OR REPLACE FUNCTION get_mascotas_by_cliente_id(cliente_id INT)
RETURNS TABLE (
    id INT,
    nombre TEXT,
    cliente TEXT,
    fecha TIMESTAMP,
    sexo TEXT,
    especie TEXT,
    raza TEXT,
    foto TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT m.id, m.nombre::text, CONCAT(c.nombre, ' ', c.apellidos)::text, m.fecha_creacion, m.sexo::text, e.nombre::text,
    r.nombre::text, m.foto::text
    FROM mascota AS m
    INNER JOIN cliente AS c ON m.cliente_id = c.id
    INNER JOIN raza AS r ON m.raza_id = r.id
    INNER JOIN especie AS e ON r.especie_id = e.id
    WHERE m.eliminado = '0' and m.cliente_id = get_mascotas_by_cliente_id.cliente_id
    ORDER BY m.fecha_creacion DESC;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_tratamientos_by_mascota_id(mascota_id INT)
RETURNS TABLE (
    id INT,
    cliente TEXT,
    foto_cliente TEXT,
    celular TEXT,
    mascota TEXT,
    foto_mascota TEXT,
    tipo TEXT,
    fecha TIMESTAMP,
    citado TEXT,
    precio NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT t.id, CONCAT(c.nombre, ' ', c.apellidos)::text, c.foto::text, c.celular::text, m.nombre::text, m.foto::text, t.tipo::text, t.fecha_creacion, CASE WHEN t.cita_id IS NOT NULL THEN 'SI' ELSE 'NO' END, t.precio::numeric
    FROM tratamiento AS t 
    INNER JOIN mascota AS m ON t.mascota_id = m.id
    INNER JOIN cliente AS c ON m.cliente_id = c.id
    WHERE t.eliminado = '0' and t.mascota_id = get_tratamientos_by_mascota_id.mascota_id
    ORDER BY t.fecha_creacion DESC;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_banios_by_mascota_id(mascota_id INT)
RETURNS TABLE (
    id INT,
    cliente TEXT,
    foto_cliente TEXT,
    celular TEXT,
    mascota TEXT,
    foto_mascota TEXT,
    tipo TEXT,
    fecha TIMESTAMP,
    citado TEXT,
    precio NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT b.id, CONCAT(c.nombre, ' ', c.apellidos)::text, c.foto::text, c.celular::text, m.nombre::text, m.foto::text, b.tipo::text, b.fecha_creacion, CASE WHEN b.cita_id IS NOT NULL THEN 'SI' ELSE 'NO' END, b.precio::numeric
    FROM banio AS b 
    INNER JOIN mascota AS m ON b.mascota_id = m.id
    INNER JOIN cliente AS c ON m.cliente_id = c.id
    WHERE b.eliminado = '0' and b.mascota_id = get_banios_by_mascota_id.mascota_id
    ORDER BY b.fecha_creacion DESC;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_citas_by_mascota_id(mascota_id INT)
RETURNS TABLE (
    id INT,
    cliente TEXT,
    foto_cliente TEXT,
    celular TEXT,
    mascota TEXT,
    foto_mascota TEXT,
    motivo TEXT,
    estado TEXT,
    fecha_programada TIMESTAMP,
    fecha_atendida TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT ci.id, CONCAT(c.nombre, ' ', c.apellidos)::text, c.foto::text, c.celular::text, m.nombre::text, m.foto::text, ci.motivo::text, ci.estado::text, ci.fecha_programada, ci.fecha_atendida
    FROM cita AS ci
    INNER JOIN mascota AS m ON ci.mascota_id = m.id
    INNER JOIN cliente AS c ON m.cliente_id = c.id
    WHERE ci.eliminado = '0' and ci.mascota_id = get_citas_by_mascota_id.mascota_id
    ORDER BY ci.fecha_programada DESC;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_citas_by_estado(estado_cita VARCHAR(15))
RETURNS TABLE (
    id INT,
    cliente TEXT,
    foto_cliente TEXT,
    celular TEXT,
    mascota TEXT,
    foto_mascota TEXT,
    motivo TEXT,
    estado TEXT,
    fecha_programada TIMESTAMP,
    fecha_atendida TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT ci.id, CONCAT(c.nombre, ' ', c.apellidos)::text, c.foto::text, c.celular::text, m.nombre::text, m.foto::text, ci.motivo::text, ci.estado::text, ci.fecha_programada, ci.fecha_atendida
    FROM cita AS ci
    INNER JOIN mascota AS m ON ci.mascota_id = m.id
    INNER JOIN cliente AS c ON m.cliente_id = c.id
    WHERE ci.eliminado = '0' and ci.estado = estado_cita
    ORDER BY ci.fecha_programada DESC;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION ingresos_del_dia(fecha DATE)
RETURNS TABLE(hora TIME, precio NUMERIC, servicio TEXT, id INT, cliente TEXT) AS $$
BEGIN
    RETURN QUERY
    SELECT b.fecha_creacion::TIME AS hora, b.precio::NUMERIC AS precio, 'Baño' AS servicio , b.id AS id, CONCAT(c.nombre, ' ', c.apellidos) AS cliente
    FROM banio b
    JOIN mascota m ON b.mascota_id = m.id
    JOIN cliente c ON m.cliente_id = c.id
    WHERE DATE(b.fecha_creacion) = fecha AND b.eliminado = false
    UNION ALL
    SELECT t.fecha_creacion::TIME AS hora, t.precio::NUMERIC AS precio, 'Tratamiento' AS servicio , t.id AS id, CONCAT(c.nombre, ' ', c.apellidos) AS cliente
    FROM tratamiento t
    JOIN mascota m ON t.mascota_id = m.id
    JOIN cliente c ON m.cliente_id = c.id
    WHERE DATE(t.fecha_creacion) = fecha AND t.eliminado = false
    UNION ALL
    SELECT bo.fecha_creacion::TIME AS hora, bo.precio_final::NUMERIC AS precio, 'Venta' AS servicio , bo.id AS id, CONCAT(c.nombre, ' ', c.apellidos) AS cliente
    FROM boleta bo
    JOIN cliente c ON bo.cliente_id = c.id
    WHERE DATE(bo.fecha_creacion) = fecha;
END; $$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION suma_total_ingresos(fecha DATE)
RETURNS TABLE(Total NUMERIC) AS $$
BEGIN
    RETURN QUERY
    SELECT COALESCE(ROUND(SUM(Precio)::numeric, 2), 0) AS Total
    FROM (
        SELECT b.precio AS Precio
        FROM banio b
        WHERE DATE(b.fecha_creacion) = fecha AND b.eliminado = false
        UNION ALL
        SELECT t.precio AS Precio
        FROM tratamiento t
        WHERE DATE(t.fecha_creacion) = fecha AND t.eliminado = false
        UNION ALL
        SELECT bo.precio_final AS Precio
        FROM boleta bo
        WHERE DATE(bo.fecha_creacion) = fecha
    ) AS ingresos;
END; $$
LANGUAGE plpgsql;



