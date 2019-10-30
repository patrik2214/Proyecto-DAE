/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBC;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;

public class clsVenta {
    clsJDBC objConectar = new clsJDBC();
    String strSQL;
    ResultSet rs=null;
    Connection con=null;
    Statement sent;
    
    public Integer generarCodigoVenta() throws Exception{
        strSQL = "SELECT COALESCE(max(numventa),0)+1 AS codigo FROM venta;" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de venta");
        }
        return 0;
    }
    
    public void registrar(String cod, String total, String subtotal, String igv, boolean tipo, String cliente, JTable tblDetalle) throws Exception{
        try {
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            strSQL = "INSERT INTO venta VALUES (" + cod + ", CURRENT_DATE, " + total + ", " + subtotal + ", " + igv + ", " + tipo + ", false, " + cliente + " );";
            sent.executeUpdate(strSQL);
            int ctd = tblDetalle.getRowCount();
            for (int i=0; i<ctd; i++){
                String descuento = tblDetalle.getValueAt(i, 4).toString();
                
                strSQL = "INSERT INTO detalle VALUES (" + cod + ", " + tblDetalle.getValueAt(i, 0).toString() + ", " + tblDetalle.getValueAt(i, 3).toString() + ", " + tblDetalle.getValueAt(i, 5).toString() + ", " + descuento.substring(0, descuento.length()-1) +", " + tblDetalle.getValueAt(i, 6).toString() +");";
                sent.executeUpdate(strSQL);
                
                strSQL = "UPDATE producto SET stock=stock-" + Integer.parseInt(tblDetalle.getValueAt(i, 3).toString()) + " WHERE codproducto = " + Integer.parseInt(tblDetalle.getValueAt(i, 0).toString()) + ";";
                sent.executeUpdate(strSQL);
            }
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al guardar Venta");
        }finally{
            objConectar.desconectar();
        }
    }
    
    public void registrarDetalle(String venta, String prod, String cant, String preVen, String desc, String sub) throws Exception{
        strSQL = "INSERT INTO detalle VALUES (" + venta + ", " + prod + ", " + cant + ", " + preVen + ", " + desc +", " + sub +");";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al guardar el detalle de venta");
        }
    }

    //para listar todas las ventas pendientes de pago - JDPAGO
    public ResultSet listarVentaPagoPendiente() throws Exception{
        strSQL = "SELECT * FROM venta WHERE estadopago=false;";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar venta");
        }
    }
    
    //para listar las ventas pendientes de pago por cliente - JDPAGO
    public ResultSet listarVentaPagoPendiente(String codcliente) throws Exception{
        strSQL = "SELECT * FROM venta WHERE estadopago=false and codcliente="+codcliente+";";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar ventas");
        }
    }
    
    //para listar las ventas por fecha
    public ResultSet listarVenta(Date fecha) throws Exception{
        strSQL = "SELECT * FROM venta WHERE fecha='"+fecha+"';";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar marca");
        }
    }
    
    public ResultSet buscarVenta(Integer numVenta) throws Exception{
        strSQL = "SELECT V.*,C.dni, C.ruc FROM venta V inner join cliente C on V.codcliente=C.codcliente WHERE numventa="+ numVenta;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al buscar venta");
        }
    }
    
       public ResultSet listarDetalleVenta(Integer numVenta) throws Exception{
        strSQL = "sELECT D.*,P.nomproducto FROM detalle D inner join Producto P on D.codproducto=P.codproducto WHERE numventa="+ numVenta;
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar detalle");
        }
    }
}
