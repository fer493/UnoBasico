/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *Controla el flujo del juego Uno basico
 *Version semanadas 1-2 sin cartas especiales
 * jugador vs computadora
 */
public class game {
    private deck deck;
    private ArrayList<player> jugadores;
    private int turnoActual;
    private int direccion;
    private card cartaMesa;
    //Es un lector de texto que permite leer datos desde una fuente de entrada
    private Scanner scanner;
    
    public game(){
         /**
         * Flujo de entrada estandar del programa
         * param systemin representa lo que el usuario escribe en el teclado
         * Captura la opcion de la carta que el jugador juega
         */
        scanner= new Scanner(System.in);

        System.out.println("Ingresa nombre:");
        String nombreJugador = scanner.nextLine();

        deck = new deck();
        jugadores = new ArrayList<>();

        jugadores.add(new player(nombreJugador, true));
        jugadores.add(new player("Pepe", false));
        jugadores.add(new player("Ana", false));
        jugadores.add(new player("Mari", false));

        turnoActual = 0;
        direccion = 1;   
    }
    public deck getDeck(){
        return deck;
    }
    private String elegirColorJugador(){
    System.out.println("Elige color: rojo, azul, verde, amarillo!");

    while(true){
        String color = scanner.next().trim().toLowerCase();

        if(color.equals("rojo") || color.equals("azul") ||
           color.equals("verde") || color.equals("amarillo")){
            return color;
        }
     
        System.out.println("Color invalido.");
    }
}    
    private String elegirColor(player jugador){

    if(jugador.esHumano()){
        return elegirColorJugador();
    }

    String[] colores = {"rojo","azul","verde","amarillo"};
    return colores[(int)(Math.random()*4)];
    }
    public String leerSN(){
        while(true){
            String resp = scanner.next();

            if(resp.equalsIgnoreCase("S") || resp.equalsIgnoreCase("N")){
                return resp.toUpperCase();
            }

            System.out.println("Opcion invalida. Escribe S o N:");
        }
    }
    public int leerNumero(){
        while(true){
            try{
                return scanner.nextInt();
            }catch(Exception e){
                System.out.println("Entrada invalida");
                scanner.next();
            }
        }
    }
    /**
     * Inicia juego
     */
    public void iniciar(){
         repartirCartas();

    do{
        cartaMesa = deck.robarCarta();
    }while(!cartaMesa.getTipo().equals("numero"));

    System.out.println("Carta inicial en mesa: " + cartaMesa);

    while(true){

        player actual = jugadores.get(turnoActual);

        mostrarEstado();

        System.out.println("Turno de: " + actual.getNombre());

        card jugada = actual.jugarTurno(this, cartaMesa);

        if(jugada != null){
            cartaMesa = jugada;
            System.out.println(actual.getNombre() + " juega: " + cartaMesa);

            efectos(cartaMesa, actual);
        }

        if(actual.getMano().estaVacia()){
            System.out.println("¡" + actual.getNombre() + " ha ganado!");
            break;
        }

        siguienteTurno();
    }
}
    private void siguienteTurno(){
    turnoActual = (turnoActual + direccion + jugadores.size()) % jugadores.size();
    }
 /**
  * Reparte 7 cartas a cada jugador
  */  
    private void repartirCartas(){
        for(int i=0; i<7; i++){
            for(player p : jugadores){
                p.getMano().agregarCarta(deck.robarCarta());
            }
        }
    }
    public void mostrarEstado(){
        System.out.println("--------------------");
        System.out.println("Carta en mesa: " + cartaMesa);

        for(player p : jugadores){
            System.out.println(p.getNombre() + ": " + p.getMano().size() + " cartas");
        }

        System.out.println("--------------------");
    
    }
    private void efectos(card carta, player jugador){

        String tipo = carta.getTipo();

        switch(tipo){

            case "reversa":
                direccion *= -1;
                System.out.println("Cambio de direccion!");
                break;

            case "salto":
                siguienteTurno();
                System.out.println("Turno saltado!");
                break;

            case "roba2":
                siguienteTurno();
                player siguiente = jugadores.get(turnoActual);

                for(int i=0;i<2;i++){
                    siguiente.getMano().agregarCarta(deck.robarCarta());
                }

                System.out.println(siguiente.getNombre() + " roba 2 cartas");

                siguienteTurno(); 
                break;

            case "roba4":
                siguienteTurno();
                player sig = jugadores.get(turnoActual);

                for(int i=0;i<4;i++){
                    sig.getMano().agregarCarta(deck.robarCarta());
                }

                System.out.println(sig.getNombre() + " roba 4 cartas");

                String color = elegirColor(jugador);
                cartaMesa = new card(color,"roba4");
                break;

            case "comodin":
                String nuevoColor = elegirColor(jugador);
                cartaMesa = new card(nuevoColor,"comodin");
                break;
        }
    }
    }
