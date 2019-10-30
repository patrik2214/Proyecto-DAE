select * from usuario;

-- Función: Retorna el total de usuarios
create or replace function f_usuarios_total() returns int as
$$
declare
	i_resultado int;
begin
	select count(*) into  i_resultado from usuario;
	return i_resultado;
end;
$$
language 'plpgsql';

-- select f_usuarios_total();

-- Función: Registrar una marca
create or replace function f_marca_registrar(p_nommarca varchar(30), p_vigencia boolean) returns boolean as
$$
declare
	i_id_insertar int;
begin
	select coalesce(max(codmarca)+1, 1) into i_id_insertar from marca;
	insert into marca(codmarca, nommarca, vigencia) values(i_id_insertar, p_nommarca, p_vigencia);
	return true;
	exception when others then return false;
end;
$$
language 'plpgsql';
-- select f_marca_registrar('TOYOTA', true)

SELECT * FROM MARCA








--PA que genere el código de la próxima venta
CREATE OR REPLACE FUNCTION fn_generar_codVenta() RETURNS int AS
$$
DECLARE
	codigo int;
BEGIN
	SELECT COALESCE(max(numventa),0)+1 into codigo FROM venta;
	RETURN codigo;
END
$$ language 'plpgsql';

SELECT fn_generar_codVenta() AS codVenta;



--PA que retorne todos los productos vigentes mayores a un precio
CREATE OR REPLACE FUNCTION fn_listarProductosPrecioMayor( pre numeric(8,2) ) 
RETURNS TABLE(codproducto int, nomproducto varchar, descripcion varchar, precio numeric(8,2), stock int, 
		vigencia boolean, codmarca int, codcategoria int) AS
$$
DECLARE
BEGIN
	return query
	select * from producto p where p.vigencia = true and p.precio > pre;
END
$$ language 'plpgsql';

select * from fn_listarProductosPrecioMayor(899)




CREATE OR REPLACE FUNCTION fn_listarProductosPrecioMayor2(pre numeric(8,2)) RETURNS SETOF PRODUCTO AS
$$
DECLARE
BEGIN
	return query
	select * from producto p where p.vigencia = true and p.precio > pre;
END
$$ language 'plpgsql';

select * from fn_listarProductosPrecioMayor2(899)

select * from producto

CREATE OR REPLACE FUNCTION fn_LISTARPRO( precioxd numeric(8,2) ) RETURNS 
			TABLE(codigoproducto int, nombreproducto varchar, descripcionpro varchar, preciopro numeric(8,2), 
			stockXD int, codigomarca int, codigocategoria int) AS
$$
DECLARE
BEGIN
	return query
		select codproducto,nomproducto,descripcion,precio,stock,codmarca,codcategoria
		 from producto  where vigencia = true and precio > precioxd;
END
$$ language 'plpgsql';

select * from fn_LISTARPRO( 800 ) 
