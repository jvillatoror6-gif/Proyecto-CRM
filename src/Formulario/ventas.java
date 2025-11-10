/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formulario;


import Clases.Producto;
import Clases.Venta;
import Clases.conexion;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.util.Vector;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ventas extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ABCclientes.class.getName());
    private Object _model;
    private Connection _conexion;

    private DefaultTableModel model;
    private JTable _Tabla;
    private int _id;

    /**
     * Creates new form ventas
     */
    public ventas() {
        initComponents();
        CrearModelo();
        CargarTabla();

    }
private void CrearModelo() {
        String[] nombreColumnas = {"id", "No.Producto", "Precio"};
        model = new DefaultTableModel(nombreColumnas, 0);
        _Tabla = new JTable(model);

        JScrollPane scroll = new JScrollPane(_Tabla);

        _Tabla.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargarDatos();
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        jPanelTabla.removeAll();
        jPanelTabla.setLayout(new java.awt.BorderLayout());
        jPanelTabla.add(scroll, java.awt.BorderLayout.CENTER);
        jPanelTabla.revalidate();
        jPanelTabla.repaint();
    }

    private void CargarTabla() {
//    while (_model.getRowCount() > 0){
//    _model.removeRow(0);

        try {
            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            Statement st = _conexion.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT id, No.Producto, Precio FROM categorias ORDER BY Nombre ASC"
            );

            while (rs.next()) {
                Vector<Object> venta = new Vector<>();
                venta.add(rs.getString("id"));
                venta.add(rs.getInt("No.Producto"));
                venta.add(rs.getString("Precio"));
//                venta.add(rs.getString("Telefono"));
//                producto.add(rs.getInt("Idcategoria"));
                model.addRow(venta);
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (_conexion != null) {
                    _conexion.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    private void CargarDatos() {
    try {
        int filaSeleccionada = _Tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila primero");
            return;
        }

        int idCliente = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
        String noProducto = _Tabla.getValueAt(filaSeleccionada, 1).toString();
        int precio = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 2).toString());

        jTextFieldCliente.setText(String.valueOf(idCliente));
        jTextFieldNo.setText(noProducto);
        jTextFieldPrecio.setText(String.valueOf(precio));

    } catch (Exception ex) {
        System.out.println("ERROR " + ex.getMessage());
    }
}



    public void Guardar(int id) {
//        Connection _conexion = null;
        Producto producto = new Producto();
        if (producto.Guardar()) {
            JOptionPane.showMessageDialog(this, "Producto guardado", "Informacion", JOptionPane.INFORMATION_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "No fue Posible", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }
    private void Eliminar() {
    if (_id != 0) {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que desea eliminar este registro?",
            "Advertencia",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                Venta venta = new Venta();
                boolean eliminado = venta.Eliminar(_id); // método en tu clase Venta

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");

                    // Limpia los campos
                    jTextFieldCliente.setText("");
                    jTextFieldNo.setText("");
                    jTextFieldPrecio.setText("");

                    // Recarga los datos del panel o tabla
                    CargarTabla();
                    jPanelTabla.revalidate();
                    jPanelTabla.repaint();

                    _id = 0; // Limpia el ID seleccionado
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione el producto a eliminar", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldCliente = new javax.swing.JTextField();
        jTextFieldPrecio = new javax.swing.JTextField();
        jTextFieldNo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanelTabla = new javax.swing.JPanel();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Ventas");

        jLabel2.setText("Id Cliente");

        jLabel3.setText("Precio");

        jLabel4.setText("No. Producto");

        jTextFieldCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClienteActionPerformed(evt);
            }
        });

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTablaLayout = new javax.swing.GroupLayout(jPanelTabla);
        jPanelTabla.setLayout(jPanelTablaLayout);
        jPanelTablaLayout.setHorizontalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jButtonEliminar)
                        .addGap(27, 27, 27)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(18, 18, 18))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(56, 56, 56)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(39, 39, 39)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(jTextFieldNo)
                                    .addComponent(jTextFieldPrecio)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addComponent(jLabel1)))
                        .addGap(51, 51, 51)
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButtonEliminar))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    Venta nuevaVenta = new Venta();
    
    try {
        int idCliente = Integer.parseInt(jTextFieldCliente.getText().trim());
        double precioVenta = Double.parseDouble(jTextFieldPrecio.getText().trim());
        int noProducto = Integer.parseInt(jTextFieldNo.getText().trim());


        nuevaVenta.setIdCliente(idCliente);  
        nuevaVenta.setPrecio(precioVenta);       
        nuevaVenta.setNoProducto(noProducto);        

        nuevaVenta.Guardar();  
        
    } catch (NumberFormatException e) {
        
        JOptionPane.showMessageDialog(this, 
            "Error de formato. Asegúrese de ingresar números válidos en ID Cliente, Precio y Producto.", 
            "Error de Entrada", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        
        JOptionPane.showMessageDialog(this, 
            "Ocurrió un error inesperado al guardar: " + e.getMessage(), 
            "Error General", JOptionPane.ERROR_MESSAGE);
    }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextFieldClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClienteActionPerformed
        Eliminar();
    }//GEN-LAST:event_jTextFieldClienteActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanelTabla;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldNo;
    private javax.swing.JTextField jTextFieldPrecio;
    // End of variables declaration//GEN-END:variables
}
