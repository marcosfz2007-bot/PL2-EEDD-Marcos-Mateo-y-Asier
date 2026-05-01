# Práctica PL2b: Grafos

Esta práctica construye un grafo de conocimiento basado en una versión simplificada de RDF N-Triples usando archivos JSON.

Cada tripleta del JSON tiene tres partes:

```json
{
  "s": "sujeto",
  "p": "predicado",
  "o": "objeto"
}
```

En el programa se representa como una arista dirigida:

```text
sujeto --predicado--> objeto
```

Por ejemplo:

```text
persona:Albert Einstein --nace_en--> lugar:Ulm
```

## Estructura del proyecto

```text
PL2b-Grafos/
 ├── src/
 │    ├── Main.java
 │    ├── modelo/
 │    │    ├── DatosGrafo.java
 │    │    ├── Tripleta.java
 │    │    ├── Arista.java
 │    │    └── GrafoConocimiento.java
 │    ├── lectura/
 │    │    └── LectorJSON.java
 │    └── pruebas/
 │         └── ProgramaPruebaGrafos.java
 ├── datos/
 │    ├── grafo_conectado.json
 │    ├── grafo_disjunto.json
 │    └── nobel_fisicos.json
 ├── pom.xml
 ├── README.md
 └── RESPUESTAS.md
```

## Archivos de datos

### `grafo_conectado.json`

Contiene un grafo dirigido en el que, desde el primer nodo, se puede llegar al resto de nodos siguiendo la dirección de las aristas.

Se usa para comprobar un caso de grafo no disjunto y para probar el camino mínimo entre `entidad:A` y `entidad:E`.

### `grafo_disjunto.json`

Contiene un grafo dirigido dividido en dos partes:

```text
entidad:A -> entidad:B
entidad:C -> entidad:D
```

Desde `entidad:A` no se puede llegar a `entidad:C` ni a `entidad:D`, por lo que el programa lo detecta como grafo disjunto.

### `nobel_fisicos.json`

Contiene un pequeño grafo de conocimiento con personas, premios, áreas y lugares de nacimiento.

Se usa para responder a estas consultas:

- Qué físico premio Nobel nació en la misma ciudad que Einstein.
- Qué lugares de nacimiento tienen los premios Nobel.
- Qué tipos de nodos aparecen en el grafo.

## Decisiones tomadas

El grafo se ha implementado como grafo dirigido, porque las tripletas RDF tienen dirección.

Todas las aristas tienen peso `1.0`, ya que el formato JSON de la práctica no incluye pesos. De esta forma, el camino mínimo es el camino que utiliza el menor número de relaciones.

Para calcular caminos mínimos se utiliza Dijkstra.

Para comprobar si el grafo es disjunto, se toma como nodo inicial el primer nodo cargado desde el JSON. Después se ejecuta Dijkstra desde ese nodo. Si existe algún nodo al que no se puede llegar, el grafo se considera disjunto.

## Ejecución

El proyecto está preparado con Maven y usa la librería Gson para leer los archivos JSON.

Para compilar:

```bash
mvn clean package
```

Para ejecutar el `.jar` generado:

```bash
java -jar target/pl2b-grafos-1.0-SNAPSHOT.jar
```

También se puede ejecutar directamente la clase `Main` desde IntelliJ.

Es importante que el directorio de trabajo sea la carpeta raíz del proyecto, es decir, la carpeta donde están `src`, `datos` y `pom.xml`. Si no, el programa no encontrará los archivos JSON de la carpeta `datos`.

## Funcionamiento general

El programa ejecuta directamente las pruebas desde `ProgramaPruebaGrafos`:

1. Carga `grafo_conectado.json`.
2. Comprueba si el grafo conectado es disjunto.
3. Calcula el camino mínimo dirigido entre `entidad:A` y `entidad:E`.
4. Carga `grafo_disjunto.json`.
5. Comprueba si el grafo disjunto se detecta correctamente.
6. Carga `nobel_fisicos.json`.
7. Busca los físicos premio Nobel nacidos en la misma ciudad que Einstein.
8. Añade la tripleta de Antonio indicada en el enunciado.
9. Lista los lugares de nacimiento de los premios Nobel.
10. Muestra los caminos utilizados para obtener esa respuesta.
11. Muestra los tipos de nodos que aparecen en el grafo.

