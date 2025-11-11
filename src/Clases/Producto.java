
package Clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kenet
 */
public class Producto {

    public Producto(int _id) {
    }
  private int Id;
 private int Idcategoria;
    private int Stock;
    private int Idventa;
    private String Nombre;
    private double precio;
    private Statement _st;
    private Connection _conexion;

    
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

    /**
     * @return the Idcategoria
     */
    public int getIdcategoria() {
        return Idcategoria;
    }

    /**
     * @param Idcategoria the Idcategoria to set
     */
    public void setIdcategoria(int Idcategoria) {
        this.Idcategoria = Idcategoria;
    }

    /**
     * @return the Stock
     */
    public int getStock() {
        return Stock;
    }

    /**
     * @param Stock the Stock to set
     */
    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    /**
     * @return the Idventa
     */
    public int getIdventa() {
        return Idventa;
    }

    /**
     * @param Idventa the Idventa to set
     */
    public void setIdventa(int Idventa) {
        this.Idventa = Idventa;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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
   
    private void CrearConexion(){
        _conexion = null;
        try{
            String conexionString="jdbc:mysql://localhost/crm?characterEncoding=latin1";
            String driverName="com.mysql.cj.jdbc.Driver";
            Class.forName(driverName).newInstance();
            _conexion=DriverManager.getConnection(conexionString,"proyectofinal","012003");
            _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            _st= _conexion.createStatement();
        }catch(Exception ex) {
            JOptionPane.showMessageDialog( null, ex.getMessage(), "Error al abrir la conexion", JOptionPane.ERROR_MESSAGE);
            
        }
    
    }
    
public boolean Guardar(int _id) {
    Connection con = null;
    try {
        String conexionString ="jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(conexionString, "root","012003");

        // IMPORTANTE: Los nombres deben ser EXACTOS como en la tabla
        String sql = "INSERT INTO producto (Nombre, Stock, Precio, IdCategoria) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, this.Nombre);
        ps.setInt(2, this.Stock);
        ps.setDouble(3, this.precio);
        ps.setInt(4, this.Idcategoria);

        ps.executeUpdate();
        return true;

    } catch (Exception ex) {
        System.out.println("Error al guardar producto: " + ex.getMessage());
        return false;
    } finally {
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}



   public boolean Eliminar(int _id){
        _conexion=null;
        try{
            CrearConexion();
            _st.execute("delete from productos where idproducto="+ getId());
            return true;
        } catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
            return false;
        }
        finally{
            try{
            _conexion.close();}
            catch(Exception ex2){
            }
            }
    }
   public ResultSet ObtenerProductos(){
         ResultSet rs=null;
         try{
             CrearConexion();
             rs= _st.executeQuery("SELECT p.idproducto, p.Nombre,\n"
                     + " p.Precio, p.Stock, c.idCategoriaProducto, c.Nombre AS categoria \n"
                     +"FROM productos AS p \n"
                      +"INNER JOIN categoriaproducto AS c \n"
                      +"ON p.idCategoriaProducto = c.idCategoriaProducto ORDER BY p.Nombre ASC");
             return rs;
         }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
            return rs;
        }
       
     }
  
}





