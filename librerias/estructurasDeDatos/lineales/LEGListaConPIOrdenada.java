package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.*;

/**
 * Write a description of class LEGListaConPIOrdenada here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LEGListaConPIOrdenada<E extends Comparable<E>> extends LEGListaConPI<E> implements ListaConPI<E>{
    public LEGListaConPIOrdenada() {
        super();
    }

    @Override
    public void insertar(E e) {
        if(esVacia()) super.insertar(e);
        else{ 
            inicio();
            while(!esFin() && recuperar().compareTo(e)<0){
                siguiente();
            }
            super.insertar(e);
        }
    }
}
