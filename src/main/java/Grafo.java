import java.util.ArrayList;
import java.util.List;

/* Esta clase contiene la representación de una grafo no dirigido ponderado por medio
*  de las matrices de adyacencia y peso
*
*  @author Kianush Atighi-Moghaddam
*/

public class Grafo {
    private int cantVertices;
    final private int pesoMax = 9999;      //límite superior del valor correspondiente al peso de cada arista
    private List<List<Integer>> matrizAdyacencia = new ArrayList<>();  //matriz de adyacencia
    private List<List<Integer>> matrizPesos = new ArrayList<>();   //matriz de pesos

    //Método constructor que inicializa la matriz de adyacencia y la matriz de pesos de tamaño "cantVertices" con ceros
    public Grafo(int cantVertices){
        this.cantVertices = cantVertices;

        for (int i=0; i<cantVertices; i++){
            this.matrizAdyacencia.add(new ArrayList<>(cantVertices));  //se inicializan las filas de las 2 matrices
            this.matrizPesos.add(new ArrayList<>(cantVertices));
            for (int j=0; j<cantVertices; j++){
                this.matrizAdyacencia.get(i).add(0);     //se llenan las columnas con ceros
                this.matrizPesos.get(i).add(pesoMax);     //se llenan las columnas con el pesos máximos
            }
        }
    }

    //Método que agrega una arista junto con su peso a la matriz de adyacencia
    public void agregarArista(int v1, int v2, int peso) throws IllegalArgumentException ,
            ArrayIndexOutOfBoundsException{
        if(peso <=0){
            throw new IllegalArgumentException();
        }
        this.establecerValorMatrizAdyacencia(1,v1,v2);
        this.establecerValorMatrizAdyacencia(1,v2,v1);
        this.establecerPesoMatrizPesos(peso,v1,v2);
        this.establecerPesoMatrizPesos(peso,v2,v1);
    }

    //Método que elimina una arista junto con su peso de la matriz de adyacencia
    public void eliminarArista(int v1, int v2) throws ArrayIndexOutOfBoundsException,
            IllegalArgumentException {
        if(!this.existeArista(v1,v2)){
            throw new IllegalArgumentException("La arista no existe.");
        }
        this.establecerValorMatrizAdyacencia(0,v1,v2);
        this.establecerValorMatrizAdyacencia(0,v2,v1);
        this.establecerPesoMatrizPesos(pesoMax,v1,v2);
        this.establecerPesoMatrizPesos(pesoMax,v2,v1);
    }

    public boolean grafoEsArbol(){
        if(this.grafoEsConexo()){
            for (int i=0; i<matrizAdyacencia.size(); i++){
                for (int j=0; j<matrizAdyacencia.size(); j++){
                    if(this.existeArista(i,j)){
                        if(!this.aristaEsPuente(i,j)){
                            return false;
                        }
                    }

                }
            }
        }else{
            return false;
        }
        return true;
    }

    public boolean grafoEsConexo(){
        boolean[] visitados; //arreglo que comprueba cuales vértices estan conectados por un camino desde el vertice "i"
        int acum;   //acumula la cantidad de valores "true" del arreglo "visitados"

        for (int i=0; i<this.matrizAdyacencia.size(); i++){    //bucle que recorre cada vertice "i" (fila)
            visitados = new boolean[this.matrizAdyacencia.size()];/* se inicializa "visitados" con valores "false", este
          arreglo debe contener solamente valores "true" al final de la iteración en cada vertice "i" para ser conexo */
            visitados[i] = true;        //el vertice "i" se visita a si mismo
            acum = 1;
            for (int j=0; j<this.matrizAdyacencia.size(); j++){    //bucle que recorre cada columna
                if((this.existeArista(i,j)) && (i!=j)){
                    visitados[j] = true;     //el vertice "i" visita al vertice "j", ya que este posee valor 1
                    acum++;
                }
            }
            if(acum < matrizAdyacencia.size()){    //luego se comprueba si existen o no caminos a los vertices restantes
                int contador = 0;
                do {
                    for (int i2=0; i2<this.matrizAdyacencia.size(); i2++){//bucle que recorre filas d vertices visitados
                        if((visitados[i2]) && (i!=i2)){
                            for (int j=0; j<this.matrizAdyacencia.size(); j++){  //bucle que recorre columnas "j"
                                if((this.existeArista(i2,j)) && (!visitados[j])){
                                    visitados[j] = true;    //el vertice "i2" visita al vertice "j" de valor 1
                                    acum++;
                                }
                            }
                        }
                    }
                    contador++;
                    if(contador > (this.matrizAdyacencia.size())){ /* en caso de que no existan mas caminos, el grafo*/
                        return false;                          /* no es conexo  */
                    }
                }while(acum < this.matrizAdyacencia.size()); //el iterador termina cuando "i" se pueda conectar a todos
            }
        }
        return true;
    }

