package arboles;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Árbol binario de búsqueda genérico.
 */
public class ArbolBinarioDeBusqueda<T extends Comparable<T>> {

    protected static class Nodo<T> {
        protected final T dato;
        protected Nodo<T> izquierda;
        protected Nodo<T> derecha;

        protected Nodo(T dato) {
            this.dato = dato;
        }
    }

    protected Nodo<T> raiz;

    public ArbolBinarioDeBusqueda() {
        raiz = null;
    }

    protected ArbolBinarioDeBusqueda(Nodo<T> raiz) {
        this.raiz = copiar(raiz);
    }

    // Inserta respetando el orden del ABB y sin aceptar null.
    public void add(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede insertar un dato null en el arbol");
        }
        raiz = addRecursivo(raiz, dato);
    }

    // Grado del árbol: el máximo número de hijos encontrado.
    public int getGrado() {
        return getGrado(raiz);
    }

    // Altura en aristas; por eso el árbol vacío vale -1.
    public int getAltura() {
        return getAltura(raiz);
    }

    // Devuelve solo los datos que están exactamente en ese nivel.
    public Lista<T> getListaDatosNivel(int nivel) {
        Lista<T> datos = new Lista<>();
        if (nivel < 0) {
            return datos;
        }
        llenarDatosNivel(raiz, nivel, 0, datos);
        return datos;
    }

    // Homogéneo: ningún nodo tiene un único hijo.
    public boolean isArbolHomogeneo() {
        return isHomogeneo(raiz);
    }

    // Completo según el enunciado: hojas al mismo nivel y sin nodos a medias.
    public boolean isArbolCompleto() {
        return isHomogeneo(raiz) && profundidadHojaDistinta(raiz, primeraProfundidadHoja(raiz), 0) == -1;
    }

    // Casi completo: recorrido por niveles y huecos controlados.
    public boolean isArbolCasiCompleto() {
        if (raiz == null) {
            return true;
        }

        Queue<NodoConNivel<T>> cola = new ArrayDeque<>();
        cola.add(new NodoConNivel<>(raiz, 0));
        boolean encontradoHueco = false;
        int menorNivelHoja = Integer.MAX_VALUE;
        int mayorNivelHoja = Integer.MIN_VALUE;

        while (!cola.isEmpty()) {
            NodoConNivel<T> actual = cola.remove();
            Nodo<T> nodo = actual.nodo;
            int nivel = actual.nivel;

            if (nodo.izquierda == null && nodo.derecha == null) {
                menorNivelHoja = Math.min(menorNivelHoja, nivel);
                mayorNivelHoja = Math.max(mayorNivelHoja, nivel);
                if (mayorNivelHoja - menorNivelHoja > 1) {
                    return false;
                }
            }

            // En un casi completo, después del primer hueco ya no pueden aparecer más hijos.
            if (nodo.izquierda != null) {
                if (encontradoHueco) {
                    return false;
                }
                cola.add(new NodoConNivel<>(nodo.izquierda, nivel + 1));
            } else {
                encontradoHueco = true;
            }

            if (nodo.derecha != null) {
                if (encontradoHueco) {
                    return false;
                }
                cola.add(new NodoConNivel<>(nodo.derecha, nivel + 1));
            } else {
                encontradoHueco = true;
            }
        }

        return true;
    }

    // Si el dato no aparece, se devuelve lista vacía en vez de un camino parcial.
    public Lista<T> getCamino(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("No se puede buscar un dato null en el arbol");
        }

        Lista<T> camino = new Lista<>();
        if (llenarCamino(raiz, dato, camino)) {
            return camino;
        }
        return new Lista<>();
    }

    // Se copia el subárbol para no tocar el árbol original desde fuera.
    public ArbolBinarioDeBusqueda<T> getSubArbolIzquierda() {
        return crearArbolDesdeNodo(raiz == null ? null : raiz.izquierda);
    }

    // Igual que el izquierdo, pero con la rama derecha.
    public ArbolBinarioDeBusqueda<T> getSubArbolDerecha() {
        return crearArbolDesdeNodo(raiz == null ? null : raiz.derecha);
    }

    // Raíz, izquierda, derecha.
    public Lista<T> getListaPreOrden() {
        Lista<T> datos = new Lista<>();
        preOrden(raiz, datos);
        return datos;
    }

    // Izquierda, derecha, raíz.
    public Lista<T> getListaPostOrden() {
        Lista<T> datos = new Lista<>();
        postOrden(raiz, datos);
        return datos;
    }

    // Izquierda, raíz, derecha. En un ABB sale ordenado.
    public Lista<T> getListaOrdenCentral() {
        Lista<T> datos = new Lista<>();
        ordenCentral(raiz, datos);
        return datos;
    }

    // La subclase de enteros lo aprovecha para devolver subárboles con suma.
    protected ArbolBinarioDeBusqueda<T> crearArbolDesdeNodo(Nodo<T> nodo) {
        return new ArbolBinarioDeBusqueda<>(nodo);
    }

    private Nodo<T> addRecursivo(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return new Nodo<>(dato);
        }

        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion < 0) {
            nodo.izquierda = addRecursivo(nodo.izquierda, dato);
        } else if (comparacion > 0) {
            nodo.derecha = addRecursivo(nodo.derecha, dato);
        }
        // Si compareTo da 0, el dato ya estaba y se ignora.
        return nodo;
    }

    protected Nodo<T> copiar(Nodo<T> nodo) {
        if (nodo == null) {
            return null;
        }
        Nodo<T> copia = new Nodo<>(nodo.dato);
        copia.izquierda = copiar(nodo.izquierda);
        copia.derecha = copiar(nodo.derecha);
        return copia;
    }

    private int getAltura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(getAltura(nodo.izquierda), getAltura(nodo.derecha));
    }

    private int getGrado(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int hijos = 0;
        if (nodo.izquierda != null) {
            hijos++;
        }
        if (nodo.derecha != null) {
            hijos++;
        }
        return Math.max(hijos, Math.max(getGrado(nodo.izquierda), getGrado(nodo.derecha)));
    }

    private void llenarDatosNivel(Nodo<T> nodo, int nivelBuscado, int nivelActual, Lista<T> datos) {
        if (nodo == null) {
            return;
        }
        if (nivelActual == nivelBuscado) {
            datos.add(nodo.dato);
            return;
        }
        llenarDatosNivel(nodo.izquierda, nivelBuscado, nivelActual + 1, datos);
        llenarDatosNivel(nodo.derecha, nivelBuscado, nivelActual + 1, datos);
    }

    private boolean isHomogeneo(Nodo<T> nodo) {
        if (nodo == null) {
            return true;
        }
        boolean tieneIzquierda = nodo.izquierda != null;
        boolean tieneDerecha = nodo.derecha != null;
        if (tieneIzquierda != tieneDerecha) {
            return false;
        }
        return isHomogeneo(nodo.izquierda) && isHomogeneo(nodo.derecha);
    }

    private int profundidadHojaDistinta(Nodo<T> nodo, int profundidadEsperada, int profundidadActual) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.izquierda == null && nodo.derecha == null) {
            return profundidadEsperada == profundidadActual ? -1 : profundidadActual;
        }

        int izquierda = profundidadHojaDistinta(nodo.izquierda, profundidadEsperada, profundidadActual + 1);
        if (izquierda != -1) {
            return izquierda;
        }
        return profundidadHojaDistinta(nodo.derecha, profundidadEsperada, profundidadActual + 1);
    }

    private int primeraProfundidadHoja(Nodo<T> nodo) {
        int profundidad = 0;
        Nodo<T> actual = nodo;
        while (actual != null && (actual.izquierda != null || actual.derecha != null)) {
            actual = actual.izquierda != null ? actual.izquierda : actual.derecha;
            profundidad++;
        }
        return profundidad;
    }

    private boolean llenarCamino(Nodo<T> nodo, T dato, Lista<T> camino) {
        if (nodo == null) {
            return false;
        }

        camino.add(nodo.dato);
        int comparacion = dato.compareTo(nodo.dato);
        if (comparacion == 0) {
            return true;
        }
        if (comparacion < 0) {
            return llenarCamino(nodo.izquierda, dato, camino);
        }
        return llenarCamino(nodo.derecha, dato, camino);
    }

    private void preOrden(Nodo<T> nodo, Lista<T> datos) {
        if (nodo == null) {
            return;
        }
        datos.add(nodo.dato);
        preOrden(nodo.izquierda, datos);
        preOrden(nodo.derecha, datos);
    }

    private void postOrden(Nodo<T> nodo, Lista<T> datos) {
        if (nodo == null) {
            return;
        }
        postOrden(nodo.izquierda, datos);
        postOrden(nodo.derecha, datos);
        datos.add(nodo.dato);
    }

    private void ordenCentral(Nodo<T> nodo, Lista<T> datos) {
        if (nodo == null) {
            return;
        }
        ordenCentral(nodo.izquierda, datos);
        datos.add(nodo.dato);
        ordenCentral(nodo.derecha, datos);
    }

    private static class NodoConNivel<T> {
        private final Nodo<T> nodo;
        private final int nivel;

        private NodoConNivel(Nodo<T> nodo, int nivel) {
            this.nodo = nodo;
            this.nivel = nivel;
        }
    }
}
