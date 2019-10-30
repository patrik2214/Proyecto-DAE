//18-09-2019
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;

public class clsProducto {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public ResultSet listarProductos() throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    public Integer generarCodigoProducto() throws Exception{
        strSQL = "SELECT COALESCE(max(codProducto),0)+1 as codigo from producto" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código del producto");
        }
        return 0;
    }
    
    public void registrarProducto(Integer cod, String nom, String des, Double pre, Integer sto, Boolean vig, Integer codMar, Integer codCat) throws Exception{
        strSQL="insert into Producto values(" + cod + ",'" + nom + "','" + des + "'," + pre + "," + sto + "," + vig + "," + codMar + "," + codCat + ")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al registrar un producto!");
        }
            
    }
    
    public void modificarProducto(Integer cod, String nom, String des, Double pre, Integer sto, Boolean vig, Integer codMar, Integer codCat) throws Exception{
        strSQL="update Producto set nomProducto='" + nom + "', descripcion='" + des + "', precio=" + pre + ", stock=" + sto + ", vigencia=" + vig + ", codMarca=" + codMar + ", codCategoria=" + codCat + " where codProducto=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al modificar un producto!");
        }
    }
    
    public void eliminarProducto(Integer cod) throws Exception{
        strSQL="delete from Producto where codProducto=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al modificar un producto!");
        }
            
    }
    
    public void darBajaProducto(Integer cod) throws Exception{
        strSQL="update Producto set vigencia=false where codProducto=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al modificar un producto!");
        }
            
    }

    public ResultSet buscarProducto(Integer cod) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where codProducto=" + cod;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    public ResultSet listarProductosPorCategoria(Integer codCat) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where P.codCategoria=" + codCat + " order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    public ResultSet listarProductosPorMarca(Integer codMar) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where P.codMarca=" + codMar + " order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    public ResultSet listarProductosMarca_Cat(Integer codMar, Integer codCat) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where P.codMarca=" + codMar + " and P.codCategoria=" + codCat + " order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    //select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where nomproducto like 'm%' order by codProducto 
    public ResultSet listarProductosPorNombre(String nom) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where nomproducto like '" + nom + "%' order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
   
    public ResultSet listarProductosPorprecio(Double precioMin, Double precioMax) throws Exception{
        strSQL="select P.*,M.nomMarca, C.nomCategoria from producto P inner join Marca M on P.codMarca=M.codMarca inner join Categoria C on P.codCategoria=C.codCategoria where precio>=" + precioMin + " and precio<=" + precioMax +  " order by codProducto ";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception(e.getMessage() + ": Error al consultar productos.") ;
        }
    }
    
    //MÉTODOS PARA FILTRAR PRODUCTOS
    public int getStock(int cod) throws Exception{
        strSQL = "SELECT stock FROM producto WHERE codproducto = " + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener stock");
        }
        return 0;
    }
    
    public ResultSet filtrar(String nom) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND vigencia=true) p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public int getPrecioMax() throws Exception{
        strSQL = "SELECT MAX(precio) FROM producto;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener precios");
        }
        return 0;
    }
    
    public ResultSet filtrar(String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrarMarca(int marca, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrarCategoria(int categoria, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public ResultSet filtrar(int marca, int categoria, String nom, int min, int max) throws Exception{
        strSQL = "SELECT p.*, m.nommarca, c.nomcategoria FROM (SELECT * FROM producto WHERE codmarca = " + marca + " AND codcategoria = " + categoria + " AND UPPER(nomproducto) LIKE UPPER('%" + nom + "%') AND precio BETWEEN " + min + " AND " + max + ") p INNER JOIN marca m ON p.codmarca = m.codmarca INNER JOIN categoria c ON p.codcategoria = c.codcategoria;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al filtrar productos");
        }
    }
    
    public String getNombre(int cod) throws Exception{
        strSQL = "SELECT nomproducto FROM producto WHERE codproducto = " + cod + ";";
        try {
            rs=objConectar.consultarBD(strSQL);
            if(rs.next()){
                return rs.getString("nomproducto");
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener nombre");
        }
        return null;
    }
    
    public void actualizarProducto(int cod, int stock)throws Exception{
        strSQL = "UPDATE producto SET stock=" + stock + " WHERE codproducto = " + cod + ";";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al actualizar stock");
        }
    }
}
