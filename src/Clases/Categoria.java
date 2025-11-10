/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author kenet
 */
public class Categoria {
private int id; 
   private String nombre;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
    public boolean  Guardar(){
    
      Connection _conexion = null;
    try {
        String conexionString ="jdbc:mysql://localhost/sys?characterEncoding=latin1";
        String driverName ="com.mysql.cj.jdbc.Driver";  //com.mysql.jdbc.Driver;
        Class.forName(driverName).newInstance();
       _conexion = DriverManager.getConnection(conexionString, "root","DOMI-666"); 
       _conexion.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      
       Statement st = _conexion.createStatement();
       st.execute("INSERT INTO categorias (Nombre) VALUES('"+getNombre()+"')");
       
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

