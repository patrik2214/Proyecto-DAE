package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;

public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public Integer generarCodigoMarca() throws Exception{
        strSQL = "SELECT COALESCE(max(codMarca),0)+1 as codigo from marca" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de marca");
        }
        return 0;
    }
    
    public void registrar(Integer cod, String nom, Boolean vig) throws Exception{
        strSQL="insert into MARCA values(" + cod + ",'" + nom + "'," + vig + ")";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public ResultSet buscarMarca(Integer cod) throws Exception{
        strSQL = "select * from marca where codMarca=" + cod ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public void eliminarMarca(Integer cod) throws Exception {
        strSQL="delete from marca where codMarca=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al eliminar la marca");
        }
    }

    public ResultSet listarMarcas() throws Exception{
        strSQL = "select * from marca" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public void modificar(Integer cod, String nom, Boolean vig) throws Exception{
        strSQL="update marca set nomMarca='" + nom + "', vigencia=" + vig + " where codMarca=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public void darBaja(Integer cod) throws Exception{
        strSQL="update marca set vigencia=false where codMarca=" + cod;
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al registrar la marca");
        }
    }
    
    public Integer obtenerCodigoMarca(String nom) throws Exception{
        strSQL = "select codMarca from marca where nommarca='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codMarca");
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
        return 0;
    }
    
    
    public int getCodigo(String nombre) throws Exception{
        strSQL = "SELECT codmarca FROM marca WHERE nommarca='"+nombre+"';" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while (rs.next()){
                return rs.getInt("codmarca");
            }
        } catch (Exception e) {
            throw new Exception("Error al listar marcas");
        }
        return 0;
    }
    
    //29-10-19    
    public boolean registrarSP(String nombre, Boolean vigencia) throws Exception 
    {
        boolean rpta = false;

        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("select f_marca_registrar(?, ?)");
        sentencia.setString(1, nombre);
        sentencia.setBoolean(2, vigencia);
        ResultSet resultado = sentencia.executeQuery();
        if (resultado.next()) 
        {
            rpta = resultado.getBoolean("f_marca_registrar");
        }
        return rpta;
    }
}
