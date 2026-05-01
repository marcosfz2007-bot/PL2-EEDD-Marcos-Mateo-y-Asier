package arboles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArbolBinarioDeBusquedaTest {

    @Test
    void add() {
        ArbolBinarioDeBusqueda<Integer> arbol = new ArbolBinarioDeBusqueda<>();

        arbol.add(4);
        arbol.add(2);
        arbol.add(6);
        arbol.add(2);

        assertLista(arbol.getListaOrdenCentral(), 2, 4, 6);
        assertThrows(IllegalArgumentException.class, () -> arbol.add(null));
    }

    @Test
    void getGrado() {
        assertEquals(0, new ArbolBinarioDeBusqueda<Integer>().getGrado());

        ArbolBinarioDeBusqueda<Integer> uno = crear(4);
        assertEquals(0, uno.getGrado());

        ArbolBinarioDeBusqueda<Integer> cadena = crear(4, 6);
        assertEquals(1, cadena.getGrado());

        ArbolBinarioDeBusqueda<Integer> dosHijos = crear(4, 2, 6);
        assertEquals(2, dosHijos.getGrado());
    }

    @Test
    void getAltura() {
        assertEquals(-1, new ArbolBinarioDeBusqueda<Integer>().getAltura());
        assertEquals(0, crear(4).getAltura());
        assertEquals(2, crear(4, 6, 7).getAltura());
        assertEquals(2, crear(4, 2, 6, 1, 3, 5, 7).getAltura());
    }

    @Test
    void getListaDatosNivel() {
        ArbolBinarioDeBusqueda<Integer> vacio = new ArbolBinarioDeBusqueda<>();
        assertLista(vacio.getListaDatosNivel(0));
        assertLista(vacio.getListaDatosNivel(-1));

        ArbolBinarioDeBusqueda<Integer> arbol = crear(4, 2, 6, 1, 3, 5, 7);
        assertLista(arbol.getListaDatosNivel(0), 4);
        assertLista(arbol.getListaDatosNivel(1), 2, 6);
        assertLista(arbol.getListaDatosNivel(2), 1, 3, 5, 7);
        assertLista(arbol.getListaDatosNivel(3));
        assertLista(arbol.getListaDatosNivel(-2));
    }

    @Test
    void isArbolHomogeneo() {
        assertTrue(new ArbolBinarioDeBusqueda<Integer>().isArbolHomogeneo());
        assertTrue(crear(4).isArbolHomogeneo());
        assertTrue(crear(4, 2, 6).isArbolHomogeneo());
        assertFalse(crear(4, 6).isArbolHomogeneo());
        assertFalse(crear(4, 2).isArbolHomogeneo());
    }

    @Test
    void isArbolCompleto() {
        assertTrue(new ArbolBinarioDeBusqueda<Integer>().isArbolCompleto());
        assertTrue(crear(4).isArbolCompleto());
        assertTrue(crear(4, 2, 6).isArbolCompleto());
        assertTrue(crear(4, 2, 6, 1, 3, 5, 7).isArbolCompleto());
        assertFalse(crear(4, 2).isArbolCompleto());
        assertFalse(crear(4, 6, 7).isArbolCompleto());
        assertFalse(crear(4, 2, 6, 1, 3).isArbolCompleto());
    }

    @Test
    void isArbolCasiCompleto() {
        assertTrue(new ArbolBinarioDeBusqueda<Integer>().isArbolCasiCompleto());
        assertTrue(crear(4).isArbolCasiCompleto());
        assertTrue(crear(4, 2, 6, 1, 3, 5, 7).isArbolCasiCompleto());
        assertTrue(crear(4, 2, 6, 1, 3).isArbolCasiCompleto());
        assertTrue(crear(4, 2).isArbolCasiCompleto());
        assertFalse(crear(4, 2, 6, 3).isArbolCasiCompleto());
        assertFalse(crear(4, 6).isArbolCasiCompleto());
        assertFalse(crear(10, 5, 15, 3, 1).isArbolCasiCompleto());
    }

    @Test
    void getCamino() {
        ArbolBinarioDeBusqueda<Integer> vacio = new ArbolBinarioDeBusqueda<>();
        assertLista(vacio.getCamino(1));

        ArbolBinarioDeBusqueda<Integer> arbol = crear(4, 2, 6, 1, 3, 5, 7);
        assertLista(arbol.getCamino(5), 4, 6, 5);
        assertLista(arbol.getCamino(4), 4);
        assertLista(arbol.getCamino(8));
        assertThrows(IllegalArgumentException.class, () -> arbol.getCamino(null));
    }

    @Test
    void getSubArbolIzquierda() {
        ArbolBinarioDeBusqueda<Integer> vacio = new ArbolBinarioDeBusqueda<>();
        assertNotNull(vacio.getSubArbolIzquierda());
        assertLista(vacio.getSubArbolIzquierda().getListaOrdenCentral());

        ArbolBinarioDeBusqueda<Integer> arbol = crear(4, 2, 6, 1, 3, 5, 7);
        ArbolBinarioDeBusqueda<Integer> izquierda = arbol.getSubArbolIzquierda();
        assertLista(izquierda.getListaOrdenCentral(), 1, 2, 3);
        assertEquals(1, izquierda.getAltura());

        izquierda.add(0);
        assertLista(izquierda.getListaOrdenCentral(), 0, 1, 2, 3);
        assertLista(arbol.getListaOrdenCentral(), 1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void getSubArbolDerecha() {
        ArbolBinarioDeBusqueda<Integer> vacio = new ArbolBinarioDeBusqueda<>();
        assertNotNull(vacio.getSubArbolDerecha());
        assertLista(vacio.getSubArbolDerecha().getListaOrdenCentral());

        ArbolBinarioDeBusqueda<Integer> arbol = crear(4, 2, 6, 1, 3, 5, 7);
        ArbolBinarioDeBusqueda<Integer> derecha = arbol.getSubArbolDerecha();
        assertLista(derecha.getListaOrdenCentral(), 5, 6, 7);
        assertEquals(1, derecha.getAltura());

        derecha.add(8);
        assertLista(derecha.getListaOrdenCentral(), 5, 6, 7, 8);
        assertLista(arbol.getListaOrdenCentral(), 1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void getListaPreOrden() {
        assertLista(new ArbolBinarioDeBusqueda<Integer>().getListaPreOrden());
        assertLista(crear(4, 2, 6, 1, 3, 5, 7).getListaPreOrden(), 4, 2, 1, 3, 6, 5, 7);
    }

    @Test
    void getListaPostOrden() {
        assertLista(new ArbolBinarioDeBusqueda<Integer>().getListaPostOrden());
        assertLista(crear(4, 2, 6, 1, 3, 5, 7).getListaPostOrden(), 1, 3, 2, 5, 7, 6, 4);
    }

    @Test
    void getListaOrdenCentral() {
        assertLista(new ArbolBinarioDeBusqueda<Integer>().getListaOrdenCentral());
        assertLista(crear(4, 2, 6, 1, 3, 5, 7).getListaOrdenCentral(), 1, 2, 3, 4, 5, 6, 7);
    }

    @Test
    void crearArbolDesdeNodo() {
        ArbolExpuesto arbol = new ArbolExpuesto();
        arbol.add(4);
        arbol.add(2);
        arbol.add(6);

        ArbolBinarioDeBusqueda<Integer> copia = arbol.crearDesdeRaiz();
        assertLista(copia.getListaOrdenCentral(), 2, 4, 6);

        copia.add(1);
        assertLista(copia.getListaOrdenCentral(), 1, 2, 4, 6);
        assertLista(arbol.getListaOrdenCentral(), 2, 4, 6);

        assertLista(arbol.crearDesdeNull().getListaOrdenCentral());
    }

    @Test
    void copiar() {
        ArbolExpuesto arbol = new ArbolExpuesto();
        arbol.add(4);
        arbol.add(2);
        arbol.add(6);

        ArbolBinarioDeBusqueda.Nodo<Integer> copia = arbol.copiarRaiz();
        ArbolBinarioDeBusqueda<Integer> arbolCopia = arbol.crearArbolDesdeNodo(copia);
        assertLista(arbolCopia.getListaOrdenCentral(), 2, 4, 6);

        assertEquals(null, arbol.copiarNull());
    }

    private static ArbolBinarioDeBusqueda<Integer> crear(int... datos) {
        ArbolBinarioDeBusqueda<Integer> arbol = new ArbolBinarioDeBusqueda<>();
        for (int dato : datos) {
            arbol.add(dato);
        }
        return arbol;
    }

    private static void assertLista(Lista<Integer> lista, int... esperados) {
        assertNotNull(lista);
        assertEquals(esperados.length, lista.size());
        for (int i = 0; i < esperados.length; i++) {
            assertEquals(esperados[i], lista.get(i));
        }
    }

    private static class ArbolExpuesto extends ArbolBinarioDeBusqueda<Integer> {

        private ArbolBinarioDeBusqueda<Integer> crearDesdeRaiz() {
            return crearArbolDesdeNodo(raiz);
        }

        private ArbolBinarioDeBusqueda<Integer> crearDesdeNull() {
            return crearArbolDesdeNodo(null);
        }

        private Nodo<Integer> copiarRaiz() {
            return copiar(raiz);
        }

        private Nodo<Integer> copiarNull() {
            return copiar(null);
        }
    }
}
