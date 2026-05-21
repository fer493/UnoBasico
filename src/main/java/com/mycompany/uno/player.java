/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 * Representa un jugador del juego Uno
 * Puede ser humano o bot, y contiene su mano de cartas
 */
public class player {

    private String nombre;
    private hand mano;
    private boolean esHumano;

    /**
     * Constructor de jugador
     * @param nombre Nombre del jugador
     * @param esHumano true si es humano, false si es bot
     */
    public player(String nombre, boolean esHumano) {

        this.nombre = nombre;
        this.esHumano = esHumano;
        this.mano = new hand();
    }

    /** @return Nombre del jugador */
    public String getNombre() {
        return nombre;
    }

    /** @return true si es humano */
    public boolean esHumano() {
        return esHumano;
    }

    /** @return Mano del jugador */
    public hand getMano() {
        return mano;
    }

    /**
     * Roba una carta del mazo
     * @param deck Mazo de donde se roba
     */
    public void robarCarta(deck deck) {

        card c = deck.robarCarta();

        if(c != null){

            mano.agregarCarta(c);

        }
    }

    /**
     * Verifica si el jugador tiene almenos una jugada válida
     * sobre la carta actual en mesa
     * @param cartaMesa Carta en mesa
     * @return true si existe jugada válida
     */
    public boolean tieneJugadaValida(card cartaMesa) {

        return mano.tieneJugadaValida(cartaMesa);
    }

    /**
     * Lógica de turno para un jugador bot (IA)
     * @param game Instancia del juego
     * @param cartaMesa Carta actual en mesa
     * @return Carta jugada o null si no pudo jugar
     */
    private card turnoIA(game game, card cartaMesa) {

        try {

            Thread.sleep(3000);

        } catch(InterruptedException e) {

            Thread.currentThread().interrupt();
        }

        if(mano.size() == 2){

            game.setMensaje(nombre + " dice UNO!");
        }

        // buscar carta jugable
        for(int i = 0; i < mano.size(); i++){

            card c = mano.getCarta(i);

            if(c.esJugableSobre(cartaMesa)){

                return mano.jugarCarta(i);
            }
        }

        // si no hay jugada → roba
        card robada = game.getDeck().robarCarta();

        if(robada == null){

            game.setMensaje(
                    nombre + " no puede robar, deck vacío"
            );

            return null;
        }

        if(robada.esJugableSobre(cartaMesa)){

            game.setMensaje(
                    nombre + " juega carta robada"
            );

            return robada;

        } else {

            mano.agregarCarta(robada);

            game.setMensaje(
                    nombre + " roba carta"
            );

            return null;
        }
    }

    /**
     * Juega un turno completo
     * @param game Instancia del juego
     * @param cartaMesa Carta actual en mesa
     * @return Carta jugada o null si no jugó
     */
    public card jugarTurno(game game, card cartaMesa) {

        if(esHumano){

            return turnoHumano(game, cartaMesa);

        } else {

            return turnoIA(game, cartaMesa);
        }
    }

    /**
     * Lógica de turno para jugador humano
     * @return null si no jugó
     */
    private card turnoHumano(game game, card cartaMesa) {

      
        return null;
    }
}