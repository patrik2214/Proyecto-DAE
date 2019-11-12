package util;

import capaDatos.clsJDBC;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.swing.JRViewer;

public class Reportes 
{
    
    public static final String RUTA_REPORTES = "C:\\Users\\laboratorio_computo\\Documents\\Paola Castro\\Tarea-de-DAE\\appUnidad3\\src\\Reportes\\";
    
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
}
