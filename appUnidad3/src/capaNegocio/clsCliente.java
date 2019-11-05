//25/09/2019
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;
import javax.swing.JOptionPane;

public class clsCliente {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;

    public ResultSet listarTipoClientes() throws Exception{
        strSQL = "select * from TIPO_CLIENTE" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }    
    
    public Integer obtenerCodigoTipoCliente(String nom) throws Exception{
        strSQL = "select codTipo from TIPO_CLIENTE where nombre='" + nom + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            if (rs.next()) return rs.getInt("codTipo");
        } catch (Exception e) {
            throw new Exception("Error al buscar tipo cliente");
        }
        return 0;
    }
    
    public ResultSet listarClientes() throws Exception{
        strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar categoría");
        }
    }  
    
    public Integer generarCodigoCliente() throws Exception{
        strSQL = "SELECT COALESCE(max(codCliente),0)+1 as codigo from CLIENTE" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de cliente");
        }
        return 0;
    }
    
    public void registrar(Integer cod, Integer codTipo, String dni, String ruc, String nom, String tel, String cor, String dir, Boolean vig) throws Exception{
        try {
            if (codTipo==1){
                
            objConectar.conectar();
            Connection con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,cod);
            sentencia.setInt(2,codTipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, cor);
            sentencia.setString(8,dir);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
            if (codTipo==2){
                
            objConectar.conectar();
            Connection con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,cod);
            sentencia.setInt(2,codTipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, cor);
            sentencia.setString(8,dir);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate();  
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
            if (codTipo==3){
                
            objConectar.conectar();
            Connection con = objConectar.getCon();
            CallableStatement sentencia = con.prepareCall("INSERT INTO clientes VALUES(?,?,?,?,?,?,?,?,?)");
            sentencia.setInt(1,cod);
            sentencia.setInt(2,codTipo);
            sentencia.setString(3, dni);
            sentencia.setString(4, null); //"'null'"
            sentencia.setString(5, nom);
            sentencia.setString(6, tel);
            sentencia.setString(7, cor);
            sentencia.setString(8,dir);
            sentencia.setBoolean(9, vig);
            sentencia.executeUpdate(); 
            JOptionPane.showMessageDialog(null, "Registrado Correctamente"); 
            
            }
            
        } catch (Exception e) {
            throw new Exception("Error al registrar Producto");
        }finally{
            objConectar.desconectar();
        } 
        
    }
    
    public ResultSet buscarCliente(Integer cod) throws Exception{
        strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where codCliente=" + cod ;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    public void eliminarCliente(Integer cod) throws Exception {
        Integer cantidad=0;
        Integer cantidad2=0;
        try {
            strSQL="SELECT COUNT(*) AS cantidad FROM venta WHERE codcliente=" + cod ;
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            
            while(rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            strSQL="SELECT COUNT(*) AS cantidad FROM venta WHERE estadopago=false and codcliente=" + cod ;
            rs=sent.executeQuery(strSQL);
            while(rs.next()){
                cantidad2=rs.getInt("cantidad");
            }
            
            if(cantidad>0){
                if (cantidad2>0){
                  JOptionPane.showMessageDialog(null,"El cliente tiene deudas pendientes, no puede ser eliminado");
                }else{
                    String strSQL1="UPDATE cliente set vigencia=false WHERE codcliente="+cod+"";
                    JOptionPane.showMessageDialog(null, "Eliminado Correctamente DB");
                    sent.executeUpdate(strSQL1);
                }
            }else{
                String strSQL1="DELETE FROM cliente WHERE codcliente=" + cod;
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                sent.executeUpdate(strSQL1);
            }    
            con.commit();
            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al eliminar al cliente");
        }finally{
            objConectar.desconectar();
        } 
    }
    
    public ResultSet buscarClienteDniRuc(String cod, Boolean tipo) throws Exception{
        if (tipo){
            strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where dni='" + cod + "'";
        }else{
            strSQL = "select * from CLIENTE C inner join TIPO_CLIENTE T on C.codTipo=T.codTipo where ruc='" + cod + "'";
        }
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar cliente");
        }
    }
    
    
     public void registrarC(String dni, String ruc, String nombre,String telefono,String correo ,String direccion ,Boolean vigencia ,int tipo ) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT fn_cliente(?,?,?,?,?,?,?,?)");
        sentencia.setString(1, dni);
        sentencia.setString(2, ruc );
        sentencia.setString(3, nombre);
        sentencia.setString(4, telefono);
        sentencia.setString(5, correo);
        sentencia.setString(6, direccion);
        sentencia.setBoolean(7, vigencia);
        sentencia.setInt(8, tipo);
        ResultSet resultado = sentencia.executeQuery();
        
    }
    
    
}
