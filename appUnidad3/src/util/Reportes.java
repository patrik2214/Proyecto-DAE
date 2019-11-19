package util;

import capaDatos.clsJDBC;
import java.util.Map;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

public class Reportes 
{
    
    public static final String RUTA_REPORTES = "C:\\Users\\laboratorio_computo\\Documents\\Paola Castro\\Tarea-de-DAE\\appUnidad3\\src\\Reportes\\";
    public static final String NOMBRE_IMPRESORA = "EPSON TM-T20II Receipt";
    
    
    public JRViewer reporteInterno(String archivoReporte, Map<String,Object> parametros) throws Exception{
        try 
        {
            //URL rutaR = new URL(getClass().getResource("/reportes/"+archivoReporte).toString());
            //JasperPrint reporte = JasperFillManager.fillReport(rutaR.getPath(), parametros, this.abrirConexion());
            clsJDBC objConexion = new clsJDBC();
            objConexion.conectar();
            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, 
                    parametros, 
                    objConexion.getCon()
            );
            JRViewer visor =new JRViewer(reporte);
            return visor;
        } catch (JRException e ) 
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.out.println(e.getMessage());
        
        }
        
        return null;
        
    }

    
    public JRViewer reporteInternoHorizontal(String archivoReporte, Map<String,Object> parametros) throws Exception{
        try {
            //URL rutaR = new URL(getClass().getResource("/reportes/"+archivoReporte).toString());
            //JasperPrint reporte = JasperFillManager.fillReport(rutaR.getPath(), parametros, this.abrirConexion());
            clsJDBC objConexion = new clsJDBC();
            objConexion.conectar();
            JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, objConexion.getCon());
            JRViewer visor =new JRViewer(reporte);
            
            //OrientationEnum.LANDSCAPE = Horizontal
            reporte.setOrientation(OrientationEnum.LANDSCAPE);
            return visor;
            
        } catch (JRException e ) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return null;
        
    }
    
    
     public JasperPrint reporte(String archivoReporte, Map<String, Object> parametros) throws Exception 
    {
        clsJDBC objConexion = new clsJDBC();
        objConexion.conectar();
        JasperPrint reporte = JasperFillManager.fillReport(Reportes.RUTA_REPORTES + archivoReporte, parametros, objConexion.getCon());
        return reporte;
    }

    public static void imprimirReporte(String archivoReporte, Map<String, Object> parametros, boolean dialogPrint) throws Exception {
        // Reporte
        JasperPrint reporteJP = new Reportes().reporte(archivoReporte, parametros);

        // Impresora
        PrintServiceAttributeSet impresora = new HashPrintServiceAttributeSet();
        impresora.add(new PrinterName(Reportes.NOMBRE_IMPRESORA, null));

        // Configuracion
        SimplePrintServiceExporterConfiguration configuracion = new SimplePrintServiceExporterConfiguration();
        configuracion.setPrintServiceAttributeSet(impresora);
        configuracion.setDisplayPageDialog(false);
        configuracion.setDisplayPrintDialog(dialogPrint);

        // Imprimir
        JRPrintServiceExporter imprimirServicio = new JRPrintServiceExporter();
        imprimirServicio.setExporterInput(new SimpleExporterInput(reporteJP));
        imprimirServicio.setConfiguration(configuracion);
        imprimirServicio.exportReport();
    }
}
