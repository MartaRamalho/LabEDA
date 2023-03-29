package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.Cola;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.lineales.ArrayCola;

// EN LA SEGUNDA SESION: incluir los siguientes import: 
import librerias.estructurasDeDatos.modelos.UFSet;
import librerias.estructurasDeDatos.jerarquicos.ForestUFSet;
import librerias.estructurasDeDatos.modelos.ColaPrioridad;
import librerias.estructurasDeDatos.jerarquicos.MonticuloBinarioR0;

/** Clase abstracta Grafo: Base de la jerarquia Grafo, que define el 
 *  comportamiento de un grafo.<br> 
 *  No es una interfaz porque incluye el codigo de las operaciones de un 
 *  grafo que son independientes tanto de su tipo como de su implementacion.<br>
 *  
 *  @version Diciembre 2018
 */

public abstract class Grafo {

    protected boolean esDirigido; // Indica si un grafo es Dirigido o no
    protected int[] visitados;    // Nodos visitados en un Recorrido
    protected int ordenVisita;    // Orden de visita de nodos en un Recorrido
    protected Cola<Integer> q;    // Cola para un Recorrido BFS
    
    /** Crea un grafo vacio, Dirigido si dirigido es true
      * o No Dirigido en caso contrario.
      * 
      * @param dirigido Indica el tipo del grafo, Dirigido o No
     */
    public Grafo(boolean dirigido) { esDirigido = dirigido; }
    
    /** Comprueba si un grafo es o no Dirigido.
      *
      * @return boolean true si el grafo es Dirgido y false si es No Dirigido
     */
    public boolean esDirigido() { return esDirigido; }
    
    /** Devuelve el numero de vertices de un grafo.
      * 
      * @return int numero de vertices
      */
    public abstract int numVertices();
    
    /** Devuelve el numero de aristas de un grafo.
      * 
      * @return int numero de aristas
     */
    public abstract int numAristas();
    
    /** Comprueba si la arista (i,j) esta en un grafo.
      * 
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @return boolean true si (i,j) esta en el grafo y false en caso contrario
     */
    public abstract boolean existeArista(int i, int j);
    
    /** Devuelve el peso de la arista (i,j) de un grafo, 0 si dicha arista 
      * no esta en el grafo.
      * 
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @return double Peso de la arista (i,j), 0 si no existe.
      */
    public abstract double pesoArista(int i, int j);
    
    /** Si no esta, inserta la arista (i,j) en un grafo No Ponderado.
      * 
      * @param i    Vertice origen
      * @param j    Vertice destino
     */
    public abstract void insertarArista(int i, int j);
    
    /** Si no esta, inserta la arista (i, j) de peso p en un grafo Ponderado.
      * 
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @param p    Peso de la arista (i, j)
     */
    public abstract void insertarArista(int i, int j, double p);

    /** Devuelve una ListaConPI que contiene los adyacentes al vertice i.
      * 
      * @param i Vertice del que se obtienen los adyacentes
      * @return ListaConPI con los vertices adyacentes a i
     */
    public abstract ListaConPI<Adyacente> adyacentesDe(int i);
    
