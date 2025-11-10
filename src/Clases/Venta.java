/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;

/**
 *
 * @author Eduar
 */
public class Venta {

    private int idCliente;
    private double precio;
    private int noProducto;
    private LocalDateTime fechaVenta;

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getNoProducto() {
        return noProducto;
    }

    public void setNoProducto(int noProducto) {
        this.noProducto = noProducto;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // Método para guardar una venta
    public boolean Guardar() {

        Connection _conexion = null;
        try {
            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            Statement st = _conexion.createStatement();

            // Inserción con NOW() para la fecha actual
            String sql = "INSERT INTO ventas (IdCliente, Precio, `NoProducto`, `FechaVenta`) VALUES ('"
                    + getIdCliente() + "', '"
                    + getPrecio() + "', '"
                    + getNoProducto() + "', NOW())";

            st.execute(sql);

            JOptionPane.showMessageDialog(null, "Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception ex) {
            System.out.println("Error al guardar venta: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        } finally {
            try {
                if (_conexion != null) {
                    _conexion.close();
                }
            } catch (SQLException ex2) {
            }
        }
    }
//    public boolean Eliminar(int id) {
//    try {
//        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/crm2", "root", "012003");
//        String sql = "DELETE FROM ventas WHERE id = ?";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setInt(1, id);
//        int filas = ps.executeUpdate();
//        con.close();
//        return filas > 0;
//    } catch (Exception e) {
//        System.out.println("Error al eliminar: " + e.getMessage());
//        return false;
//    }
//}

    // Método para eliminar una venta (opcional)
    public boolean Eliminar(int idVenta) {

        Connection _conexion = null;
        try {
            String conexionString = "jdbc:mysql://localhost/crm2?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "root", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            Statement st = _conexion.createStatement();
            String sql = "DELETE FROM ventas WHERE IdVenta = " + idVenta;
            st.execute(sql);

            JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (Exception ex) {
            System.out.println("Error al eliminar venta: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;

        } finally {
            try {
                if (_conexion != null) {
                    _conexion.close();
                }
            } catch (SQLException ex2) {
            }
        }
    }
}
