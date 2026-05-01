package arboles;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListaTest {

    @Test
    void add() {
        Lista<Integer> lista = new Lista<>();

        lista.add(10);
        lista.add(20);
        lista.add(30);

        assertEquals(3, lista.size());
        assertEquals(10, lista.get(0));
        assertEquals(20, lista.get(1));
        assertEquals(30, lista.get(2));
    }

    @Test
    void get() {
        Lista<String> lista = new Lista<>();
        lista.add("a");
        lista.add("b");

        assertEquals("a", lista.get(0));
        assertEquals("b", lista.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.get(2));
    }

    @Test
    void estaVacia() {
        Lista<Integer> lista = new Lista<>();

        assertTrue(lista.estaVacia());
        lista.add(1);
        assertFalse(lista.estaVacia());
    }

    @Test
    void size() {
        Lista<Integer> lista = new Lista<>();

        assertEquals(0, lista.size());
        lista.add(1);
        assertEquals(1, lista.size());
        lista.add(2);
        assertEquals(2, lista.size());
    }

    @Test
    void iterator() {
        Lista<Integer> lista = new Lista<>();
        lista.add(1);
        lista.add(2);

        Iterator<Integer> iterator = lista.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testToString() {
        Lista<Integer> lista = new Lista<>();

        assertEquals("[]", lista.toString());
        lista.add(1);
        lista.add(2);
        assertEquals("[1, 2]", lista.toString());
    }
}
