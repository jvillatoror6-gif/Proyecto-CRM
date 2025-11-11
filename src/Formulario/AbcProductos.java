package Formulario;

import Clases.ComboBox;
import Clases.Producto;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
/**
 *
 * @author kenet
 *
 *
 * /**
 *
 * @author kenet
 */
public class AbcProductos extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AbcProductos.class.getName());
    private Connection _conexion;

    private DefaultTableModel model;
    private JTable _Tabla;
    private int _id;

    public AbcProductos() {
        initComponents();
        CargarComboCategorias();
        CrearModelo();
        CargarTabla();
    }

    private void CargarComboCategorias() {
          DefaultComboBoxModel model = new DefaultComboBoxModel ();
        Connection _conexion = null;

        try {
            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Statement st = _conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT Id, Nombre FROM categorias ORDER BY Nombre ASC");

            while (rs.next()) {
                ComboBox c = new ComboBox(rs.getInt("Id"), rs.getString("Nombre"));
                model.addElement(c);
            }
            rs.close();
            
            jComboBoxCategoria.setModel(model);

        } catch (Exception ex) {
            System.out.println("Error al cargar categorías: " + ex.getMessage());
        } finally {
            try {
                if (_conexion != null) _conexion.close();
            } catch (Exception ex2) {}
        }
    }

    private void CrearModelo() {
        String[] nombreColumnas = {"ID", "Nombre", "Precio", "Stock", "Categoría"};
        model = new DefaultTableModel(nombreColumnas, 0);
        _Tabla = new JTable(model);

        JScrollPane scroll = new JScrollPane(_Tabla);

        _Tabla.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CargarDatos();
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        jPanelTabla.removeAll();
        jPanelTabla.setLayout(new java.awt.BorderLayout());
        jPanelTabla.add(scroll, java.awt.BorderLayout.CENTER);
        jPanelTabla.revalidate();
        jPanelTabla.repaint();
    }

//    private void CargarTabla() {
//        model.setRowCount(0); 
//        try {
//            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
//            String driverName = "com.mysql.cj.jdbc.Driver";
//            Class.forName(driverName).newInstance();
//            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
//            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//
//            String sql = "SELECT idProducto, Nombre, Precio, Stock, Idcategoria FROM producto ORDER BY Nombre ASC";
//            Statement st = _conexion.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//
//            while (rs.next()) {
//                Object[] fila = {
//                    rs.getInt("idProducto"),
//                    rs.getString("Nombre"),
//                    rs.getDouble("Precio"),
//                    rs.getInt("Stock"),
//                    rs.getInt("Idcategoria")
//                };
//                model.addRow(fila);
//            }
//
//            rs.close();
//            st.close();
//            _conexion.close();
//
//            System.out.println("Filas cargadas: " + model.getRowCount()); 
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error al cargar tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            try {
//                if (_conexion != null) _conexion.close();
//            } catch (Exception e) {}
//        }
//    }
 private void CargarTabla() {
    model.setRowCount(0); 
    try {
        String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName).newInstance();
        _conexion = DriverManager.getConnection(conexionString, "root", "012003");
        _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        String sql = "SELECT idProducto, Nombre, Precio, Stock, Idcategoria FROM producto ORDER BY Nombre ASC";
        Statement st = _conexion.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Object[] fila = {
                rs.getInt("idProducto"),
                rs.getString("Nombre"),
                rs.getDouble("Precio"),
                rs.getInt("Stock"),
                rs.getInt("Idcategoria")
            };
            model.addRow(fila);
        }

        rs.close();
        st.close();

        // ✅ ESTA LÍNEA ES LA QUE HACÍA FALTA
        _Tabla.setModel(model);

        System.out.println("Filas cargadas: " + model.getRowCount()); 
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (_conexion != null) _conexion.close();
        } catch (Exception e) {}
    }
}

    private void CargarDatos() {
        try {
            int filaSeleccionada = _Tabla.getSelectedRow();
            if (filaSeleccionada == -1) return; 
            _id = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
            String nombre = _Tabla.getValueAt(filaSeleccionada, 1).toString();
            String precio = _Tabla.getValueAt(filaSeleccionada, 2).toString();
            String stock = _Tabla.getValueAt(filaSeleccionada, 3).toString();

            jTextFieldNombre.setText(nombre);
            jFormattedTextFieldPrecio.setText(precio);
            jFormattedTextFieldStock.setText(stock);

        } catch (Exception ex) {
            System.out.println("ERROR al cargar datos: " + ex.getMessage());
        }
    }

    public void Guardar(int id) {
        try {
            Producto producto = new Producto();
            producto.setNombre(jTextFieldNombre.getText());
            producto.setPrecio(Double.parseDouble(jFormattedTextFieldPrecio.getText()));
            producto.setStock(Integer.parseInt(jFormattedTextFieldStock.getText()));

            ComboBox categoriaSeleccionada = (ComboBox) jComboBoxCategoria.getSelectedItem();
            producto.setIdcategoria(categoriaSeleccionada.getId());

            if (producto.Guardar()) {
                JOptionPane.showMessageDialog(this, "Producto guardado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);

                CargarTabla();

                jTextFieldNombre.setText("");
                jFormattedTextFieldPrecio.setText("");
                jFormattedTextFieldStock.setText("");
                jComboBoxCategoria.setSelectedIndex(0);

            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jButtonGuardar = new javax.swing.JButton();
        jFormattedTextFieldPrecio = new javax.swing.JFormattedTextField();
        jFormattedTextFieldStock = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxCategoria = new javax.swing.JComboBox<>();
        jPanelTabla = new javax.swing.JPanel();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("Precio");

        jLabel5.setText("Categoria ");

        jButtonGuardar.setText("Guardar ");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jFormattedTextFieldPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        jFormattedTextFieldPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldPrecioActionPerformed(evt);
            }
        });

        jFormattedTextFieldStock.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));

        jLabel1.setText("Catalogo de Productos ");

        jLabel2.setText("Nombre");

        jLabel3.setText("Stock");

        jComboBoxCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoriaActionPerformed(evt);
            }
        });

        jPanelTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelTablaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTablaLayout = new javax.swing.GroupLayout(jPanelTabla);
        jPanelTabla.setLayout(jPanelTablaLayout);
        jPanelTablaLayout.setHorizontalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 571, Short.MAX_VALUE)
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
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
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jFormattedTextFieldStock, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                .addComponent(jTextFieldNombre)
                                .addComponent(jFormattedTextFieldPrecio)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addComponent(jButtonGuardar)
                                    .addGap(35, 35, 35)
                                    .addComponent(jButtonEliminar))))
                        .addGap(18, 18, 18)
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jFormattedTextFieldStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardar)
                    .addComponent(jButtonEliminar))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        Producto P = new Producto();
