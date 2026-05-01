package arboles.programas;

import arboles.ArbolBinarioDeBusquedaEnteros;
import arboles.Lista;

/*
 * Programa 1: inserción ordenada.
 *
 * - Calcular la suma (getSuma()):
 *   La suma de 0 + 1 + ... + 128 es 8256.
 *
 * - Verificar que la suma es la misma accediendo en los 3 recorridos posibles:
 *   Preorden, postorden e inorden contienen los mismos datos. Cambia el orden
 *   de visita, pero al sumar todos sus elementos el resultado sigue siendo 8256.
 *
 * - Verificar que la suma es la misma cuando se suman los elementos de los
 *   subárboles izquierdo y derecho. ¿Por qué?
 *   En esta prueba la raíz es 0. El subárbol izquierdo queda vacío y el derecho
 *   contiene 1..128. La relación general es:
 *   sumaTotal = sumaIzquierda + raíz + sumaDerecha.
 *   Como la raíz vale 0, también se cumple:
 *   sumaTotal = sumaIzquierda + sumaDerecha.
 *
 * - ¿Cuál es la altura del árbol?
 *   La altura es 128. Hay 129 nodos colocados como una cadena hacia la derecha,
 *   y la altura se mide como número de saltos: 129 - 1 = 128.
 *
 * - ¿Cuál es el camino para llegar al valor 110? ¿Cuál es su longitud?
 *   El camino es 0, 1, 2, 3, ..., 110. Contiene 111 nodos, por lo que su
 *   longitud es 111 - 1 = 110.
 */
public class ProgramaInsercionOrdenada {

    private static final int MAXIMO = 128;
    private static final int VALOR_CAMINO = 110;

    public static void main(String[] args) {
        ArbolBinarioDeBusquedaEnteros arbol = new ArbolBinarioDeBusquedaEnteros();

        // Primero metemos los números exactamente como pide el enunciado.
        for (int i = 0; i <= MAXIMO; i++) {
            arbol.add(i);
        }

        // Calculamos la suma de varias formas para poder compararlas.
        int sumaArbol = arbol.getSuma();
        Lista<Integer> preOrden = arbol.getListaPreOrden();
        Lista<Integer> postOrden = arbol.getListaPostOrden();
        Lista<Integer> ordenCentral = arbol.getListaOrdenCentral();

        int sumaPreOrden = sumar(preOrden);
        int sumaPostOrden = sumar(postOrden);
        int sumaOrdenCentral = sumar(ordenCentral);

        // En preorden el primer elemento visitado es la raíz.
        int raiz = preOrden.get(0);
        int sumaIzquierda = arbol.getSubArbolIzquierda().getSuma();
        int sumaDerecha = arbol.getSubArbolDerecha().getSuma();

        int altura = arbol.getAltura();
        Lista<Integer> camino = arbol.getCamino(VALOR_CAMINO);
        int longitudCamino = camino.estaVacia() ? -1 : camino.size() - 1;

        verificar(sumaArbol == 8256, "La suma total debe ser 8256");
        verificar(sumaArbol == sumaPreOrden, "La suma por preorden no coincide");
        verificar(sumaArbol == sumaPostOrden, "La suma por postorden no coincide");
        verificar(sumaArbol == sumaOrdenCentral, "La suma por orden central no coincide");
        verificar(sumaArbol == sumaIzquierda + raiz + sumaDerecha, "La suma de raíz y subárboles no coincide");
        verificar(sumaArbol == sumaIzquierda + sumaDerecha, "En este caso, la raíz es 0 y no altera la suma");
        verificar(altura == 128, "La altura debe ser 128");
        verificar(longitudCamino == 110, "La longitud del camino a 110 debe ser 110");

        System.out.println("Programa 1: inserción ordenada de 0 a 128");
        System.out.println("Suma con getSuma(): " + sumaArbol);
        System.out.println("Suma en preorden: " + sumaPreOrden);
        System.out.println("Suma en postorden: " + sumaPostOrden);
        System.out.println("Suma en orden central: " + sumaOrdenCentral);
        System.out.println("Raíz: " + raiz);
        System.out.println("Suma subárbol izquierdo: " + sumaIzquierda);
        System.out.println("Suma subárbol derecho: " + sumaDerecha);
        System.out.println("Altura: " + altura);
        System.out.println("Camino hasta 110: " + camino);
        System.out.println("Longitud del camino: " + longitudCamino);
    }

    // Recorremos la lista a mano y vamos acumulando sus valores.
    private static int sumar(Lista<Integer> lista) {
        int suma = 0;
        for (Integer dato : lista) {
            suma += dato;
        }
        return suma;
    }

    // si algo está mal, paro el programa.
    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }
}
