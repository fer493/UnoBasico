/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.uno;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 *Clase de pruebas para validar el comportamiento del juego Uno
 * Incluye:
 * -Pruebas d reglas (comodin,roba4,reversa)
 * -Pruebas de turnos (jugador humano y bot)
 * -Deteccion de errorres 
 */
public class RuleEngineTest {
    TurnManager turnm;
    RuleEngine rule;
    deck deck;
    DiscardPile pila;
    game game;
    ArrayList<player> jugadores;
    player p1;
    player p2;
    
    /**
    * Inicializa los objetos antes de cada prueba para evitar efectos colaterales
    */
    @BeforeEach
    void setUp() {
        //Para testear la direccion normal de turno
        turnm = new TurnManager(2);
        rule = new RuleEngine();
        deck = new deck();
        pila = new DiscardPile();
        game = new game(true);
        jugadores = new ArrayList<>();
        p1 = new player("A",true);
        p2 = new player("B",false);
        jugadores.add(p1);
        jugadores.add(p2);   
    }
    
    //===================================
    //- Pruebas de Reglas -
    //===================================
    
    @Test
    @DisplayName("Salto hace perder turno al siguiente jugador")
    void testSalto() {
    card salto = new card(card.Color.VERDE, card.Tipo.SALTO);
    int pasos = rule.aplicarEfecto(salto, p1, jugadores, turnm, deck, pila, game);
    assertEquals(2, pasos);
   }

    @Test
    @DisplayName("Roba2 hace que el siguiente jugador robe 2 cartas")
    void testRoba2() {
    int antes = p2.getMano().size();
    card roba2 = new card(card.Color.AZUL, card.Tipo.ROBA2);
    int pasos = rule.aplicarEfecto(roba2, p1, jugadores, turnm, deck, pila, game);
    int despues = p2.getMano().size();
    assertEquals(antes + 2, despues);
    assertEquals(2, pasos);
}
 
    /**
    * Verifica que la carta comodín cambie el color y avance un turno 
    */
    @Test
    @DisplayName("Comodin cambia color y avanza turno")
    void testComodin(){
        card comodin = new card(card.Color.NEGRO,card.Tipo.COMODIN);
        int pasos = rule.aplicarEfecto(comodin,p1,jugadores,turnm,deck,pila,game);
        assertEquals(1,pasos);
        assertNotNull(pila.getCartaActual());
    }
    
    /**
    * Verifica que la carta roba4 haga que el siguiente jugador robe 4 cartas
    */
    @Test
    @DisplayName("Roba4 hace que el siguiente jugador robe 4 cartas")
    void testRoba4() {
        card roba4 = new card(card.Color.NEGRO,card.Tipo.ROBA4);
        int antes = p2.getMano().size();
        int pasos = rule.aplicarEfecto(roba4, p1, jugadores, turnm, deck, pila, game);
        int despues = p2.getMano().size();
        assertEquals(antes + 4,despues);
        assertEquals(2,pasos);
    }
    
     /**
    * Verifica que la carta reversa cambie la direccion del juego
    */
    @Test
    @DisplayName("Reversa cambia la dirección del juego")
    void testReversa(){
        int dirAntes = turnm.getDireccion();
        card reversa = new card(card.Color.ROJO,card.Tipo.REVERSA);
        int pasos = rule.aplicarEfecto(reversa, p1, jugadores, turnm, deck, pila, game);
        int dirDespues = turnm.getDireccion();
        assertEquals(dirAntes * -1,dirDespues);
        assertEquals(1,pasos);
    }
    
    //=================================
    //Pruebas de turnos
    //=================================
     /**
    * Verifica que un jugador bot pueda jugar una carta válida
    */
    @Test
    @DisplayName("Bot juega carta válida")
    void testTurnoBot(){
        card mesa = new card(card.Color.ROJO,5);
        p2.getMano().agregarCarta(new card(card.Color.ROJO,7));
        card jugada = p2.jugarTurno(game, mesa);
        assertNotNull(jugada);
    }
    
     /**
    * Verifica que jugador humano pueda robar carta correctamente
    */
    @Test
    @DisplayName("Humano roba carta correctamente")
    void testTurnoHumanoRobar(){
        int antes = p1.getMano().size();
        p1.robarCarta(deck);
        int despues = p1.getMano().size();
        assertEquals(antes + 1,despues);
    }
    
    //==================================
    //Detección de errores
    //==================================
    
     /**
    * Verifica que el método no falle cuando la carta es null
    */
    @Test
    @DisplayName("No falla cuando carta es null")
    void testCartaNull(){
        int pasos = rule.aplicarEfecto(null, p1, jugadores, turnm, deck, pila, game);
        assertEquals(1,pasos);
    }
    
     /**
    * Verifica que al obtener una carta con indice inválido no genere excepción
    */
    @Test
    @DisplayName("Indice invalido en mano retorna null")
    void testIndiceInvalido(){
        card c = p1.getMano().getCarta(10);
        assertNull(c);
    }
    
     /**
    * Verifica el comportamiento cuando el deck está vacio
    */
    @Test
    @DisplayName("Robar carta de deck vacio retorna null")
    void testDeckVacio(){
        while(deck.robarCarta() !=null){}
        card c = deck.robarCarta();
        assertNull(c);
        
    }
    
}