    /*  Método que determina si una arista es puente o no, primero se elimina la arista de la matriz de adyacencia,
    **  a continuación se determina si el grafo es conexo o no, en caso de que no sea conexo la arista resulta ser
    **  puente y viceversa, finalmente se regresa el valor original de la arista eliminada     */
    public boolean aristaEsPuente(int v1, int v2) throws ArrayIndexOutOfBoundsException{
        if(!this.existeArista(v1,v2)){
            throw new IllegalArgumentException("La arista no existe.");
        }
        if(!this.grafoEsConexo()){
            throw new IllegalArgumentException("El grafo no es conexo.");
        }
        this.establecerValorMatrizAdyacencia(0,v1,v2);
        this.establecerValorMatrizAdyacencia(0,v2,v1);
        if(!this.grafoEsConexo()){
            this.establecerValorMatrizAdyacencia(1,v1,v2);
            this.establecerValorMatrizAdyacencia(1,v2,v1);
            return true;
        }
        this.establecerValorMatrizAdyacencia(1,v1,v2);
        this.establecerValorMatrizAdyacencia(1,v2,v1);
        return false;

    }

    public void imprimirMatrizAdyacencia() {
            System.out.println("\nMatriz de adyacencia: ");
            System.out.println();
            for (int i = 0; i < this.matrizAdyacencia.size(); i++) {
                System.out.println("(" + i + "): " + this.matrizAdyacencia.get(i));
        }
    }

    public void imprimirMatrizPesos() {
            System.out.println("\nMatriz de pesos: ");
            System.out.println();
            for (int i = 0; i < this.matrizPesos.size(); i++) {
                System.out.println("(" + i + "): " + this.matrizPesos.get(i));
        }
    }

    public boolean existeArista(int v1, int v2) throws ArrayIndexOutOfBoundsException{
        return (this.matrizAdyacencia.get(v1).get(v2) != 0);
    }

    public int obtenerPesoArista(int v1, int v2) throws ArrayIndexOutOfBoundsException{
        return this.matrizPesos.get(v1).get(v2);
    }

    //Método que asigna un valor numérico a la matriz de adyacencia, en la fila y columna correspondientes
    public void establecerValorMatrizAdyacencia(int valor, int fila, int columna){
        this.matrizAdyacencia.get(fila).set(columna,valor);
    }

    //Método que asigna un valor numérico a la matriz de pesos, en la fila y columna correspondientes
    public void establecerPesoMatrizPesos(int peso, int fila, int columna){
        this.matrizPesos.get(fila).set(columna,peso);
    }

    public void agregarVertice(){
        this.matrizAdyacencia.add(new ArrayList<>()); //se inicializa fila del vertice "cantVertices" en las 2 matrices
        this.matrizPesos.add(new ArrayList<>());
        for (int i=0; i<this.cantVertices; i++){ //bucle que agrega una columna adicional a cada fila de las 2 matrices
            this.matrizAdyacencia.get(i).add(0);
            this.matrizPesos.get(i).add(pesoMax);
        }
        for (int i=0; i<this.cantVertices + 1; i++){
            this.matrizAdyacencia.get(cantVertices).add(0);     //se llena la fila "cantVertices" con ceros
            this.matrizPesos.get(cantVertices).add(pesoMax);   //se llena la fila "cantVertices" con pesos máximos
        }
        this.cantVertices++;
    }

    public void eliminarVertice(int v){
        this.matrizAdyacencia.remove(v);
        this.matrizPesos.remove(v);
        for (int i=0; i<this.cantVertices; i++){
            this.matrizAdyacencia.get(i).remove(v);
            this.matrizPesos.get(i).remove(v);
        }
        this.cantVertices--;
    }
}