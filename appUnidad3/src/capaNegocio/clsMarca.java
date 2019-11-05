package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;
import javax.swing.JOptionPane;

public class clsMarca {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
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
        try {
            objConectar.conectar();
            Connection con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("INSERT INTO marca VALUES(?,?,?)");
            sentencia.setInt(1,cod);
            sentencia.setString(2,nom);
            sentencia.setBoolean(3, vig);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
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
        int cantidad = 0;
        try {
            strSQL="SELECT COUNT(*) AS cantidad FROM marca WHERE codMarca=" + cod ;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            
            if(cantidad>0){
                String strSQL1="UPDATE producto SET vigencia=false WHERE codmarca="+cod+"";
                sent.executeUpdate(strSQL1);
                String strSQL2="UPDATE FROM marca SET vigencia=false WHERE codMarca=" + cod; 
                sent.executeUpdate(strSQL2);
            }else{
                String strSQL2="DELETE FROM marca WHERE codMarca=" + cod;
                sent.executeUpdate(strSQL2);
            }    
            con.commit();
            JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar la marca");
        }finally{
            objConectar.desconectar();
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
}
