/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

import java.util.ArrayList;

/**
 * Aplica las reglas del juego Uno
 * Maneja efectos de cartas especiales (comodin, roba4, reversa)
 */
public class RuleEngine {

    /**
     * Aplica el efecto de una carta especial
     * @param carta carta jugada
     * @param jugadorActual Jugador que jugó la carta
     * @param jugadores Lista de jugadores
     * @param turnManager Control de turno
     * @param deck Mazo de cartas
     * @param pila Pila de descarte
     * @param game Instancia del juego
     * @return Número de pasos que debe avanzar el turno
     */
    public int aplicarEfecto(card carta,
                             player jugadorActual,
                             ArrayList<player> jugadores,
                             TurnManager turnManager,
                             deck deck,
                             DiscardPile pila,
                             game game) {

        if(carta == null) return 1;

        card.Tipo tipo = carta.getTipo();

        switch(tipo){

            case REVERSA:

                turnManager.reversa();

                game.setMensaje(
                        jugadorActual.getNombre()
                        + " cambia la dirección!"
                );

                turnManager.avanzar(1);

                return 1;

            case SALTO:

                int idxSalto = (
                        turnManager.getTurnoActual()
                        + turnManager.getDireccion()
                        + jugadores.size()
                ) % jugadores.size();

                player saltado = jugadores.get(idxSalto);

                game.setMensaje(
                        saltado.getNombre()
                        + " pierde su turno!"
                );

                turnManager.avanzar(2);

                return 2;

            case ROBA2:

                int idx2 = (
                        turnManager.getTurnoActual()
                        + turnManager.getDireccion()
                        + jugadores.size()
                ) % jugadores.size();

                player siguiente = jugadores.get(idx2);

                for(int i = 0; i < 2; i++){

                    siguiente.getMano().agregarCarta(
                            deck.robarCarta()
                    );
                }

                game.setMensaje(
                        siguiente.getNombre()
                        + " roba 2 cartas y pierde turno"
                );

                turnManager.avanzar(2);

                return 2;

            case ROBA4:

                int idx4 = (
                        turnManager.getTurnoActual()
                        + turnManager.getDireccion()
                        + jugadores.size()
                ) % jugadores.size();

                player sig = jugadores.get(idx4);

                for(int i = 0; i < 4; i++){

                    sig.getMano().agregarCarta(
                            deck.robarCarta()
                    );
                }

                game.setMensaje(
                        sig.getNombre()
                        + " roba 4 cartas y pierde turno"
                );
                turnManager.avanzar(2);
                return 2;

            case COMODIN:
                turnManager.avanzar(1);
                return 1;

            default:
                return 1;
        }
    }
}