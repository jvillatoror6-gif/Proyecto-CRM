/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
/**
 *
 * @author ASUS
 */
public class Clientes {
   private String Nombre;
   private String Dpi;
   private String Correo;
   private String Telefono;
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
     * @return the Codigo
     */
   

    /**
     * @param Codigo the Codigo to set
     */
    
    

    /**
     * @return the Dpi
     */
    public String getDpi() {
        return Dpi;
    }

    /**
     * @param Dpi the Dpi to set
     */
    public void setDpi(String Dpi) {
        this.Dpi = Dpi;
    }

    /**
     * @return the Correo
     */
    public String getCorreo() {
        return Correo;
    }

    /**
     * @param Correo the Correo to set
     */
    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    /**
     * @return the Telefono
     */
    public String getTelefono() {
        return Telefono;
    }

    /**
     * @param Telefono the Telefono to set
     */
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
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
       st.execute("insert into clientes (Nombre, DPI, Correo, Telefono)\n" +
          "values ('"+getNombre()+"', '"+ getDpi()+"' , '"+ getCorreo ()+"' , '" + getTelefono()+"')");
       
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
       st.execute("insert into clientes (Nombre,DPI, Correo, Telefono)\n" +
           "values ('"+getNombre() + "," + getDpi()+"," + getCorreo()+ getTelefono ()+")");
       
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



