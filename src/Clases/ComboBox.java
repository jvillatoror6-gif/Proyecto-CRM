/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author kenet
 */
public class ComboBox {
 private int Id;
   private String Nombre;
   private int Idcategoria;
   
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
   
   
   public ComboBox( int Id, String Nombre){
       this.Id=Id;
       this.Nombre=Nombre;
       
   }
   
    @Override
    public String toString(){
        return Nombre; 
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
}

