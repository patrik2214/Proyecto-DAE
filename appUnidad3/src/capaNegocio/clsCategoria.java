package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;

public class clsCategoria {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
    public Integer generarCodigoCategoria() throws Exception{
        strSQL = "SELECT COALESCE(max(codCategoria),0)+1 as codigo from categoria" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de categoría");
        }
        return 0;
    }
    
    public void registrar(Integer cod, String nom,String des, Boolean vig) throws Exception{
        strSQL="insert into CATEGORIA values(" + cod + ",'" + nom + "','" + des + "'," + vig + ")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la categoría");
        }
    }
    
    public ResultSet buscarCategoria(Integer cod) throws Exception{
        strSQL = "select * from CATEGORIA where codCategoria=" + cod ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }
    
    public void eliminarCategoria(Integer cod) throws Exception {
        Integer cant=0;
        try {
            strSQL="select count(*) as total from PRODUCTO where codCategoria=" + cod;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                cant=rs.getInt("total");
            }
            if(cant>0){
                strSQL="update PRODUCTO set vigencia=false where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
                strSQL="update CATEGORIA set vigencia=false where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
            }else{
                strSQL="delete from CATEGORIA where codCategoria=" + cod;
                sent.executeUpdate(strSQL);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar la categoría");
        }finally{
            objConectar.desconectar();
        }
    }

    public ResultSet listarCategorias() throws Exception{
        strSQL = "select * from CATEGORIA" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }
    
    public void modificar(Integer cod, String nom, String des,Boolean vig) throws Exception{
        try {
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="update CATEGORIA set nomCategoria='" + nom + "',descripcion='" + des + "', vigencia=" + vig + " where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            strSQL="update PRODUCTO set vigencia=" + vig + " where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al modificar la categoría");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void darBaja(Integer cod) throws Exception{
        try {
            objConectar.conectar();
            con = objConectar.getCon();
            con.setAutoCommit(false);
            strSQL="update CATEGORIA set vigencia=false where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            strSQL="update PRODUCTO set vigencia=false where codCategoria=" + cod;
            objConectar.ejecutarBD(strSQL);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al dar de baja a la categoría");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public Integer obtenerCodigoCategoria(String nom) throws Exception{
        strSQL = "select codCategoria from categoria where nomcategoria='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codCategoria");
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria");
        }
        return 0;
    }
    
    
    public int getCodigo(String nombre) throws Exception{
        strSQL = "SELECT codcategoria FROM categoria WHERE nomcategoria='"+nombre+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codcategoria");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar codigo categoria");
        }
        return 0;
    }
}