    /** Devuelve un String con cada uno de los vertices de un grafo y sus 
      * adyacentes, en orden de insercion.
      * 
      * @return  String que representa a un grafo
     */               
    public String toString() {
        String res = "";  
        for (int  i = 0; i < numVertices(); i++) {
            res += "Vertice: " + i;
            ListaConPI<Adyacente> l = adyacentesDe(i);
            if (l.esVacia()) { res += " sin Adyacentes "; }
            else { res += " con Adyacentes "; } 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                res +=  l.recuperar() + " ";  
            }
            res += "\n";  
        }
        return res;      
    }
    
    /** Devuelve un array con cada uno de los vertices de un grafo y sus 
      * adyacentes, en orden BFS.
      * 
      * @return  Array de vertices visitados en el recorrido BFS
     */   
    public int[] toArrayBFS() {
        int[] res = new int[numVertices()];
        visitados = new int[numVertices()]; 
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visitados[i] == 0) { toArrayBFS(i, res); }
        }
        return res;
    }
    // Recorrido BFS del vertice origen, que almacena en res
    // su resultado
    protected void toArrayBFS(int origen, int[] res) { 
        res[ordenVisita++] = origen;
        visitados[origen] = 1;
        q.encolar(origen);
        while (!q.esVacia()) {
            int u = q.desencolar().intValue(); 
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                if (visitados[a.destino] == 0) {
                    res[ordenVisita++] = a.destino;
                    visitados[a.destino] = 1;
                    q.encolar(a.destino);
                }
            }  
        }
    }
    
    protected void toArrayBFS(int origen, Arista[] res) { 
        visitados[origen] = 1;
        q.encolar(origen);
        while (!q.esVacia()) {
            int u = q.desencolar().intValue(); 
            ListaConPI<Adyacente> l = adyacentesDe(u); 
            for (l.inicio(); !l.esFin(); l.siguiente()) {
                Adyacente a = l.recuperar(); 
                if (visitados[a.getDestino()] == 0) {
                    res[ordenVisita++]= new Arista(u, a.getDestino(), 
                        pesoArista(u, a.getDestino()));
                    visitados[a.getDestino()] = 1;
                    q.encolar(a.getDestino());
                }
            }  
        }
    }
    
    /** PRECONDICION: !this.esDirigido()
      * Devuelve un subconjunto de aristas que conectan todos los vertices
      * de un grafo No Diridigo y Conexo, o null si el grafo no es Conexo.
      *  
      * @return Arista[], array con las numV - 1 aristas que conectan  
      *                   los numV vertices del grafo, o null si el grafo 
      *                   no es Conexo
     */ 
    public Arista[] arbolRecubrimientoBFS() {
        Arista[] res = new Arista[numVertices()-1];
        visitados = new int[numVertices()]; 
        ordenVisita = 0;  
        q = new ArrayCola<Integer>();
        for (int  i = 0; i < numVertices(); i++) {
            if (visitados[i] == 0) { toArrayBFS(i, res); }
        }
        if(ordenVisita!=numVertices()-1) return null;
        return res;
    }
    
    /** PRECONDICION: !this.esDirigido()
      * Devuelve un subconjunto de aristas que, con coste minimo,  
      * conectan todos los vertices de un grafo No Dirigido y Conexo, 
      * o null si el grafo no es Conexo.
      * 
      * @return Arista[], array con las numV - 1 aristas que conectan 
      *                   los numV vertices con coste minimo, o null 
      *                   si el grafo no es Conexo
     */ 
    public Arista[] kruskal() {       
        ColaPrioridad<Arista> feasibleEdges = new MonticuloBinarioR0();
        for(int i=0; i<numVertices(); i++){
            ListaConPI<Adyacente> l= adyacentesDe(i);
            for(l.inicio(); !l.esFin(); l.siguiente()){
                int w = l.recuperar().getDestino();
                feasibleEdges.insertar(new Arista(i, w, pesoArista(i,w)));
            }
        }
        UFSet cc= new ForestUFSet(numVertices());
        Arista[] res = new Arista[numVertices()-1];
        int numberEdges = 0;
        while(numberEdges!=numVertices()-1 && !feasibleEdges.esVacia()){
            Arista min = feasibleEdges.eliminarMin();
            int ccV= cc.find(min.getOrigen());
            int ccW = cc.find(min.getDestino());
            if(ccV!=ccW){
                res[numberEdges++]=min;
                cc.union(ccV, ccW);
            }
        }
        if(numberEdges==numVertices()-1) return res;
        else return null;
    }
    
    public int[] gradosMST(){
        int[] sol = new int[numVertices()];
        for(int i=0; i<sol.length; i++){
            sol[i]=0;
        }
        Arista[] aristas = this.kruskal();
        if(aristas==null) return null;
        for(int i=0; i<aristas.length; i++){
            int v = aristas[i].getOrigen();
            sol[v]++;
            int w = aristas[i].getDestino();
            sol[w]++;
        }
        return sol;
    }
}
