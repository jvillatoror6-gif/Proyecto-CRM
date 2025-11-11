/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author ASUS
 */
public class Clientes {

    private int Id;
    private String Nombre;
    private String Dpi;
    private String Correo;
    private String Telefono;
    private Statement _st;
    private Connection _conexion;

    // 🔹 Constructor vacío (necesario para crear instancias sin parámetros)
    public Clientes() {
    }

    // 🔹 Constructor con parámetro Id (el que pedía tu clase ABCclientes)
    public Clientes(int id) {
        this.Id = id;
        CrearConexion();
    }

    /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDpi() {
        return Dpi;
    }

    public void setDpi(String Dpi) {
        this.Dpi = Dpi;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    private void CrearConexion() {
        _conexion = null;
        try {
            String conexionString = "jdbc:mysql://localhost/crm?characterEncoding=latin1";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion = DriverManager.getConnection(conexionString, "proyectofinal", "012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            _st = _conexion.createStatement();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error al abrir la conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean Guardar(int _id) {
        Connection con = null;
        try {
            String conexionString = "jdbc:mysql://localhost/crm?characterEncoding=latin1";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(conexionString, "proyectofinal", "012003");

            // ✅ Cambié la tabla y los campos para clientes
            String sql;
            if (_id == 0) {
                sql = "INSERT INTO clientes (Nombre, Dpi, Correo, Telefono) VALUES (?, ?, ?, ?)";
            } else {
                sql = "UPDATE clientes SET Nombre=?, Dpi=?, Correo=?, Telefono=? WHERE id=?";
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.Nombre);
            ps.setString(2, this.Dpi);
            ps.setString(3, this.Correo);
            ps.setString(4, this.Telefono);

            if (_id != 0) {
                ps.setInt(5, _id);
            }

            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            System.out.println("Error al guardar cliente: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }

    public boolean Eliminar(int _id) {
        _conexion = null;
        try {
            CrearConexion();
            _st.execute("DELETE FROM clientes WHERE id=" + _id);
            return true;
        } catch (Exception ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (_conexion != null) _conexion.close();
            } catch (Exception ex2) {
            }
        }
    }

    public ResultSet ObtenerClientes() {
        ResultSet rs = null;
        try {
            CrearConexion();
            rs = _st.executeQuery(
                    "SELECT id, Nombre, Dpi, Correo, Telefono " +
                    "FROM clientes ORDER BY Nombre ASC");
            return rs;
        } catch (Exception ex) {
            System.out.println("Error al obtener clientes: " + ex.getMessage());
            return rs;
        }
    }
}




