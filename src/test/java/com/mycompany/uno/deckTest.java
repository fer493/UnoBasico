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
 * Prueba del mazo de cartas
 */
public class deckTest {
    private deck deck;
    
    @BeforeEach
    void setUp() {
        deck = new deck();
    }

    @Test
    void testRobarCarta() {
        assertNotNull(deck.robarCarta());
    }

    @Test
    void testDeckVacio() {
        while(deck.robarCarta() != null){}
        assertNull(deck.robarCarta());
    }
    
}
