/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBC;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/*
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
*/

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
       
    public ResultSet listarVenta(String dni) throws Exception{
        strSQL="select venta.numventa , venta.fecha, venta.igv, venta.subtotal, venta.total,venta.estadopago from venta inner join cliente on cliente.codcliente=venta.codcliente where venta.baja = false and cliente.dni='" + dni + "'or cliente.ruc='" + dni +"';";
        try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar Venta Pagada");
        }
    }  
     
   public ResultSet listarDetalle(Integer numVen) throws Exception {
       strSQL = "select detalle.numventa,producto.codproducto,detalle.cantidad,detalle.precioventa,detalle.descuento,detalle.subtotal from venta inner join cliente on cliente.codcliente=venta.codcliente inner join detalle on detalle.numventa = venta.numventa inner join producto on producto.codproducto = detalle.codproducto where detalle.numventa =" + numVen ;
       try {
            rs=objConectar.consultarBD(strSQL);
            return rs;
            
        } catch (Exception e) {
            throw new Exception("Error al buscar Detalle de Venta");
        }
    } 
   
   public Integer extraerCodProducto(String nomPro) throws Exception {
        strSQL = "SELECT codproducto FROM producto WHERE nomproducto = '"+ nomPro + "'" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getInt("codproducto");
            }
        } catch (Exception e) {
            throw new Exception("Error al extraer código del Producto");
        }
        return 0;
    }
   
   
   
   
   public void GestionarDevolucion(int codVenta,String motivo, Float monto,JTable tbldetalle) throws Exception{       
      try {         
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();           
            String strSQL1 = "INSERT INTO devolucion VALUES ((SELECT COALESCE(max(numerodev),0)+1 FROM devolucion) , current_date , '"+ motivo + "', " + monto +", 1)";
            sent.executeUpdate(strSQL1);
            int cantidad = tbldetalle.getRowCount();            
            for (int i =0; i<cantidad;i++){
                //String nom = (String) tbldetalle.getValueAt(i, 1);
                    int codpro = (int) tbldetalle.getValueAt(i, 1);
                int cantpro = (int) tbldetalle.getValueAt(i, 2);              
                String strSQL2 = "UPDATE producto set stock=stock+"+cantpro+" where codproducto="+codpro+";";
                String strSQL3 = "UPDATE detalle set baja=true where codproducto="+codpro+" and numventa="+codVenta+"";              
                sent.executeUpdate(strSQL2);
                sent.executeUpdate(strSQL3);              
            }       
            String strSQL5 = "UPDATE venta set baja=true where numventa="+codVenta+"";
            sent.executeUpdate(strSQL5);
            con.commit();
            JOptionPane.showMessageDialog(null, "Devolución Aceptada");            
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar la devolucion");
        }finally{
            objConectar.desconectar();
        } 
       
   } 
   
   public void registrarCambio(Integer codp1,Integer codp2,Integer cant,Integer venta,Integer des) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT cambio(?,?,?,?,?)");
        sentencia.setInt(1, codp1);
        sentencia.setInt(2, codp2 );
        sentencia.setInt(3, cant);
        sentencia.setInt(4, venta);
        sentencia.setInt(5, des);
        ResultSet resultado = sentencia.executeQuery();
    }
   
   
   public int TotalVentas() throws Exception 
    {
        int rpta=0;
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT fn_ventasT()");
        ResultSet resultado = sentencia.executeQuery();
        
        if(resultado.next()){
            rpta=resultado.getInt("fn_ventasT");
            return rpta;
        }
        return rpta;
    }
   
   public float MaxVentas() throws Exception 
    {
       float rpta=0;
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT fn_ventasM()");
        ResultSet resultado = sentencia.executeQuery();
        
        if(resultado.next()){
            rpta=resultado.getFloat("fn_ventasM");
            return rpta;
        }
        return rpta;
    }
   
   public float MinVentas() throws Exception 
    {
       float rpta=0;
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT fn_ventasMi()");
        ResultSet resultado = sentencia.executeQuery();
        
        if(resultado.next()){
            rpta=resultado.getFloat("fn_ventasMi");
            return rpta;
        }
        return rpta;
    }
   
   public float MonVentas() throws Exception 
    {
       float rpta=0;
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT fn_ventasMo()");
        ResultSet resultado = sentencia.executeQuery();
        
        if(resultado.next()){
            rpta=resultado.getFloat("fn_ventasMo");
            return rpta;
        }
        return rpta;
    }
   
      public ResultSet Ventas() throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT * FROM fn_venta()");
        ResultSet resultado = sentencia.executeQuery();
        
        if(resultado.next()){
            return resultado;
        }
        return null;
    }
   
      public ResultSet BUSQUEDA(int v) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT * FROM fn_Mo(?)");
        sentencia.setInt(1, v);
        ResultSet resultado = sentencia.executeQuery();        
        return resultado;
    }                 
    
        public ResultSet ReporteVentas(Date fecha1,Date fecha2) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT * FROM informe_ventas(?,?)");
        sentencia.setDate(1, fecha1);
        sentencia.setDate(2, fecha2);
        ResultSet resultado = sentencia.executeQuery();        
        return resultado;
    } 
        
        
    public float Monto_Total(Integer cod) throws Exception {
        strSQL = "SELECT SUM(subtotal) as monto FROM detalle WHERE numventa= "+ cod + "" ;
        try {
            rs=objConectar.consultarBD(strSQL);
            while(rs.next()){
                return rs.getFloat("monto");
            }
        } catch (Exception e) {
            throw new Exception("Error al extraer código del Producto");
        }
        return 0;
    }

     public void pagocontado(Integer cod) throws Exception 
    {
        boolean valor=false;
        try {
            strSQL="SELECT estadopago FROM venta WHERE numventa=" + cod ;
            
            objConectar.conectar();
            con=objConectar.getCon();
            con.setAutoCommit(false);
            sent=con.createStatement();
            
            while(rs.next()){
                valor=rs.getBoolean("estadopago");
            }
        
            if(valor=false){
                 String strSQL1 = "INSERT INTO pago VALUES ("+cod+",(SELECT COALESCE(max(codpago),0)+1 FROM pago),null,current_date,(SELECT total FROM venta WHERE numventa="+cod+"),0,(SELECT total FROM venta WHERE numventa="+cod+"),'contado',true)";
                 sent.executeUpdate(strSQL1);
                 JOptionPane.showMessageDialog(null, "Pago Registrado");            
            }else{
                JOptionPane.showMessageDialog(null,"No se puede pagar una venta que ya esta cancelada");
            } 
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al registrar la devolucion");
        }finally{
            objConectar.desconectar();
        }   
    }
   
    public ResultSet comprobante(Integer cod) throws Exception 
    {
        objConectar.conectar();
        Connection con = objConectar.getCon();
        CallableStatement sentencia = con.prepareCall("SELECT *FROM comprobante(?)");
        sentencia.setInt(1, cod);
        ResultSet resultado = sentencia.executeQuery();
        return resultado;
    }
        
        
}
