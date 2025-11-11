    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formulario;

import Clases.Clientes;
import Clases.ComboBox;
import Clases.Producto;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ASUS
 */
public class ABCclientes extends javax.swing.JFrame {

    private DefaultTableModel _model;
    private JTable _Tabla;
    private int _id;
    private Clientes _cliente = null; // antes era Producto

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ABCclientes.class.getName());

    public ABCclientes() {
        initComponents();
        _cliente = new Clientes(); // se inicializa correctamente
        CrearModelo();
        CargarTabla();
    }

    private void CrearModelo() {
        String[] nombreColumnas = {"Id", "Nombre", "DPI", "Correo", "Teléfono"};
        _model = new DefaultTableModel(nombreColumnas, 0);
        _Tabla = new JTable(_model);
        JScrollPane scroll = new JScrollPane(_Tabla);

        _Tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                CargarDatos();
            }
        });

        // Limpiar el panel antes
        jPanelTabla.removeAll();
        jPanelTabla.setLayout(new java.awt.BorderLayout());
        jPanelTabla.add(scroll, java.awt.BorderLayout.CENTER);
        jPanelTabla.revalidate();
        jPanelTabla.repaint();
    }

    private void CargarTabla() {
        while (_model.getRowCount() > 0) {
            _model.removeRow(0);
        }

        try {
            ResultSet rs = _cliente.ObtenerClientes(); // Cambiado a clientes
            while (rs.next()) {
                Vector<Object> cliente = new Vector<>();
                cliente.add(rs.getInt("idcliente"));
                cliente.add(rs.getString("nombre"));
                cliente.add(rs.getString("dpi"));
                cliente.add(rs.getString("correo"));
                cliente.add(rs.getString("telefono"));
                _model.addRow(cliente);
            }
            rs.close();
        } catch (Exception ex) {
            System.out.println("Error al cargar tabla: " + ex.getMessage());
        }
    }

    private void CargarDatos() {
        _id = 0;
        try {
            int filaSeleccionada = _Tabla.getSelectedRow();
            if (filaSeleccionada >= 0) {
                _id = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
                String nombre = _Tabla.getValueAt(filaSeleccionada, 1).toString();
                String dpi = _Tabla.getValueAt(filaSeleccionada, 2).toString();
                String correo = _Tabla.getValueAt(filaSeleccionada, 3).toString();
                String telefono = _Tabla.getValueAt(filaSeleccionada, 4).toString();

                jTextFieldNombre.setText(nombre);
                jTextFieldDPI.setText(dpi);
                jTextFieldCorreo.setText(correo);
                jTextFieldTelefono.setText(telefono);
            }
        } catch (Exception ex) {
            System.out.println("Error al seleccionar fila: " + ex.getMessage());
        }
    }

    private void Actualizar() {
        if (_id == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Clientes cliente = new Clientes(_id);
            cliente.setNombre(jTextFieldNombre.getText());
            cliente.setDpi(jTextFieldDPI.getText());
            cliente.setCorreo(jTextFieldCorreo.getText());
            cliente.setTelefono(jTextFieldTelefono.getText());

            if (cliente.Guardar(_id)) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                LimpiarCampos();
                CargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            System.out.println("Error al actualizar: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error al actualizar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Eliminar() {
        if (_id != 0) {
            int o = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este cliente?", "Advertencia", JOptionPane.YES_NO_OPTION);
            if (o == JOptionPane.YES_OPTION) {
                Clientes cliente = new Clientes(_id);
                if (cliente.Eliminar(_id)) { // corregido: antes llamaba a _producto
                    JOptionPane.showMessageDialog(this, "El cliente se eliminó correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                    CargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void LimpiarCampos() {
        jTextFieldNombre.setText("");
        jTextFieldDPI.setText("");
        jTextFieldCorreo.setText("");
        jTextFieldTelefono.setText("");
        _id = 0;
    }

   /**
     * Creates new form ABCclientes
     */
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldDPI = new javax.swing.JTextField();
        jTextFieldCorreo = new javax.swing.JTextField();
        jTextFieldTelefono = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jPanelTabla = new javax.swing.JPanel();
        jButtonActualizar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Catalogo de clientes");

        jLabel3.setText("Nombre");

        jLabel4.setText("DPI");

        jLabel5.setText("Correo");

        jLabel6.setText("Telefono");

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTablaLayout = new javax.swing.GroupLayout(jPanelTabla);
        jPanelTabla.setLayout(jPanelTablaLayout);
        jPanelTablaLayout.setHorizontalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButtonActualizar.setText("Actualizar");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonActualizar)
                                .addGap(68, 68, 68)
                                .addComponent(jButtonGuardar)
                                .addGap(42, 42, 42)
                                .addComponent(jButtonEliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                    .addComponent(jTextFieldDPI, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldTelefono))))
                        .addGap(82, 82, 82)
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardar)
                    .addComponent(jButtonActualizar)
                    .addComponent(jButtonEliminar))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
       Guardar();                                          
                                                 
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
    Actualizar();        
    }//GEN-LAST:event_jButtonActualizarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_jButtonEliminarActionPerformed
    private void LimpiarVariables() {
        _id=0;
        
        // Limpia los campos
        jTextFieldNombre.setText("");
        jTextFieldDPI.setText("");
        jTextFieldCorreo.setText("");
        jTextFieldTelefono.setText("");
        
        JOptionPane.showMessageDialog(rootPane, "Producto guardado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        
        // Actualiza la tabla después de guardar
        _model.setRowCount(0);  // Limpia las filas actuales
        CargarTabla();          // Carga los datos actualizados desde la BD
    }

    private void Guardar() {
    Clientes cliente = new Clientes(_id);
    cliente.setNombre(jTextFieldNombre.getText());   
    cliente.setDpi(jTextFieldDPI.getText());
    cliente.setNombre(jTextFieldCorreo.getText());
    cliente.setNombre(jTextFieldTelefono.getText());
    
    
    if (cliente.Guardar(_id)) {
        LimpiarVariables();
    } 
    else {
        JOptionPane.showMessageDialog(rootPane, "No fue posible guardar el producto", "Error", JOptionPane.ERROR_MESSAGE);}
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ABCclientes().setVisible(true);
            }
        }); /* Create and display the form */
       }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanelTabla;
    private javax.swing.JTextField jTextFieldCorreo;
    private javax.swing.JTextField jTextFieldDPI;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldTelefono;
    // End of variables declaration//GEN-END:variables

}