P.setNombre(jTextFieldNombre.getText());
P.setStock(((Number) jFormattedTextFieldStock.getValue()).intValue());
P.setPrecio(((Number) jFormattedTextFieldPrecio.getValue()).doubleValue());
P.setIdcategoria(((ComboBox) jComboBoxCategoria.getSelectedItem()).getId());

if (P.Guardar()) {
    jTextFieldNombre.setText("");
    jFormattedTextFieldStock.setValue(null);
    jFormattedTextFieldPrecio.setValue(null);
    jComboBoxCategoria.setSelectedIndex(-1);

    JOptionPane.showMessageDialog(this, "Producto creado", "Información", JOptionPane.INFORMATION_MESSAGE);
    CargarTabla(); // <-- IMPORTANTE para ver el nuevo producto en la tabla

} else {
    JOptionPane.showMessageDialog(this, "No fue posible crear el producto", "Error", JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jFormattedTextFieldPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldPrecioActionPerformed

    private void jComboBoxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCategoriaActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
int filaSeleccionada = _Tabla.getSelectedRow();  

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un producto en la tabla para eliminar.");
        return;
    }

    // Obtener el ID del producto (columna 0)
    int idProducto = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());

    // Confirmar eliminación
    int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que desea eliminar el producto con ID " + idProducto + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
    );

    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            // Crear objeto producto
            Producto producto = new Producto();
            
            // Llamar al método Eliminar de la clase Producto
            boolean eliminado = producto.Eliminar(idProducto);

            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                CargarTabla(); // refresca los datos en la tabla
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el producto en la base de datos.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jPanelTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelTablaMouseClicked
        
    }//GEN-LAST:event_jPanelTablaMouseClicked
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
                new AbcProductos().setVisible(true);
            }
        }); /* Create and display the form */
       }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JComboBox<String> jComboBoxCategoria;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrecio;
    private javax.swing.JFormattedTextField jFormattedTextFieldStock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanelTabla;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables

}
