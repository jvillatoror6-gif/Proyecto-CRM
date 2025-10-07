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
 * @author ASUS
 */
public class CategoriaIncidente {

/**
 *
 * @author kenet
 */
public class Categoria {

        /**
         * @return the estado
         */
        
private int id; 
   private String nombre;
        private String queja;
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
    public String getQueja() {
        return queja;
    }

    /**
     
     */
    public void setQueja(String queja) {
        this.queja = queja;
    }
   private String estado;
   public String getEstado() {
            return estado;
        }

        /**
         * @param estado the estado to set
         */
        public void setEstado(String estado) {
            this.estado = estado;
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
       st.execute("insert into categorias (nombre)values ('"+getQueja()+"')");
       
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
}
