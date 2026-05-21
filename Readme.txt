----Juego Uno (java)----
Descripción:
Este proyecto implementa una versión con refactorización de clases. El diseño sigue principios de separación de responsabilidades y buenas prácticas de ingeniería de software, incluyenfo pruebas unitarias con JUnit 5 y documentación Javadoc.

Arquitectura:
El sistema está dividido en clases con responsabilidades claras:

Game - Controla el flujo principal del juego(Inicialización, turnos, interacción)

Player- Representa un jugador humano obot, maneja su mano y lógica de turno

Hand - Contiene las cartas de un jugador y operaciones sobre ellas

Card - Modela una carta (color, número, tipo)

Deck - Mazo de cartas, con lógica de creación, barajeado y robo

DiscardPile - Pila de descarte, mantiene la carta actual en mesa

TurnManager - Control de turnos, dirección y orden de jugadores

RuleEngine - Aplica reglas y efectos de cartas especiales (comodin, roba2, roba4, reversa y salto)

Main - punto de entrada del programa

Funcionalidades:
Juego básico de Uno con jugadores humano y bots
Cartas númericas y especiales (salto, reversa, roba2, roba4 y comodín)
Control de turnos y dirección
Validación de jugadas según reglas de uno
interacción por consola para jugadores humanos
bots con lógica automática de decisión

Pruebas:
Se implementarón pruebas unitarias con JUnit 5, cubriendo:

Reglas especiales: comodín, Roba2, Roba4, Reversa y Salto

Turnos: Jugador humano roba carta, bot juega carta valida

Errores: Carta null, índice invalido en mano, mazo vacio

Validaciones adicionales: Jugadas validas según color, numero y tipo

plan de pruebas:
Caja negra: Validación de reglas y turnos

Caja blanca: Rutas internas y condiciones límite

Cobertura: Métodos públicos de clases principales

Decisión: Bifurcaciones críticas (jugada válida vs. invalida, UNO declarado vs. no declarado)


Bugs encontrados:

-Bloqueo en pruebas por entrada de nombre -> constructor de pruebas en game
-Método avanzar() inexistente -> implemetado después de TurnManager
-Mensajes incorrectos en SALTO -> ahora muestra al jugador saltado
-Jugador humano roba carta sin mensaje -> ahora muestra que jugador robo carta
