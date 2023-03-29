// metodo main a incluir en la clase BuscadorDeLaBibl para el test del metodo tablaFrecuencias
public static void main(String[] args) throws FileNotFoundException {
    BuscadorDeLaBibl bib = new BuscadorDeLaBibl();    
    String[] a = {"ficheros", "tabla", "programa", "estrellas", "tocadiscos", "ladrillos"};
    ListaConPI<Termino> lt = new LEGListaConPI<Termino>();
    for (String s : a) lt.insertar(new Termino(s));
    Map<Termino,Integer> m = bib.tablaFrecuencias(lt);    
}
