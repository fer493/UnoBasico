/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.uno;

import java.awt.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;


/**
 *
 * @author USER
 */
public class UnoUITest {
    
    public UnoUITest() {
    }

    @Test
    @DisplayName("La ventana se inicializa correctamente")
    void testVentanaInicializada() {
        UnoUI ui=new UnoUI();
        assertNotNull(ui);
    }
    
    @Test
    @DisplayName("Existe carta inicial en mesa")
    void testCartaInicialMesa(){
        UnoUI ui=new UnoUI();
        assertNotNull(ui.getJuego().getPila().getCartaActual());
    }
    
    @Test
    @DisplayName("El log contiene carta inicial")
    void testLosInicial(){
        UnoUI ui=new UnoUI();
        assertTrue(ui.getLog().getText().contains("carta en mesa"));
    }
    
    @Test
    @DisplayName("Botón uno creado")
    void testBotonUNO(){
        UnoUI ui=new UnoUI();
        assertNotNull(ui.getBtnUno());
    }
    
    @Test
    @DisplayName("Boton robar creado")
    void testBotonRobar(){
        UnoUI ui=new UnoUI();
        assertNotNull(ui.getBtnRobar());
    }
    
    @Test
    @DisplayName("Cambio de color mesa")
    void testActualiarColorMesa(){
        UnoUI ui=new UnoUI();
        card carta=new card(card.Color.ROJO,5);
        ui.actualizarColorMesa(carta);
        assertEquals(Color.RED,ui.getColorMesa().getBackground());
    }
    
    @Test
    @DisplayName("Robar carta aumente mano")
    void testRobarCarta(){
        UnoUI ui=new UnoUI();
        player jugador=ui.getJuego().getJugadores().get(0);
        
        int antes=jugador.getMano().size();
        jugador.robarCarta(ui.getJuego().getDeck());
        int despues=jugador.getMano().size();
        assertEquals(antes+1,despues);
    }
    
    @Test
    @DisplayName("Mostrar ganador")
    void testMostrarGanador(){
        UnoUI ui=new UnoUI();
        assertDoesNotThrow((Executable) () -> {
            ui.mostrarGanador("jugador");
        });
    }
}
