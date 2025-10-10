/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author ASUS
 */
public class Lista {
    NodoLista cabecera;
    
    public Lista() {
        this.cabecera = new NodoLista(null);
    }

    boolean estaVacia() {
        return this.cabecera.getSiguiente() == null;
    }

    IteradorLista buscar(Object o) {
        NodoLista nodo = this.cabecera.getSiguiente();
        while (nodo != null && !nodo.elemento.equals(o)) {
            nodo = nodo.getSiguiente();
        }
        return new IteradorLista(nodo);
    }

    void insertar(Object o, IteradorLista it) {
        if (it != null && it.actual != null) {
            it.actual.setSiguiente(new NodoLista(o, it.actual.getSiguiente()));
        }
    }

    void eliminar(Object o) {
        IteradorLista it = encontrarPrevio(o);
        if (it.actual.getSiguiente() != null) {
            it.actual.setSiguiente(it.actual.getSiguiente().getSiguiente());
        }
    }

    IteradorLista encontrarPrevio(Object o) {
        NodoLista nodo = this.cabecera;
        while (nodo.getSiguiente() != null && !nodo.siguiente.elemento.equals(o)) {
            nodo = nodo.getSiguiente();
        }
        return new IteradorLista(nodo);
    }
}

