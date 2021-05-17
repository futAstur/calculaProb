Programa que calcula las probabilidades que tiene un equipo de quedar en un determinado puesto tras una serie de partidos.

### Características
* Adaptado especialmente para la temporada 2020/21
* Calcula la clasificación final por coeficiente o por puntos
* No resuelve empates (muestra el porcentaje de posiciones que se decidirían por golaveraje)
* La estrategia inicial es generación aleatoria con 1.000.000 iteraciones
* Se puede usar la estrategia backtracking y calcular todas las posibilidades (pero sólo funciona para muy pocos partidos)

## Formato de Archivos
### clas.txt
Todas las líneas separadas por `\n` y cada una de la forma `Equipo;Victorias;Empates;Derrotas`
(Ahora puede sustituirse `;` por cualquier otro String si se configura en el código)
### grupos.txt
Cada línea es un equipo y todas van separadas por `\n`.
Para separar en dos grupos debe introducirse una línea entre los ambos con el siguiente carácter  `-` .
Los partidos que se generen enfrentarán a los equipos del primer grupo con los del segundo a ida y vuelta.
### partidos.txt
Cada línea es un partido y todas van separadas por `\n`.
Cada partido es de la forma `<equipoLocal>;<equipovisitante>`.

_Nota: Los nombres de los equipos deben coincidir en todos los archivos_

## Versiones
### 1.0.0: Versión Inicial
### 1.0.1
* Arreglado defecto a la hora de resolver qué equipos empataban en el golaveraje.
* Arreglado defecto en la asignación de posiciones siguientes a equipos cuando había empates.
* Ahora pueden subir clasificaciones separadas por cualquier String y no solo ";".
* Refactorización de algunos métodos.
* Otros cambios menores