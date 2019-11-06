/*03-Set 2019*/
package capaDatos;

import java.sql.*;

public class clsJDBC {
    private String driver, url,user,password;
    private Connection con;
    private Statement sent=null;
    private Statement sent1=null;
    private Statement sent2=null;

    //Constructor
    public clsJDBC() {
        this.driver = "org.postgresql.Driver";
        this.url = "jdbc:postgresql://localhost:5432/bdApp3";
        this.user = "postgres";
        //Cambiar la contraseña
        this.password = "123456789";
        this.con = null;
    }    
    //Conectar
    public void conectar() throws Exception{
        try{
           Class.forName(driver);
           con=DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException | SQLException ex){
            throw new Exception("Error al conectar a la BD!");
        }
    }
    //Desconectar
    public void desconectar() throws Exception{
        try{
           con.close();
        } catch (SQLException ex) {
             throw new Exception("Error al desconectar de la BD!");
        }
    }
    //Ejecutar una consulta SELECT
    public ResultSet consultarBD (String strSQL) throws Exception{
        ResultSet rs=null;
        try {
            conectar();
            sent=con.createStatement();
            rs=sent.executeQuery(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al ejecutar consulta");
        } finally{
            if (con!=null){
                desconectar();
            }
        }
    }
    
    //Ejecutar insert, update y delete
    public void ejecutarBD (String strSQL) throws Exception{
        try {
            conectar();
            sent=con.createStatement();
            sent.executeUpdate(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al ejecutar consulta");
        } finally{
            if (con!=null){
                desconectar();
            }
        }
    }

    public Connection getCon() {
        return con;
    }
    
    public void ejecutarBDTransacciones (String strSQL1,String strSQL2,String strSQL3) throws Exception{
        try {
            conectar();
            con.setAutoCommit(false);
            sent=con.createStatement();
            sent.executeUpdate(strSQL1);
            sent1=con.createStatement();
            sent1.executeUpdate(strSQL2);
            sent2=con.createStatement();
            sent2.executeUpdate(strSQL3);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar consulta");
        } finally{
            if (con!=null){
                desconectar();
            }
        }
    }
}
