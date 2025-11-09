/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class Incidente {
    private int id;
    private String queja;
    private String estado;
    private String descripcion;
    private String IDCategoria;
    /**
     * @return the IdCatergoriaIncidente
     */
    public String getIdCategoria() {
        return IDCategoria;
    }

    /**
     * @param IdCategoria the IdCatergoriaIncidente to set
     */
    public void setIdCategoria(String IdCategoria) {
        this.IDCategoria = IdCategoria;
    }

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
     * @return the queja
     */
    public String getQueja() {
        return queja;
    }

    /**
     * @param queja the queja to set
     */
    public void setQueja(String queja) {
        this.queja = queja;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
       st.execute("insert into incidentes (Id, Queja, Estado, Descripcion, IdCategoria)\n" +
           "values ('"+getId()+"',"+getQueja()+","+getEstado()+","+getDescripcion()+")");
       
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

    public void setCategoria(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}   

