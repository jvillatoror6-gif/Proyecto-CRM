package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexion {

    public static String DB_NAME = "eduardo";
    public static String USER = "root"; 
    public static String PASSWORD = "Ramirez-77";
    
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL_BASE = "jdbc:mysql://localhost:3306/";
    private static final String PARAMETROS = "?useSSL=false&serverTimezone=UTC";

    private static Connection conexion = null;
    
    private conexion() {}


    public static Connection conectar() {
        

        String fullUrl = DB_URL_BASE + DB_NAME + PARAMETROS;
        
        try {

            if (conexion == null || conexion.isClosed()) {
                
                Class.forName(DRIVER);
                
                conexion = DriverManager.getConnection(fullUrl, USER, PASSWORD);
                System.out.println("CONEXIÓN EXITOSA a la BD: " + DB_NAME);
            }
            return conexion;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, "Driver no encontrado", ex);
            JOptionPane.showMessageDialog(null, "Error: Driver de MySQL no encontrado");
            return null;
        } catch (SQLException ex) {
            System.err.println("❌ Error de conexión SQL a la BD " + DB_NAME + ": " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error de conexión. Revise la BD: " + DB_NAME);
            return null;
        }
    }

    public static void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                System.out.println("✅ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
