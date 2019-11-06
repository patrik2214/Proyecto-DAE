/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaCliente;


import capaNegocio.clsVenta;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 INTEGRANTES:
   - BENEL RAMIREZ, Sara
   - CASTRO FERNANDEZ, Paola   
   - VILCHEZ VILLEGAS, José Carlos
   - YOMONA PARRAGUEZ, Cinthya
 */

public class jdCuotasCredito extends javax.swing.JDialog {
 
    clsVenta objcuota = new clsVenta();
    boolean pagado=false;
    String[][] cuotas;
    String montoTotal;
    String documento;
    /**
     * Creates new form JDPago2
     */
    public jdCuotasCredito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarControles();
        Gruporbt.add(rbtMensual);
        Gruporbt.add(rbtSemanal);
        tblCuotas.setEnabled(false);
        rbtSemanal.setSelected(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gruporbt = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroVenta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblTituloVuelto = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        spnNumCuotas = new javax.swing.JSpinner();
        rbtSemanal = new javax.swing.JRadioButton();
        rbtMensual = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCuotas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Venta N°:");

        txtNumeroVenta.setEditable(false);

        jLabel2.setText("Cliente:");

        txtNombre.setEditable(false);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel3.setText("DNI/RUC:");

        txtDocumento.setEditable(false);
        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        jLabel4.setText("Monto Total:");

        txtMonto.setEditable(false);

        jLabel5.setText("Numero de Cuotas:");

        lblTituloVuelto.setText("Modalidad:");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        spnNumCuotas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnNumCuotasStateChanged(evt);
            }
        });

        rbtSemanal.setText("Cuotas Semanales");
        rbtSemanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtSemanalActionPerformed(evt);
            }
        });

        rbtMensual.setText("Cuotas Mensuales");
        rbtMensual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtMensualActionPerformed(evt);
            }
        });

        tblCuotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblCuotas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTituloVuelto)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtMensual)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNumeroVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                .addComponent(txtNombre)
                                .addComponent(txtDocumento)
                                .addComponent(txtMonto)
                                .addComponent(spnNumCuotas)
                                .addComponent(rbtSemanal)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNumeroVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(spnNumCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloVuelto)
                    .addComponent(rbtSemanal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtMensual)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        int i=0;
            while (i>=0){
                try {
                    objcuota.RegistarCredito(cuotas[i][0], cuotas[i][1], cuotas[i][2], cuotas[i][7]);
                    i++;
                } catch (Exception e) {
                    i=-1;
                }
            }

        dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void rbtSemanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtSemanalActionPerformed
        // TODO add your handling code here:
        generarCuotas();
    }//GEN-LAST:event_rbtSemanalActionPerformed

    private void rbtMensualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtMensualActionPerformed
        // TODO add your handling code here:
        generarCuotas();
    }//GEN-LAST:event_rbtMensualActionPerformed

    private void spnNumCuotasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnNumCuotasStateChanged
        // TODO add your handling code here:
        if((int)spnNumCuotas.getValue()<0){
            spnNumCuotas.setValue(0);
        }
        generarCuotas();
        if ((int)spnNumCuotas.getValue()>0) {
            btnGuardar.setVisible(true);
        }
    }//GEN-LAST:event_spnNumCuotasStateChanged

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        int deuda = 0;
        try {
            deuda=objcuota.saberdeuda(documento);
        } catch (Exception ex) {
            Logger.getLogger(jdCuotasCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(deuda==1){
            JOptionPane.showMessageDialog(this, "El cliente tiene deudas pendientes de pago");
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "El cliente NO tiene deudas pendientes de pago");
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       
    }//GEN-LAST:event_formWindowActivated

    private void generarCuotas(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("N° Cuota");
        modelo.addColumn("Fecha");
        
        tblCuotas.setModel(modelo);
        
        Calendar fecha = new GregorianCalendar();
        int cant = (int) spnNumCuotas.getValue();
        
        cuotas = new String[cant][8];
        
        Float apagar;
        Float mon= Float.parseFloat(montoTotal);
        apagar = (mon/cant);
        
        
        for(int i=0; i<cant; i++){
            String newFecha = String.format("%02d-%02d-%04d",fecha.get(Calendar.DAY_OF_MONTH),fecha.get(Calendar.MONTH)+1,fecha.get(Calendar.YEAR));
            modelo.addRow(new Object[]{i+1, newFecha});
            
            cuotas[i] = new String[]{txtNumeroVenta.getText(), String.valueOf(i+1), newFecha, "null", "false", "null", "null", String.valueOf(apagar)};
            
            if(rbtSemanal.isSelected()){
                fecha.add(fecha.DAY_OF_YEAR, 7);
            }else{
                fecha.add(fecha.MONTH, 1);
            }
        }
    }
        
    private void limpiarControles(){
        btnGuardar.setVisible(false);
    }
    
    
    public boolean getPago(){
        return pagado;
    }
    
    public String[][] getCuotas(){
        return cuotas;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Gruporbt;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTituloVuelto;
    private javax.swing.JRadioButton rbtMensual;
    private javax.swing.JRadioButton rbtSemanal;
    private javax.swing.JSpinner spnNumCuotas;
    private javax.swing.JTable tblCuotas;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumeroVenta;
    // End of variables declaration//GEN-END:variables

    
   
}
