/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controla el flujo del juego Uno basico
 * Version semanas 1-2 sin cartas especiales
 * jugador vs computadora
 */
public class game {

    private deck deck;
    private ArrayList<player> jugadores;
    private TurnManager turnManager;
    private RuleEngine ruleEngine;
    private DiscardPile pila;
    private String mensaje;
    /** Constructor interactivo */
    public game(String nombre) {
        deck = new deck();
        jugadores = new ArrayList<>();
        jugadores.add(new player(nombre, true));
        jugadores.add(new player("Mari", false));
        jugadores.add(new player("Pepe", false));
        jugadores.add(new player("Toña", false));
        turnManager = new TurnManager(jugadores.size());
        ruleEngine = new RuleEngine();
        pila = new DiscardPile();
        mensaje = "";
    }
    /** Constructor para pruebas */
    public game(boolean modoTest) {
        deck = new deck();
        jugadores = new ArrayList<>();
        jugadores.add(new player("TestHumano", true));
        jugadores.add(new player("TestBot", false));
        turnManager = new TurnManager(jugadores.size());
        ruleEngine = new RuleEngine();
        pila = new DiscardPile();
        mensaje = "";
    }
    /** @return Mazo actual */
    public deck getDeck() {
        return deck;
    }
    /** Inicia el juego completo */
    public void iniciar() {
        repartirCartas();
        card inicial;
        do {
            inicial = deck.robarCarta();
            if (inicial == null) {
                mensaje = "No hay cartas para iniciar";
                return;
            }
        } while (inicial.getTipo() != card.Tipo.NUMERO);
        pila.ponerCarta(inicial);
        mensaje = "Carta inicial: " + pila.getCartaActual();
    }
    /** Permite elegir color */
    public card.Color elegirColor(player jugador){
        // SI ES BOT
        if(!jugador.esHumano()){

            card.Color[] colores = {
                card.Color.ROJO,
                card.Color.AZUL,
                card.Color.VERDE,
                card.Color.AMARILLO
            };

            return colores[(int)(Math.random() * 4)];
        }
        return card.Color.ROJO;
    }
    /** Ejecuta un turno */
    public boolean turno() {
        player actual = jugadores.get(
                turnManager.getTurnoActual()
        );
        card jugada = actual.jugarTurno(
                this,
                pila.getCartaActual()
        );
        boolean cartaEspecial = false;
        if (jugada != null) {
            pila.ponerCarta(jugada);

            mensaje = actual.getNombre()
                    + " juega: "
                    + jugada;

            cartaEspecial =
                    (jugada.getTipo()
                    != card.Tipo.NUMERO);

            ruleEngine.aplicarEfecto(
                    jugada,
                    actual,
                    jugadores,
                    turnManager,
                    deck,
                    pila,
                    this
            );
        }
        if (actual.getMano().estaVacia()) {

            mensaje = "¡"
                    + actual.getNombre()
                    + " ha ganado!";

            return true;
        }

        if (jugada == null) {

            turnManager.siguienteTurno();

        } else if (!cartaEspecial) {

            turnManager.siguienteTurno();
        }

        return false;
    }
    /** Reparte cartas iniciales */
    private void repartirCartas() {

        for (int i = 0; i < 7; i++) {

            for (player p : jugadores) {

                p.getMano().agregarCarta(
                        deck.robarCarta()
                );
            }
        }
    }

    /**
     * Muestra el estado actual del juego
     * Este metodo puede utilizarse para imprimir
     * Información del juego como:
     * Turno actual
     * Cartas en mesa
     * Estado de jugadores
     */
    public void mostrarEstado() {
    }

    /**
     * Obtiene la lista de jugadores de la partida
     * @return Lista de jugadores registrados en el juego
     */
    public ArrayList<player> getJugadores() {
        return jugadores;
    }
    
    /**
     * Obtiene la pila de descarte del juego
     * @return Pila de cartas descartadas
     */
    public DiscardPile getPila() {
        return pila;
    }

    /**
     * Obtiene el administrados de turno
     * @return Controlador encargado de los turnos
     */
    public TurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * Obtiene el mensaje actual del juego
     * Generalmente se utiliza para mostrar eventos
     * Importantes en el log
     * @return Mensaje actual del juego
     */
    public String getMensaje() {
        return mensaje;
    }
    
    /**
     * Establece un nuevo mensaje del juego
     * @param mensaje Nuevo meensaje a almacenar
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    /**
     * Obtiene el motor de reglas del juego
     * Este componente se encarga de aplicar
     * efectos especiales y reglas del UNO
     * @return Motor de reglas del juego
     */
    public RuleEngine getRuleEngine(){
        return ruleEngine;
    }
}