package Formulario;

import Clases.ComboBox;
import Clases.Producto;
import java.awt.event.MouseAdapter;
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

    private DefaultTableModel _model;
    private JTable _Tabla;
    private int _id;
    private Producto _producto=null;

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AbcProductos.class.getName());

    /**
     * Creates new form AbcProductos
     */
    public AbcProductos() {
        initComponents();
        _producto=new Producto(_id);
        CargarComboCategorias();
        CrearModelo();
        CargarTabla();
    }
    
    private void CargarComboCategorias(){
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        Connection _conexion = null;
        try{
           String conexionString = "jdbc:mysql://localhost/crm?characterEncoding=latin1"; 
           String driverName = "com.mysql.cj.jdbc.Driver";
           Class.forName(driverName).newInstance();
           _conexion = DriverManager.getConnection(conexionString,"proyectofinal","012003");
           _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
           Statement st = _conexion.createStatement();
           ResultSet rs = st.executeQuery("select idcategoriaproducto, nombre from categoriaproducto order by nombre asc");
           
           while (rs.next()){
               ComboBox c = new ComboBox(rs.getInt("idcategoriaproducto"), rs.getString("nombre"));
               model.addElement(c);
           }
           rs.close();
           st.close();
           jComboBoxCategoria.setModel(model);
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        } finally {
            try { if (_conexion != null) _conexion.close(); } catch (Exception ex2){}
        }
    }
    
    private void CrearModelo(){
               String[] nombreColumnas = {"Id", "Nombre", "Precio", "Stock","idcategoriaproducto", "Categoria"};
        _model = new DefaultTableModel(nombreColumnas, 0);
        _Tabla = new JTable(_model);
        JScrollPane scroll = new JScrollPane(_Tabla);

        
       _Tabla.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            CargarDatos();
    }
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    
        @Override
        public void mouseEntered(MouseEvent e) {}
    
        @Override
        public void mouseExited(MouseEvent e) {}
    });
       
         // Elimina la columna de idCategoria
        if (_Tabla.getColumnModel().getColumnCount() > 4) {
            _Tabla.getColumnModel().getColumn(4).setMinWidth(0);
            _Tabla.getColumnModel().getColumn(4).setMaxWidth(0);
            _Tabla.getColumnModel().getColumn(4).setWidth(0);
        }
        

        //limpiar el panel antes
        jPanelTabla.removeAll();

        //Usa un layout adecuado
        jPanelTabla.setLayout(new java.awt.BorderLayout());

        //Agrega la tabla dentro del panel
        jPanelTabla.add(scroll, java.awt.BorderLayout.CENTER);

        //Actualiza el panel en pantalla
        jPanelTabla.revalidate();
        jPanelTabla.repaint();

    }

        
    private void CargarTabla(){
        while(_model.getRowCount()>0){
            _model.removeRow(0);
        }
        try {
            ResultSet rs= _producto.ObtenerProductos();
           while (rs.next()){
               Vector producto = new Vector ();
               producto.add(rs.getInt("idproducto"));
               producto.add(rs.getString("nombre"));
               producto.add(rs.getDouble("precio"));
               producto.add(rs.getInt("stock"));
               producto.add(rs.getInt("idcategoriaproducto"));
               producto.add(rs.getString("categoria"));

               _model.addRow(producto);
           }
           rs.close();
        } catch (Exception ex) {
            System.out.println("Error al cargar tabla: " + ex.getMessage());
    }
    }
    
    private void CargarDatos(){
        _id=0;
        jComboBoxCategoria.setSelectedIndex(0);
        try{
            int filaSeleccionada= _Tabla.getSelectedRow();
        _id = Integer.parseInt(_Tabla.getValueAt(filaSeleccionada, 0).toString());
        String nombre = _Tabla.getValueAt(filaSeleccionada,1).toString();
        String precio = _Tabla.getValueAt(filaSeleccionada,2).toString();
        String stock= _Tabla.getValueAt(filaSeleccionada,3).toString();
        int idCategoria= Integer.parseInt( _Tabla.getValueAt(filaSeleccionada,4).toString());
        String categoria= _Tabla.getValueAt(filaSeleccionada,5).toString();
        ComboBox categoriaSeleccionada= new ComboBox(idCategoria, categoria);
        jComboBoxCategoria.getModel().setSelectedItem(categoriaSeleccionada);
        jTextFieldNombre.setText(nombre);
        jFormattedTextFieldPrecio.setText(precio);
        jFormattedTextFieldStock.setText(stock);
        
        }catch(Exception ex){
            System.out.println("Error al seleccionar fila: " +ex.getMessage());
        }
           
    }
    
     // Metodo para actulizar datos 
    private void Actualizar() {
    if (_id == 0) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        Producto producto = new Producto(_id);
        producto.setNombre(jTextFieldNombre.getText());
        producto.setStock(((Number) jFormattedTextFieldStock.getValue()).intValue());
        producto.setPrecio(((Number) jFormattedTextFieldPrecio.getValue()).doubleValue());

        ComboBox c = (ComboBox) jComboBoxCategoria.getSelectedItem();
        producto.setIdcategoria(c.getId());

        if (producto.Guardar(_id)) {
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            LimpiarVariables();
            CargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        System.out.println("Error al actualizar: " + ex.getMessage());
        JOptionPane.showMessageDialog(this, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

   
    private void Eliminar() {
    if (_id != 0) {
        int o = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este producto?", "Advertencia", JOptionPane.YES_NO_OPTION);
        if (o == JOptionPane.YES_OPTION) {
            Producto producto = new Producto(_id);
            if (_producto.Eliminar(_id)) {
                JOptionPane.showMessageDialog(this, "El producto se eliminó correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
                CargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un producto para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
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
        jButtonActualizar = new javax.swing.JButton();

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

        jButtonActualizar.setText("Actualizar");
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonActualizar)
                        .addGap(3, 3, 3)))
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
                    .addComponent(jButtonEliminar)
                    .addComponent(jButtonActualizar))
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jFormattedTextFieldPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextFieldPrecioActionPerformed

    private void jComboBoxCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCategoriaActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jPanelTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelTablaMouseClicked
        
    }//GEN-LAST:event_jPanelTablaMouseClicked

    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        Actualizar();
    }//GEN-LAST:event_jButtonActualizarActionPerformed
    private void LimpiarVariables() {
        _id=0;
        
        // Limpia los campos
        jTextFieldNombre.setText("");
        jFormattedTextFieldStock.setValue(0);
        jFormattedTextFieldPrecio.setValue(0);
        jComboBoxCategoria.setSelectedIndex(-1);
        
        JOptionPane.showMessageDialog(rootPane, "Producto guardado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        
        // Actualiza la tabla después de guardar
        _model.setRowCount(0);  // Limpia las filas actuales
        CargarTabla();          // Carga los datos actualizados desde la BD
    }

    private void Guardar() {
        Producto producto = new Producto(_id);
    producto.setNombre(jTextFieldNombre.getText());   
    producto.setStock(((Number) jFormattedTextFieldStock.getValue()).intValue());
    producto.setPrecio(((Number) jFormattedTextFieldPrecio.getValue()).doubleValue());
    
    int idCategoria = ((ComboBox) jComboBoxCategoria.getSelectedItem()).getId();
    producto.setIdcategoria(idCategoria);
    
    if (producto.Guardar(_id)) {
        LimpiarVariables();
    } 
    else {
        JOptionPane.showMessageDialog(rootPane, "No fue posible guardar el producto", "Error", JOptionPane.ERROR_MESSAGE);}
    }
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
    private javax.swing.JButton jButtonActualizar;
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
