public class Implementacion {
    public static void main(String[] args) {
        Grafo g = new Grafo(6);
        g.agregarArista(0,1,10);
        g.agregarArista(1,2,10);
        g.agregarArista(2,3,10);
        g.agregarArista(3,4,10);
        g.agregarArista(4,5,10);
        g.imprimirMatrizAdyacencia();

    }
}
