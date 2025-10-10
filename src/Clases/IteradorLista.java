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
<<<<<<< HEAD
        return this.actual.getElemento();
=======
        return this.actual.elemento;
    }
    void avanzar (){
        if (!estaFuera()){
        this.actual = this.actual.siguiente;
>>>>>>> 5c6d47b02f197a4fdcea7284600f6fbf7a840f82
    }
   
}
}   