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
 * Pruebas de la mano de cartas
 */
public class handTest {
    private hand hand;
    
    @BeforeEach
    void setUp() {
        hand = new hand();
    }

    @Test
    void testAgregarCarta() {
        hand.agregarCarta(new card(card.Color.AZUL, 3));
        assertEquals(1, hand.size());
    }

    @Test
    void testIndiceInvalido() {
        assertNull(hand.getCarta(5));
    }
    
}
