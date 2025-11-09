package Clases;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kenet
 */
public class Producto {
  private int Id;
 private int Idcategoria;
    private int Stock;
    private int Idventa;
    private String Nombre;
    private double precio;
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
   public boolean  Guardar(){
    
      Connection _conexion = null;
    try {
        String conexionString ="jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        String driverName ="com.mysql.cj.jdbc.Driver";  //com.mysql.jdbc.Driver;
        Class.forName(driverName).newInstance();
       _conexion = DriverManager.getConnection(conexionString, "root","012003"); 
       _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      
       Statement st = _conexion.createStatement();
       st.execute("insert into producto (Nombre,precio,stock, Idcategoria)\n" +
           "values ('"+getNombre()+"', "+ getPrecio()+" ,"+ getStock()+"," + getIdcategoria()+")");
       
      // System.out.println("Conexion exitosa!!!");
    
      return true;
       
    }catch (Exception ex){ 
        System.out.println("Error" + ex.getMessage());
     return false;
    }finally {
        try {
        _conexion.close();
   }catch (Exception ex2){   
   }        
}
    
}
     public boolean  Eliminar(){
    
      Connection _conexion = null;
    try {
        String conexionString ="jdbc:mysql://localhost/crm2?characterEncoding=latin1";
        String driverName ="com.mysql.cj.jdbc.Driver";  //com.mysql.jdbc.Driver;
        Class.forName(driverName).newInstance();
       _conexion = DriverManager.getConnection(conexionString, "root","012003"); 
       _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      
       Statement st = _conexion.createStatement();
       st.execute("insert into producto (Nombre,precio,stock, Idcategoria)\n" +
           "values ('"+getNombre()+"',"+getPrecio()+","+getStock()+","+getIdcategoria()+")");
       
      // System.out.println("Conexion exitosa!!!");
    
      return true;
       
    }catch (Exception ex){ 
        System.out.println("Error" + ex.getMessage());
     return false;
    }finally {
        try {
        _conexion.close();
   }catch (Exception ex2){   
   }        
}
    
}
}


