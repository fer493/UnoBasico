/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.uno;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *Pruebas de control de turno
 */
public class TurnManagerTest {
    private TurnManager turnma;
    
    @BeforeEach
    void setUp() {
        turnma = new TurnManager(4);
    }

    @Test
    void testSiguienteTurno() {
        turnma.siguienteTurno();
        assertEquals(1,turnma.getTurnoActual());
    }
    
    @Test
    void testReversa(){
        int dirAntes = turnma.getDireccion();
        turnma.reversa();
        assertEquals(dirAntes * -1,turnma.getDireccion());
    }
    
    @Test
    void testAvanzarEnReversa(){
        turnma.reversa();
        turnma.siguienteTurno();
        assertEquals(3,turnma.getTurnoActual());
    }
    
}
