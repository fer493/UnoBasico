/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *Controla el orden de turno en el juego Uno
 * Maneja turno actual y dirección del juego
 */
public class TurnManager {
    private int turnoActual;
    private int direccion;
    private int totalJugadores;
    
    /**
     * Constructor
     * @param totalJugadores Número total de jugadores 
     */
     public TurnManager(int totalJugadores){
        this.totalJugadores = totalJugadores;
        this.turnoActual = 0;
        this.direccion = 1;
    }
     
     /** @return Turno actual */
     public int getTurnoActual(){
        return turnoActual;
    }
     
     /**
      * AVANZA VARIOS TURNOS
      * @param pasos Número de pasos a avanzar
      */
     public void avanzar(int pasos) {
         turnoActual = (turnoActual + pasos * direccion + totalJugadores) % totalJugadores;
    }
     /** Avanza al siguiente turno */
     public void siguienteTurno(){
        avanzar(1);
    }
     
     /** Invierte la dirección del juego */
     public void reversa(){
        direccion *= -1;
    }

    /** @return Dirección actual (1 o -1) */ 
    int getDireccion() {
        return direccion; 
    }
    
    

    
    
}
