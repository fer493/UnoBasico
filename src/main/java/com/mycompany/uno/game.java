/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.Scanner;

/**
 *Controla el flujo del juego Uno basico
 *Version semanadas 1-2 sin cartas especiales
 * jugador vs computadora
 */
public class game {
    private deck deck;
    private hand jugador;
    private hand computadora;
    private card cartaMesa;
    //Es un lector de texto que permite leer datos desde una fuente de entrada
    private Scanner scanner;
    
    public game(){
        deck = new deck();
        jugador = new hand();
        computadora = new hand();
        /**
         * Flujo de entrada estandar del programa
         * param systemin representa lo que el usuario escribe en el teclado
         * Captura la opcion de la carta que el jugador juega
         */
        scanner= new Scanner(System.in);
        
        
    }
    /**
     * Inicia juego
     */
    public void iniciar(){
        repartirCartas();
        
        cartaMesa = deck.robarCarta();
        System.out.println("Carta inicial en mesa: " + cartaMesa);
        System.out.println("----------------------------------");
    
    while (true) {
    turnoJugador();
    if(jugador.estaVacia()){
        System.out.println("Ganaste");
        break;
    }
    turnoComputadora();
    if(computadora.estaVacia()){
        System.out.println("La computadora gana");
        break;
    }
}
}
 /**
  * Reparte 7 cartas a cada jugador
  */  
private void repartirCartas(){
    for (int i=0; i<7; i++){
        jugador.agregarCarta(deck.robarCarta());
        computadora.agregarCarta(deck.robarCarta());
    }
}
/**
 * Turno jugador humano
 */
private void turnoJugador(){
    System.out.println("---TU TURNO---");
    System.out.println("Carta en mesa: " + cartaMesa);
    jugador.mostrarMano();
    
    if(!jugador.tieneJugadaValida(cartaMesa)){
        System.out.println("No tienen jugada valida");
        jugador.agregarCarta(deck.robarCarta());
        return;
    }
    while (true){
        System.out.println("Elige carta a jugar (numero) o -1 para robar:");
        int opcion = scanner.nextInt();
        
        if (opcion == -1){
            jugador.agregarCarta(deck.robarCarta());
            System.out.println("Robaste carta");
            return;
        }
        card seleccionada = jugador.jugarCarta(opcion);
        
        if(seleccionada == null) {
            System.out.println("Indice invalido");
            continue;
        }
        
        if (seleccionada.esJugableSobre(cartaMesa)){
            cartaMesa = seleccionada;
            System.out.println("Jugaste: " + cartaMesa);
            return;
        }else{
            System.out.println("Carta no valida. Regresa a tu mano");
            jugador.agregarCarta(seleccionada);
        }
    }
    }
/**
 * Turno computadora (IA basica)
 */
private void turnoComputadora(){
    System.out.println("---TURNO COMPUTADORA---");
    System.out.println("Carta en mesa: " + cartaMesa);
    
    for (int i=0; i< computadora.size(); i++){
        card c= computadora.jugarCarta(i);
        
        if (c.esJugableSobre(cartaMesa)){
            cartaMesa = c;
            System.out.println("Computadora juega: " + cartaMesa);
            return;
        }else{
            computadora.agregarCarta(c); //regresa carta
        }
    }
    //si no pudo jugar
    System.out.println("computadora roba carta");
    computadora.agregarCarta(deck.robarCarta());
}



}
