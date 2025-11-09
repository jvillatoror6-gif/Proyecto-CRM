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
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ABCclientes.class.getName());
    private Object _model;
    private Connection _conexion;

    private DefaultTableModel model;
    private JTable _Tabla;
    private int _id;

    /**
     * Creates new form AbcProductos
     */
    public ABCclientes() {
        initComponents();
        CargarComboCategorias();
        CrearModelo();
        CargarTabla();

    }

    private void CargarComboCategorias() {

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        Connection _conexion = null;
        try {
            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";  //com.mysql.jdbc.Driver;
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            Statement st = _conexion.createStatement();
            ResultSet rs = st.executeQuery("select Id, Nombre from categorias\n"
                    + "order by Nombre asc");
            while (rs.next()) {

                ComboBox C = new ComboBox(rs.getInt("Id"), rs.getString("Nombre"));
                model.addElement(C);
                System.out.println("");
            }
            rs.close();

//            jComboBoxCategoriaIncidente.setModel(model);

        } catch (Exception ex) {
            System.out.println("Error" + ex.getMessage());

        } finally {
            try {
                _conexion.close();
            } catch (Exception ex2) {
            }
        }
    }

    private void CrearModelo() {
        String[] nombreColumnas = {"Id", "nombre", "Precio", "Stock", "Categoria"};
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
                    "SELECT Id, Nombre, Precio, Stock, Idcategoria FROM categorias ORDER BY Nombre ASC"
            );

            while (rs.next()) {
                Vector<Object> cliente = new Vector<>();
                cliente.add(rs.getString("Nombre"));
                cliente.add(rs.getInt("Dpi"));
                cliente.add(rs.getString("Correo"));
                cliente.add(rs.getString("Telefono"));
//                producto.add(rs.getInt("Idcategoria"));
                model.addRow(cliente);
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
            _id = 0;
            int filaSeleccionada = _Tabla.getSelectedRow();
            //_id = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
            String Nombre = _Tabla.getValueAt(filaSeleccionada, 1).toString();
            String Dpi = _Tabla.getValueAt(filaSeleccionada, 2).toString();
            String Correo = _Tabla.getValueAt(filaSeleccionada, 3).toString();//Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 3).toString());
            String Telefono = _Tabla.getValueAt(filaSeleccionada, 4).toString();//Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 4).toString());
//            String categoria = _Tabla.getValueAt(filaSeleccionada, 4).toString();
//            ComboBox categoriaSeleccionada = new ComboBox(idCategoria, categoria);
            jTextFieldNombre.setText(Nombre);
            jTextFieldDPI.setText(Dpi);//jFormattedTextFieldPrecio.setText(precio);
            jTextFieldCorreo.setText(Correo);//jFormattedTextFieldStock.setText(String.valueOf(stock));
            jTextFieldTelefono.setText(Telefono);//jComboBoxCategoria.getModel().setSelectedItem(categoriaSeleccionada);
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
    private void Eliminar (){
        if (_id !=0){
            int o = JOptionPane.showConfirmDialog(this, "Seguro que lo eliminara?", "Advertencia", JOptionPane.YES_NO_OPTION);
            if (o==0){
                
            }
        }else {
            JOptionPane.showMessageDialog(this, "Seleccione el producto a eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }    /**
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
            .addGap(0, 505, Short.MAX_VALUE)
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
                            .addComponent(jTextFieldTelefono))
                        .addGap(49, 49, 49)
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jButtonGuardar)
                .addContainerGap(818, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(31, 31, 31)
                .addComponent(jButtonGuardar)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
       Clientes c =new Clientes ();
      c.setNombre(jTextFieldNombre.getText());
      c.setDpi(jTextFieldDPI.getText());
      c.setCorreo(jTextFieldCorreo.getText());
      c.setTelefono(jTextFieldTelefono.getText());
      
      if (c.Guardar()){
      jTextFieldNombre.setText("");

 JOptionPane.showMessageDialog(this, "Cliente creado ","Informacion",JOptionPane.INFORMATION_MESSAGE);
    
     } else 
          JOptionPane.showMessageDialog(this, " No fue posoble crear el cliente ","Error",JOptionPane.ERROR_MESSAGE);
                                                 
                                                 
    }//GEN-LAST:event_jButtonGuardarActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ABCclientes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
