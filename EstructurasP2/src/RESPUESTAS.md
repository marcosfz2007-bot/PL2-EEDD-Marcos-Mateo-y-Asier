# RESPUESTAS

Este documento contiene las respuestas a las preguntas conceptuales del enunciado, así como una explicación de cómo el código resuelve cada una de las consultas pedidas.

---

## 1. ¿Cuál es el camino mínimo entre dos entidades A y B del grafo?

El camino mínimo entre dos nodos es la secuencia de aristas **más corta** que los conecta, es decir, la que pasa por menos nodos intermedios.

En nuestro código se implementa con el algoritmo **BFS (Breadth-First Search, búsqueda en anchura)** dentro del método `Grafo.caminoMinimo(origen, destino)`:

1. Empezamos en el nodo origen y lo metemos en una cola.
2. Vamos sacando nodos de la cola, y para cada uno exploramos sus vecinos.
3. La primera vez que llegamos al destino, ese camino tiene el menor número de saltos posible (porque BFS expande nivel a nivel).
4. Reconstruimos el camino "hacia atrás" usando un mapa `previo` que recuerda desde qué nodo hemos llegado a cada otro nodo.

Tratamos el grafo como **no dirigido** para el cálculo del camino, porque las relaciones RDF como `nace_en` no se pueden navegar al revés de forma natural si no hacemos esto, y queremos poder unir entidades aunque la flecha vaya en el otro sentido.

---

## 2. ¿Cuándo un grafo es disjunto? Ejemplos

Un grafo es **disjunto** (o no conexo) cuando está formado por **varias componentes separadas**: existen al menos dos nodos entre los que no hay ningún camino posible.

Para comprobarlo, partimos de un nodo cualquiera y hacemos una BFS. Si al terminar hemos visitado **menos** nodos de los que hay en total, hay islas a las que no hemos podido llegar, así que el grafo es disjunto.

Hemos creado dos archivos de datos para ilustrar las dos opciones:

### `datos/grafo_conexo.json` (NO disjunto)
Ana, Luis, María y Carlos están conectados por una cadena de relaciones `conoce`. Además, Ana y Carlos están conectados con `lugar:Madrid`. Cualquier nodo se puede alcanzar desde cualquier otro siguiendo aristas → **NO es disjunto** (`esDisjunto()` devuelve `false`).

### `datos/grafo_disjunto.json` (SÍ disjunto)
Tenemos tres "islas" totalmente separadas:
- `{Ana, Luis}`
- `{María, Carlos}`
- `{Pedro, Sevilla}`

No hay ninguna manera de ir, por ejemplo, de Ana a Carlos → **SÍ es disjunto** (`esDisjunto()` devuelve `true`).

El método `Main` del programa carga ambos archivos y muestra el resultado por consola para que se pueda comprobar.

---

## 3. ¿Qué físico famoso nació en la misma ciudad que Einstein?

Para responder a esta pregunta sobre un grafo de premios Nobel necesitamos **dos pasos**:

1. **Encontrar la ciudad de nacimiento de Einstein.** Buscamos la tripleta:
   `<persona:Einstein, nace_en, ?ciudad>`. Eso nos da `lugar:Ulm`.

2. **Buscar a otra persona que cumpla las dos condiciones**:
    - tenga la tripleta `<?p, nace_en, lugar:Ulm>`
    - tenga la tripleta `<?p, es_un, fisico>`

En nuestro archivo `datos/nobel.json` hemos puesto a `persona:Hans_Mueller` como físico nacido en Ulm, así que la respuesta del programa es:

```
¿Qué físico nació en la misma ciudad que Einstein?
  persona:Hans_Mueller
```

El método `Grafo.fisicosNacidosMismaCiudadQue("persona:Einstein")` realiza exactamente este razonamiento.

> Nota: en un grafo real (por ejemplo, sobre Wikidata) la respuesta podría ser distinta. Aquí lo que importa es **cómo se construye la consulta**: combinar dos relaciones (`nace_en` y `es_un`) sobre el mismo nodo intermedio (la ciudad).

---

## 4. Tras añadir `<persona:Antonio, nace_en, lugar:Villarrubia de los Caballeros>`, ¿cuáles son los lugares de nacimiento de los premios Nobel?

Antonio **NO** debe aparecer en la lista, porque NO es un premio Nobel.

El método `lugaresNacimientoPremiosNobel()` recorre el grafo de la siguiente forma:

1. **Primer recorrido**: identifica todas las personas que tienen una arista `gano_premio` cuyo destino empieza por `premio:Nobel`. Esas son las personas Nobel.
2. **Segundo recorrido**: para cada una de ellas, sigue la arista `nace_en` para obtener su ciudad de nacimiento.

