package librerias.estructurasDeDatos.jerarquicos;

import librerias.estructurasDeDatos.modelos.ColaPrioridad;

/** Clase MonticuloBinario: representa un Heap con Raiz en 1.
 * 
 *  Sus caracteristicas son las siguientes:
 *  @param <E>, el tipo de sus elementos es E extends Comparable<E>
 *  ATRIBUTOS (protected para la herencia): 
 *      TIENE UN array con los elementos (E[] elArray)
 *      TIENE UNA talla que indica el numero de elementos
 *      
 * @author (profesores EDA)
 * @version (Curso 2021-2022)
 **/
 
public class MonticuloBinarioR0<E extends Comparable<E>> 
    implements ColaPrioridad<E> {  
    
    protected static final int C_P_D = 11; 
    
    // Un Heap es un AB Completo y, por tanto,...
    // TIENE UNA Representacion Implicita
    protected E[] elArray;
    // TIENE UNA talla o numero de nodos
    protected int talla;
    
    /** Crea una Cola de Prioridad (CP) vacia 
     *  con capacidad inicial C_P_D
     */
    public MonticuloBinarioR0() { this(C_P_D); }
    
    /** Crea una Cola de Prioridad (CP) vacia 
     * con capacidad inicial n 
     */
    @SuppressWarnings("unchecked")
    public MonticuloBinarioR0(int n) { 
        elArray = (E[]) new Comparable[n];
        talla = 0;
    }
    
    /** Comprueba si una CP esta vacia o no 
     *  @return boolean que indica si la CP esta vacia
     */
    public boolean esVacia() { return talla == 0; }
        
    /** SII !esVacia(): obtiene el dato con maxima prioridad de la CP 
     *  @return Elemento con maxima prioridad de la CP
     */
    public E recuperarMin() { return elArray[0]; }
    
    /** Inserta el dato e en una CP, atendiendo a su prioridad 
     *  @param e  Elemento a insertar  
     */
    public void insertar(E e) {
        if (talla == elArray.length) { duplicarArray(); }
        int  posIns = talla;
        talla++;
        while (posIns > 0 && e.compareTo(elArray[(posIns - 1) / 2 ]) < 0) { 
            elArray[posIns] = elArray[(posIns - 1) / 2 ]; 
            posIns = (posIns - 1) / 2 ;
        }
        elArray[posIns] = e;
    }
   
    // Duplica la capacidad de elArray
    @SuppressWarnings("unchecked")
    protected void duplicarArray() {
        E[] nuevo = (E[]) new Comparable[elArray.length * 2];
        System.arraycopy(elArray, 0, nuevo, 0, talla);
        elArray = nuevo;
    }  
    
    /** SII !esVacia(): obtiene y borra el dato con 
     *  maxima prioridad de la CP 
     *  @return Elemento con maxima prioridad de la CP
     */
    public E eliminarMin() {
        E elMinimo = elArray[0];
        elArray[0] = elArray[talla-1];
        talla--;
        hundir(0);
        return elMinimo;
    }
    
    //  Si es necesario, restablece la propiedad de orden 
    //  de un Heap hundiendo el elemento de elArray situado 
    //  en la posicion pos 
    //  @param pos  Posicion del elemento a hundir
    protected void hundir(int pos) {
        int posActual = pos;
        E aHundir = elArray[posActual]; 
        int hijo = posActual * 2 + 1; 
        boolean esHeap = false;
        while (hijo <= talla-1 && !esHeap) {
            if (hijo != talla-1 
                && elArray[hijo + 1].compareTo(elArray[hijo]) < 0) { 
                hijo++; 
            }
            if (elArray[hijo].compareTo(aHundir) < 0) {
                elArray[posActual] = elArray[hijo];
                posActual = hijo; 
                hijo = posActual * 2 + 1;
            } else { esHeap = true; }
        }
        elArray[posActual] = aHundir;
    }
}
