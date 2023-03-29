package librerias.estructurasDeDatos.grafos;

/** Clase Arista: representa una arista de un grafo.<br> 
 *  
 *  @version noviembre 2021
 */
 
public class Arista implements Comparable<Arista>{
    
    // UNA Arista TIENE UN vertice origen y UN vertice destino:
    /*COMPLETAR*/
    protected int origen, destino;
    protected double peso;
    
    // UNA Arista TIENE UN peso:
    /*COMPLETAR*/
    
    /** Crea una arista (v, w) con peso p.
      *
      * @param v  Vertice origen
      * @param w  Vertice destino
      * @param p  Peso
     */
    public Arista(int v, int w, double p) {
        origen = v;
        destino= w;
        peso=p;
    }

    /** Devuelve el vertice origen de una arista.
      *
      * @return int vertice origen
     */
    public int getOrigen() {     
        return origen;
    }
    
    /** Devuelve el vertice destino de una arista.
      *
      * @return int vertice destino
     */
    public int getDestino() {  
        return destino;
    }
    
    /** Devuelve el peso de una arista.
      *
      * @return double Peso de la arista
     */
    public double getPeso() {
        // CAMBIAR 
        return peso;  
    }
    
    /** Devuelve un String que representa una arista
      * en formato (origen, destino, peso)
      *
      * @return  String que representa la arista
     */
    public String toString() {
        String res = "("+origen+", "+destino+", "+peso+")";
        return res;   
    }
    
    public int compareTo(Arista a){
        if(this.getPeso()<a.getPeso()) return -1;
        else if(this.getPeso()>a.getPeso()) return 1;
        else return 0;
    }
}