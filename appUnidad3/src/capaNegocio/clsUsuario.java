/* 03 Set 2019 */
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;
import javax.swing.JOptionPane;

public class clsUsuario {
    //Crear instancia de la clase clsJDBC
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    
    public String login(String usu, String con) throws Exception{
        strSQL = "select nombrecompleto from usuario where nomusuario='" + usu + "' and clave='" + con + "' and estado=true";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("nombrecompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al iniciar sesión..");
        }
        return "";
    }
    
    public Boolean validarVigencia(String usu) throws Exception{
    strSQL = "select estado from usuario where nomusuario='" + usu + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getBoolean("estado");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar usuario..");
        }
        return false;
     }
    
    public String obtenerPreguntaSecreta(String usu) throws Exception{
        strSQL = "select pregunta from usuario where nomusuario='" + usu + "'";
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("pregunta");
            }
        } catch (Exception e) {
            throw new Exception("Error al consultar pregunta secreta..");
        }
        return "";
    }
    
    public String validarPreguntaSecreta(String usu, String rpta) throws Exception{
    strSQL = "select nombrecompleto from usuario where nomusuario='" + usu + "' and respuesta='" + rpta + "' and estado=true" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getString("nombrecompleto");
            }
        } catch (Exception e) {
            throw new Exception("Error al validar pregunta secreta..");
        }
        return "";
     }
    
    public void cambiarContraseña(String con, String nuevaCon, String nombre) throws Exception{
        strSQL="update usuario set clave='" + nuevaCon + "' where nombrecompleto='" + nombre + "' and clave='" + con + "'";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al cambiar la contraseña..");
        }
    }
}
