package arboles;

/**
 * ABB de enteros con una operación extra para sumar sus datos.
 */
public class ArbolBinarioDeBusquedaEnteros extends ArbolBinarioDeBusqueda<Integer> {

    public ArbolBinarioDeBusquedaEnteros() {
        super();
    }

    protected ArbolBinarioDeBusquedaEnteros(Nodo<Integer> raiz) {
        super(raiz);
    }

    // Suma todo el árbol; el caso vacío cae naturalmente en 0.
    public int getSuma() {
        return getSuma(raiz);
    }

    // Devuelve el mismo tipo para poder seguir llamando a getSuma().
    @Override
    public ArbolBinarioDeBusquedaEnteros getSubArbolIzquierda() {
        return new ArbolBinarioDeBusquedaEnteros(raiz == null ? null : raiz.izquierda);
    }

    // Igual para la rama derecha.
    @Override
    public ArbolBinarioDeBusquedaEnteros getSubArbolDerecha() {
        return new ArbolBinarioDeBusquedaEnteros(raiz == null ? null : raiz.derecha);
    }

    private int getSuma(Nodo<Integer> nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.dato + getSuma(nodo.izquierda) + getSuma(nodo.derecha);
    }
}
