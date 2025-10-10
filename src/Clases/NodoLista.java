/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS
 */
public class NodoLista {
    Object elemento;
   NodoLista siguiente;
    
   public Object getElemento() {
        return elemento;
    }

    /**
     * @param elemento the elemento to set
     */
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    /**
     * @return the siguiente
     */
    public NodoLista getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(NodoLista siguiente) {
        this.siguiente = siguiente;
    }
   
   
   NodoLista (Object o){
       this.elemento=o;
       this.siguiente=null;
   }
   NodoLista(Object o, NodoLista n){
       this.elemento=o;
       this.siguiente=n;
   }
}
