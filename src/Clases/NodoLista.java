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
   
   NodoLista (Object o){
       this.elemento=o;
       this.siguiente=null;
   }
   NodoLista(Object o, NodoLista n){
       this.elemento=o;
       this.siguiente=n;
   }
}
