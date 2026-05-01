package arboles.programas;

import arboles.ArbolBinarioDeBusquedaEnteros;
import arboles.Lista;

import java.util.Random;

/*
 * Programa 2: inserción aleatoria sin repetidos.
 *
 * - Calcula la suma (getSuma()):
 *   La suma vuelve a ser 8256, porque se insertan los mismos valores de 0 a 128.
 *
 * - Verifica que la suma es la misma accediendo en los 3 recorridos posibles:
 *   Preorden, postorden e inorden contienen los mismos datos. Aunque el orden
 *   cambie, la suma de sus elementos sigue siendo 8256.
 *
 * - Verifica que la suma es la misma cuando se suman los elementos de los
 *   subárboles izquierdo y derecho. ¿Por qué?
 *   La igualdad correcta es:
 *   sumaTotal = sumaIzquierda + raíz + sumaDerecha.
 *   En la inserción aleatoria la raíz es el primer valor barajado. Con la
 *   semilla fija usada aquí, la raíz es 111, así que no se puede omitir la raíz
 *   como si valiera 0.
 *
 * - ¿Cuál es la altura del árbol? ¿por qué?
 *   Con la semilla fija usada aquí, la altura es 18. Es menor que en la prueba
 *   ordenada porque los valores no caen todos por la derecha; se reparten entre
 *   ambos subárboles según el orden aleatorio.
 *
 * - ¿Cuál es el camino para llegar al valor 110? ¿Cuál es su longitud?
 *   Con esta semilla, el camino es 111, 2, 110. Tiene 3 nodos, así que su
 *   longitud es 3 - 1 = 2.
 */
public class ProgramaInsercionAleatoria {

    private static final int MAXIMO = 128;
    private static final int VALOR_CAMINO = 110;
    private static final long SEMILLA = 12345L;

    public static void main(String[] args) {
        ArbolBinarioDeBusquedaEnteros arbol = new ArbolBinarioDeBusquedaEnteros();
        int[] numeros = crearNumerosOrdenados();
        barajar(numeros, new Random(SEMILLA));

        // Insertamos el array ya mezclado. No hay repetidos porque el array tiene 0..128 una sola vez.
        for (int numero : numeros) {
            arbol.add(numero);
        }

        // Igual que en el primer programa: sumamos desde el árbol y desde sus recorridos.
        int sumaArbol = arbol.getSuma();
        Lista<Integer> preOrden = arbol.getListaPreOrden();
        Lista<Integer> postOrden = arbol.getListaPostOrden();
        Lista<Integer> ordenCentral = arbol.getListaOrdenCentral();

        int sumaPreOrden = sumar(preOrden);
        int sumaPostOrden = sumar(postOrden);
        int sumaOrdenCentral = sumar(ordenCentral);

        // La raíz es el primer número que se insertó.
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
        verificar(raiz == 111, "Con esta semilla, la raíz esperada es 111");
        verificar(altura == 18, "Con esta semilla, la altura esperada es 18");
        verificar(longitudCamino == 2, "Con esta semilla, la longitud del camino a 110 debe ser 2");

        System.out.println("Programa 2: inserción aleatoria de 0 a 128 sin repetir");
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

    // Crea 0, 1, 2, ..., 128.
    private static int[] crearNumerosOrdenados() {
        int[] numeros = new int[MAXIMO + 1];
        for (int i = 0; i <= MAXIMO; i++) {
            numeros[i] = i;
        }
        return numeros;
    }

    // Mezclamos intercambiando posiciones. Así seguimos teniendo los mismos números.
    private static void barajar(int[] numeros, Random random) {
        for (int i = numeros.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temporal = numeros[i];
            numeros[i] = numeros[j];
            numeros[j] = temporal;
        }
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
