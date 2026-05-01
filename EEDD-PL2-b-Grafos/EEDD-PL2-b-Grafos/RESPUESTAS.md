# RESPUESTAS

## 1. ¿Cuál es el camino mínimo entre dos entidades A y B del grafo?

Para calcular el camino mínimo se ha usado el algoritmo de Dijkstra.

El grafo se trata como un grafo dirigido. Esto significa que si existe una tripleta:

```text
A --relacion--> B
```

se puede ir desde `A` hasta `B`, pero no desde `B` hasta `A`, salvo que exista otra tripleta en sentido contrario.

Como el formato de la práctica no incluye pesos en las tripletas, se ha decidido que todas las aristas tengan peso `1.0`. Por tanto, el camino mínimo será el camino que usa el menor número de aristas o relaciones.

En el archivo `grafo_conectado.json`, por ejemplo, se calcula el camino mínimo dirigido entre:

```text
entidad:A
entidad:E
```

Las relaciones principales son:

```text
entidad:A --relacion--> entidad:B
entidad:B --relacion--> entidad:C
entidad:C --relacion--> entidad:D
entidad:D --relacion--> entidad:E
entidad:A --atajo--> entidad:C
```

El camino mínimo encontrado es:

```text
entidad:A -> entidad:C -> entidad:D -> entidad:E
```

La distancia total es `3.0`, porque se recorren tres aristas.

## 2. Dado un archivo de datos que se carga en el grafo, ¿genera un grafo disjunto?

Para esta práctica se ha considerado que un grafo es disjunto si, siguiendo solamente la dirección de las aristas, hay nodos a los que no se puede llegar desde el nodo inicial.

El nodo inicial utilizado es el primer nodo que se carga desde el JSON.

Para comprobarlo, el programa ejecuta Dijkstra desde ese nodo inicial. Si algún nodo queda con distancia infinita, significa que no es alcanzable desde el nodo inicial y, por tanto, el grafo se considera disjunto.

Se han creado dos archivos de prueba.

### Archivo `grafo_conectado.json`

Este archivo contiene un grafo donde, desde `entidad:A`, se puede llegar al resto de nodos siguiendo la dirección de las aristas:

```text
entidad:A -> entidad:B -> entidad:C -> entidad:D -> entidad:E
```

Además, existe una relación directa:

```text
entidad:A -> entidad:C
```

El resultado esperado es:

```text
¿El grafo es disjunto?: false
```

### Archivo `grafo_disjunto.json`

Este archivo contiene dos partes separadas:

```text
entidad:A -> entidad:B
entidad:C -> entidad:D
```

Desde `entidad:A` no se puede llegar a `entidad:C` ni a `entidad:D` siguiendo la dirección de las aristas.

El resultado esperado es:

```text
¿El grafo es disjunto?: true
```

## 3. ¿Qué físico famoso nació en la misma ciudad que Einstein?

Para poder responder a esta pregunta se ha creado el archivo `nobel_fisicos.json`.

La idea seguida es buscar primero el lugar de nacimiento de Einstein:

```text
persona:Albert Einstein --nace_en--> lugar:Ulm
```

Después el programa recorre los nodos de tipo `persona` y comprueba tres condiciones:

1. Que la persona no sea Albert Einstein.
2. Que tenga área de Física:

```text
persona:X --area--> area:Fisica
```

3. Que tenga premio Nobel:

```text
persona:X --premio--> premio:Nobel
```

4. Que haya nacido en el mismo lugar que Einstein:

```text
persona:X --nace_en--> lugar:Ulm
```

En el fichero de datos se ha incluido esta persona:

```text
persona:Fisico Famoso de Ulm
```

con las siguientes relaciones:

```text
persona:Fisico Famoso de Ulm --premio--> premio:Nobel
persona:Fisico Famoso de Ulm --area--> area:Fisica
persona:Fisico Famoso de Ulm --nace_en--> lugar:Ulm
```

Por tanto, la respuesta obtenida por el programa es:

```text
persona:Fisico Famoso de Ulm
```

## 4. Añadir la tripleta de Antonio y listar los lugares de nacimiento de los premios Nobel

El programa añade al grafo la tripleta indicada en el enunciado:

```text
<"persona:Antonio", "nace_en", "lugar:Villarrubia de los Caballeros">
```

Esta tripleta indica solamente el lugar de nacimiento de Antonio. No indica que Antonio tenga un premio Nobel.

Por eso, Antonio no debe aparecer al listar los lugares de nacimiento de los premios Nobel.

Para obtener la respuesta correcta, el programa no puede listar todos los lugares asociados a relaciones `nace_en`. Primero debe comprobar qué personas son premios Nobel y después consultar su lugar de nacimiento.

Los caminos que necesita recorrer son:

```text
persona:X --premio--> premio:Nobel
persona:X --nace_en--> lugar:Y
```

Es decir:

1. Buscar nodos de tipo `persona`.
2. Comprobar si tienen la relación `premio` hacia `premio:Nobel`.
3. Si la tienen, buscar su relación `nace_en`.
4. Añadir ese lugar a la lista de resultados.

Con el archivo `nobel_fisicos.json`, los premios Nobel incluidos son:

```text
persona:Albert Einstein
persona:Fisico Famoso de Ulm
persona:Marie Curie
persona:Gabriel Garcia Marquez
```

Sus lugares de nacimiento son:

```text
lugar:Ulm
lugar:Varsovia
lugar:Aracataca
```

`lugar:Ulm` aparece una sola vez en la lista final aunque haya más de una persona nacida allí, porque el programa evita repetir lugares.

## 5. ¿Qué tipos de nodos tiene el grafo?

El tipo de un nodo se obtiene mirando la parte anterior a los dos puntos.

Por ejemplo:

```text
persona:Albert Einstein -> persona
lugar:Ulm -> lugar
premio:Nobel -> premio
area:Fisica -> area
```

Cuando un nodo no contiene dos puntos, se considera un valor literal. Por ejemplo:

```text
1921 -> literal
```

En el grafo de Nobel aparecen estos tipos de nodos:

```text
persona
premio
area
lugar
literal
```

En el JSON también existe una lista de tipos declarados. Esa lista sirve para indicar qué tipos se esperan en el archivo, pero el programa obtiene los tipos reales recorriendo los nodos que aparecen en las tripletas.

## 6. ¿Qué es una ontología? ¿Qué relación tiene con los grafos? ¿Podríamos crear una ontología para nuestro problema? ¿Qué haríamos con ella?

Una ontología es una forma de definir los conceptos importantes de un dominio y las relaciones que pueden existir entre ellos.

En este problema, el dominio es un pequeño grafo de conocimiento sobre personas, premios Nobel, áreas y lugares de nacimiento.

Una ontología podría definir clases como:

```text
Persona
Premio
Lugar
Area
```

También podría definir relaciones como:

```text
nace_en
premio
area
anio_premio
```

La relación con los grafos es que una ontología se puede representar mediante nodos y aristas. Los nodos representan entidades o conceptos, y las aristas representan relaciones entre ellos.

Sí podríamos crear una ontología para este problema. Con ella podríamos indicar, por ejemplo:

```text
Una Persona puede nacer en un Lugar.
Una Persona puede ganar un Premio.
Una Persona puede pertenecer a un Area.
Un Premio puede tener un año asociado.
```

Esto ayudaría a que el grafo tuviera una estructura más clara y a evitar datos mal formados. Por ejemplo, permitiría comprobar que una relación `nace_en` une una persona con un lugar, y no una persona con un premio.