Es decir, se recorren **dos tipos de aristas distintos** (`gano_premio` y `nace_en`) y se hace una **intersección** lógica (solo personas que aparecen en ambos sentidos).

Si hubiéramos hecho la consulta "ingenuamente" (mostrar todos los `nace_en` del grafo) Antonio aparecería, pero eso sería incorrecto. Por eso el camino correcto es:

```
PARA CADA persona P del grafo:
    SI existe la arista (P, gano_premio, premio:Nobel_*)
    Y existe la arista (P, nace_en, ?lugar)
    ENTONCES devolver (P, ?lugar)
```

Esto es esencialmente una *consulta SPARQL* expresada en código Java.

---

## 5. ¿Qué tipos de nodos tiene el grafo?

En nuestro modelo, el "tipo" de un nodo es el prefijo antes de los `:` en su nombre. Por ejemplo:

- `persona:Einstein` → tipo **persona**
- `lugar:Ulm` → tipo **lugar**
- `premio:Nobel_Fisica` → tipo **premio**
- `fisico`, `quimico`, `biologo` → tipo **literal** (no tienen prefijo)

El método `Grafo.tiposNodos()` devuelve un conjunto con todos los tipos diferentes que aparecen, recorriendo todos los nodos cargados en el grafo y extrayendo su prefijo.

Para el archivo `datos/nobel.json` (más la tripleta de Antonio) los tipos resultantes son: `literal`, `lugar`, `persona`, `premio`.

---

## 6. ¿Qué es una ontología? ¿Qué relación tiene con los grafos? ¿Podríamos crear una para nuestro problema?

### ¿Qué es una ontología?

Una **ontología** es un esquema formal que define:
- **Las clases o tipos de cosas** que existen en un dominio (Persona, Lugar, Premio…).
- **Las propiedades o relaciones** que esas cosas pueden tener entre sí (`nace_en`, `gano_premio`, `es_un`…).
- **Las restricciones y reglas** que esas relaciones deben cumplir (por ejemplo: el dominio de `nace_en` es Persona, su rango es Lugar; toda persona tiene como mucho un único lugar de nacimiento; un Premio Nobel pertenece a una categoría como Física, Química, etc.).

En la práctica, una ontología se suele escribir en lenguajes como **RDF Schema (RDFS)** o, con más expresividad, **OWL (Web Ontology Language)**.

### Relación con los grafos

Un grafo de conocimiento son **los datos concretos** (las tripletas individuales: "Einstein nació en Ulm"), mientras que una ontología es **el modelo abstracto** que dice qué pinta deben tener esos datos.

La metáfora típica:
- **Ontología = clases en un programa** (define qué es una Persona, qué propiedades tiene…).
- **Grafo de conocimiento = instancias** (Einstein, Curie, Madrid…).

Cuando un grafo "respeta" una ontología, podemos hacer **razonamiento automático**: deducir tripletas que no están escritas explícitamente. Por ejemplo, si la ontología dice "todo físico es un científico" y el grafo dice "Einstein es un físico", se deduce automáticamente "Einstein es un científico".

### ¿Podríamos crear una ontología para nuestro problema?

Sí, perfectamente. Una ontología sencilla para nuestro caso podría definir:

- **Clases**: `Persona`, `Lugar`, `Premio`, `Profesion` (con subclases `Fisico`, `Quimico`, `Biologo`…).
- **Propiedades**:
    - `nace_en` con dominio `Persona` y rango `Lugar`.
    - `gano_premio` con dominio `Persona` y rango `Premio`.
    - `es_un` con dominio `Persona` y rango `Profesion`.
    - `esta_en` con dominio `Lugar` y rango `Lugar`.
- **Jerarquías**: `Fisico ⊑ Cientifico ⊑ Profesion`.
- **Restricciones**: cada persona tiene como máximo un valor de `nace_en` (cardinalidad 1).

### ¿Qué haríamos con ella?

1. **Validar los datos** que cargamos (rechazar tripletas que no respetan los tipos).
2. **Inferir automáticamente** nuevas tripletas (si Einstein es físico, también es científico).
3. **Hacer consultas más inteligentes**: "dame todos los científicos nacidos en Alemania" funcionaría aunque en los datos solo se diga que Einstein es físico, porque la ontología enseña al sistema que "físico es un tipo de científico". Además, podríamos transitar la relación `esta_en` para cubrir tanto ciudades como países.
4. **Compartir el modelo** entre distintas aplicaciones, garantizando que todos entiendan el vocabulario igual.

En resumen: el **grafo** son los datos, y la **ontología** es la "gramática" que les da sentido y permite razonar sobre ellos.