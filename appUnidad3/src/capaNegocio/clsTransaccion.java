
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.*;

public class clsTransaccion {
    clsJDBC objConectar = new clsJDBC();
    String strSQL, strSQL1,strSQL2,strSQL3;

    public void ejecutarSentencias() throws Exception{
        try {
            strSQL="insert into marca values(8,'HP',true);";
            objConectar.ejecutarBD(strSQL);
            strSQL="insert into categoria values(5,'Parlantes','Dispositivos de audio',true)";
            objConectar.ejecutarBD(strSQL);
            strSQL="insert into producto values(8,'Parlantes Wi','Parlantes inhalambricos',250,20,true,8,5)";
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception ("Error al ejecutar sentencias...");
        }
    }
    
    public void ejecutarSentenciasTransacciones() throws Exception{
        try {
            strSQL1="insert into marca values(8,'HP',true);";
            strSQL2="insert into categoria values(5,'Parlantes','Dispositivos de audio',true)";            
            strSQL3="insert into producto values(8,'Parlantes Wi','Parlantes inhalambricos',250,20,true,18,15)";            
            objConectar.ejecutarBDTransacciones(strSQL1,strSQL2,strSQL3);
        } catch (Exception e) {
            throw new Exception ("Error al ejecutar sentencias...");
        }
    }
}
