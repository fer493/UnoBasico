/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author USER
 */
public class cardTest {
    
    public cardTest() {
    }

    @Test
    void testEsJugableSobreMismoColor() {
    card c1 = new card(card.Color.ROJO, 5);
    card c2 = new card(card.Color.ROJO, 7);
    assertTrue(c1.esJugableSobre(c2));
}

    @Test
    void testEsJugableSobreMismoNumero() {
    card c1 = new card(card.Color.AZUL, 5);
    card c2 = new card(card.Color.ROJO, 5);
    assertTrue(c1.esJugableSobre(c2));
}

    @Test
    void testEsJugableSobreComodin() {
    card c1 = new card(card.Color.NEGRO, card.Tipo.COMODIN);
    card c2 = new card(card.Color.ROJO, 5);
    assertTrue(c1.esJugableSobre(c2));
}
    
}
