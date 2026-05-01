package arboles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArbolBinarioDeBusquedaEnterosTest {

    @Test
    void getSuma() {
        ArbolBinarioDeBusquedaEnteros vacio = new ArbolBinarioDeBusquedaEnteros();
        assertEquals(0, vacio.getSuma());

        ArbolBinarioDeBusquedaEnteros uno = new ArbolBinarioDeBusquedaEnteros();
        uno.add(5);
        assertEquals(5, uno.getSuma());

        ArbolBinarioDeBusquedaEnteros equilibrado = crearEnteros(4, 2, 6, 1, 3, 5, 7);
        assertEquals(28, equilibrado.getSuma());

        ArbolBinarioDeBusquedaEnteros grande = new ArbolBinarioDeBusquedaEnteros();
        for (int i = 0; i <= 128; i++) {
            grande.add(i);
        }
        assertEquals(8256, grande.getSuma());
    }

    @Test
    void getSubArbolIzquierda() {
        ArbolBinarioDeBusquedaEnteros vacio = new ArbolBinarioDeBusquedaEnteros();
        assertEquals(0, vacio.getSubArbolIzquierda().getSuma());

        ArbolBinarioDeBusquedaEnteros arbol = crearEnteros(4, 2, 6, 1, 3, 5, 7);
        ArbolBinarioDeBusquedaEnteros izquierda = arbol.getSubArbolIzquierda();

        assertEquals(6, izquierda.getSuma());
        assertLista(izquierda.getListaOrdenCentral(), 1, 2, 3);
    }

    @Test
    void getSubArbolDerecha() {
        ArbolBinarioDeBusquedaEnteros vacio = new ArbolBinarioDeBusquedaEnteros();
        assertEquals(0, vacio.getSubArbolDerecha().getSuma());

        ArbolBinarioDeBusquedaEnteros arbol = crearEnteros(4, 2, 6, 1, 3, 5, 7);
        ArbolBinarioDeBusquedaEnteros derecha = arbol.getSubArbolDerecha();

        assertEquals(18, derecha.getSuma());
        assertLista(derecha.getListaOrdenCentral(), 5, 6, 7);
    }

    private static ArbolBinarioDeBusquedaEnteros crearEnteros(int... datos) {
        ArbolBinarioDeBusquedaEnteros arbol = new ArbolBinarioDeBusquedaEnteros();
        for (int dato : datos) {
            arbol.add(dato);
        }
        return arbol;
    }

    private static void assertLista(Lista<Integer> lista, int... esperados) {
        assertEquals(esperados.length, lista.size());
        for (int i = 0; i < esperados.length; i++) {
            assertEquals(esperados[i], lista.get(i));
        }
    }
}
