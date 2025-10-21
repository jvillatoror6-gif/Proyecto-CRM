package Clases;

import Clases.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class Venta {
    
    private int idClientes;
    private double precio;
    private int noProducto;
    private LocalDateTime fechaVenta;

    /**
     * @return the idClientes
     */
    public int getIdClientes() {
        return idClientes;
    }

    /**
     * @param idClientes the idClientes to set
     */
    public void setIdClientes(int idClientes) {
        this.idClientes = idClientes;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the noProducto
     */
    public int getNoProducto() {
        return noProducto;
    }

    /**
     * @param noProducto the noProducto to set
     */
    public void setNoProducto(int noProducto) {
        this.noProducto = noProducto;
    }

    /**
     * @return the fechaVenta
     */
    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    /**
     * @param fechaVenta the fechaVenta to set
     */
    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    public void GuardarVenta(Venta venta) {
        

        String mensaje = "¿Desea guardar esta venta con los siguientes datos?\n\n"
                + "ID Cliente: " + venta.getIdClientes() + "\n"
                + "Precio: " + venta.getPrecio() + "\n"
                + "Producto: " + venta.getNoProducto();
               

        int opcion = JOptionPane.showConfirmDialog(
                null,
                mensaje,
                "Confirmar guardado de venta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            

            String sql = "INSERT INTO ventas (Idclientes, Precio, `No producto`, `Fecha venta`) " 
                       + "VALUES (?, ?, ?, NOW())";
                      
        try (Connection cx = conexion.conectar(); 
        PreparedStatement ps = cx.prepareStatement(sql)) {

                ps.setInt(1, venta.getIdClientes());
                ps.setDouble(2, venta.getPrecio());
                ps.setInt(3, venta.getNoProducto());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Venta registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                // Manejo de errores de SQL
                JOptionPane.showMessageDialog(null, "Error al registrar la venta: " + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Registro cancelado.");
        }
    }
    

    
}
