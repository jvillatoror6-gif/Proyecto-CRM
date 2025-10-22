package Formulario;

import Clases.Categoria;
import Clases.ComboBox;
import Clases.Producto;
import Formulario.Categorias;
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
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



/**
 *
 * @author kenet
 */
public class Productos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Productos.class.getName());
    private Object _model;
    private Connection _conexion;
    
    private DefaultTableModel model;
    private JTable _Tabla;

    /**
     * Creates new form Productos
     */
    public Productos() {
        initComponents();
         CargarComboCategorias(); 
        CrearModelo();
         CargarTabla();
        
          
    }

    private void CargarComboCategorias(){
    
        DefaultComboBoxModel model = new DefaultComboBoxModel ();
    
    Connection _conexion = null;
    try {
        String conexionString ="jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        String driverName ="com.mysql.cj.jdbc.Driver";  //com.mysql.jdbc.Driver;
        Class.forName(driverName).newInstance();
       _conexion = DriverManager.getConnection(conexionString, "root","012003"); 
       _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      
       Statement st = _conexion.createStatement();
       ResultSet rs = st.executeQuery ("select Id, Nombre from categorias\n" +
                   "order by Nombre asc");
       while (rs.next()){
           
           ComboBox C =new ComboBox(rs.getInt("Id"),rs.getString("Nombre"));
           model.addElement(C);
           
//           
//           System.out.println(rs.getInt("Idcategoria"));
//           System.out.println(rs.getString("Nombre"));
//           System.out.println("");
       }
      // System.out.println("Conexion exitosa!!!");
    rs.close();
    
    jComboBoxCategoria.setModel(model);
            
    }catch (Exception ex){ 
        System.out.println("Error" + ex.getMessage());
    
    }finally {
        try {
        _conexion.close();
   }catch (Exception ex2){   
   }        
} 
    }
    private void CrearModelo(){
        String [] nombreColumnas = {"Id", "nombre", "Precio", "Stock", "Categoria"};
        model = new DefaultTableModel (nombreColumnas, 0);
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
    private void CargarTabla() {
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
            Vector<Object> producto = new Vector<>();
            producto.add(rs.getInt("Id"));
            producto.add(rs.getString("Nombre"));
            producto.add(rs.getDouble("Precio"));
            producto.add(rs.getInt("Stock"));
            producto.add(rs.getInt("Idcategoria"));
            model.addRow(producto);
        }

        rs.close();
        st.close();
    } catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    } finally {
        try {
            if (_conexion != null) _conexion.close();
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}

    private void CargarDatos(){
        try {
        int _id = 0;
        int filaSeleccionada = _Tabla.getSelectedRow();
        _id = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
        String Nombre = _Tabla.getValueAt(filaSeleccionada, 1).toString();
        String precio = _Tabla.getValueAt(filaSeleccionada, 2).toString();
        int Stock = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 3).toString());
        int idCategoria = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 4).toString());
        String categoria = _Tabla.getValueAt(filaSeleccionada, 4).toString();
        ComboBox categoriaSeleccionada = new ComboBox(idCategoria, categoria);
        jComboBoxCategoria.getModel().setSelectedItem(categoriaSeleccionada);
        jTextFieldNombre.setText(nombre);
        jFormattedTextFieldPrecio.setText(precio);
        jFormattedTextFieldStock.setText(stock);
        } catch (Exception ex) {
        System.out.println("ERROR " + ex.getMessage());
}
        }
        
    }
    public boolean Guardar(int id) {
    Connection _conexion = null;
    

    try {
        String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        String driverName = "com.mysql.cj.jdbc.Driver";
        Class.forName(driverName).newInstance();
        _conexion = DriverManager.getConnection(conexionString, "root", "012003");
        _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        Statement st = _conexion.createStatement();

        if (id == 0) {
            String sql = "INSERT INTO productos (nombre, precio, stock, idcategoria)"
            + "values ("+ getNombre() + "', " + getPrecio() + ", " + getStock() + ", " + getIdCategoria() + ")";
            st.executeUpdate(sql);
        } else {
            String sql = "UPDATE productos SET nombre = '" + getNombre() + 
                    "', precio = " + getPrecio() + 
                    ", stock = " + getStock() + 
                    ", idcategoria = " + getIdCategoria() + 
                    " WHERE idproducto = " + id;
            st.executeUpdate(sql);
        }

        return true;

    } catch (Exception ex) {
        System.out.println("Error al guardar producto: " + ex.getMessage());
        return false;

    } finally {
        try {
            if (_conexion != null && !_conexion.isClosed()) {
                _conexion.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
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

        javax.swing.GroupLayout jPanelTablaLayout = new javax.swing.GroupLayout(jPanelTabla);
        jPanelTabla.setLayout(jPanelTablaLayout);
        jPanelTablaLayout.setHorizontalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );
        jPanelTablaLayout.setVerticalGroup(
            jPanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

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
                    .addComponent(jComboBoxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jFormattedTextFieldStock, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                        .addComponent(jTextFieldNombre)
                        .addComponent(jFormattedTextFieldPrecio)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jButtonGuardar))))
                .addGap(66, 66, 66)
                .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(jButtonGuardar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
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
            int _id = 0;
            jTextFieldNombre.setText("");
            jFormattedTextFieldStock.setValue(null);
            jFormattedTextFieldPrecio.setValue(null);
            jFormattedTextFieldPrecio.setValue(null);
            jComboBoxCategoria.setSelectedIndex(-1);

                JOptionPane.showMessageDialog(this, "Categoria creada","Informacion",JOptionPane.INFORMATION_MESSAGE);

            } else
            JOptionPane.showMessageDialog(this, "No fue posible crear la categoria","Error",JOptionPane.ERROR_MESSAGE);

    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jFormattedTextFieldPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldPrecioActionPerformed

    private void jComboBoxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCategoriaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Productos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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

