package arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista enlazada pequeña para devolver recorridos y caminos del árbol.
 */
public class Lista<T> implements Iterable<T> {

    private static class Nodo<T> {
        private final T dato;
        private Nodo<T> siguiente;

        private Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int size;

    public Lista() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    // Añadir al final mantiene el orden de los recorridos del árbol.
    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
        size++;
    }

    // Acceso sencillo por índice; suficiente para los tests y programas.
    public T get(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }

        Nodo<T> actual = primero;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    // Nombre pedido por el estilo de la práctica.
    public boolean estaVacia() {
        return size == 0;
    }

    // Tamaño actual de la lista.
    public int size() {
        return size;
    }

    // Permite usar for-each al sumar recorridos.
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = primero;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No hay mas elementos en la lista");
                }
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }

    // Útil para imprimir caminos y recorridos en los programas.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        Nodo<T> actual = primero;
        while (actual != null) {
            builder.append(actual.dato);
            actual = actual.siguiente;
            if (actual != null) {
                builder.append(", ");
            }
        }
        builder.append(']');
        return builder.toString();
    }
}
