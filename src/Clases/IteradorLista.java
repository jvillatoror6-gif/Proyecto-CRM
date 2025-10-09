/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS
 */
public class IteradorLista {
    NodoLista actual;
    
    IteradorLista(NodoLista n){
    this.actual=n;
    }
    boolean estaFuera(){
        if(actual==null)
            return true;
        else
            return false;
    }
    Object obtener (){
        return this.actual.elemento;
    }
    void(!estaFuera()){
        this.actual=this.actual.siguiente;
    }
}
