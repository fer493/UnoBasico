/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * Pruebas de comportamiento del jugador y bot
 */
public class playerTest {
    private player bot;
    private player humano;
    private deck deck;
    private game game;
    
    @BeforeEach
    void setUp() {
        humano = new player("A",true);
        bot = new player("B",false);
        deck = new deck();
        game = new game(true);
    }

    @Test
    public void testBotJuegaCarta() {
        card mesa = new card(card.Color.ROJO,5);
        bot.getMano().agregarCarta(new card(card.Color.ROJO,7));
        card jugada = bot.jugarTurno(game, mesa);
    }
    @Test
    void testHumanoRobaCarta(){
        int antes = humano.getMano().size();
        humano.robarCarta(deck);
        assertEquals(antes + 1,humano.getMano().size());
    }
    
}
