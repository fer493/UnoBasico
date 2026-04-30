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
    private TurnManager turnManager;
    private RuleEngine ruleEngine;
    private DiscardPile pila;
    //Es un lector de texto que permite leer datos desde una fuente de entrada
    private Scanner scanner;
    
    /**Constructor interactivo: pide nombre y crea jugadores */
    public game(){
        scanner = new Scanner(System.in);
        System.out.println("Ingresa nombre:");
        String nombre = scanner.nextLine();
        deck = new deck();
        jugadores = new ArrayList<>();
        jugadores.add(new player(nombre, true));
        jugadores.add(new player("Pepe", false));
        jugadores.add(new player("Toña", false));
        jugadores.add(new player("Mari", false));
        turnManager = new TurnManager(jugadores.size());
        ruleEngine = new RuleEngine();
        pila = new DiscardPile();
    }
    
    /** Constructor para pruebas */
    public game(boolean modoTest) {
        deck = new deck();
        jugadores = new ArrayList<>();
        jugadores.add(new player("TestHumano", true));
        jugadores.add(new player("TestBot", false));
        turnManager = new TurnManager(jugadores.size());
        ruleEngine = new RuleEngine();
        pila = new DiscardPile();
    
    }
    
    /** @return Mazo actual */
    public deck getDeck(){
        return deck;
    }
    
    /** Lee confirmación S/N del usuario */
    public String leerRespuestaSiNo(){
        while(true){
            String resp = scanner.next();

            if(resp.equalsIgnoreCase("S") || resp.equalsIgnoreCase("N")){
                return resp.toUpperCase();
            }

            System.out.println("Opción inválida. Escribe S o N:");
        }
    }
    
    /** Lee un número válido del usuario */
    public int leerEnteroValido(){
        while(true){
            try{
                return scanner.nextInt();
            }catch(Exception e){
                System.out.println("Entrada inválida");
                scanner.next();
            }
        }
    }
    
    /** Permite elegir color (humano o bot) */
    public card.Color elegirColor(player jugador){

        if(jugador.esHumano()){
            System.out.println("Elige color: rojo, azul, verde, amarillo!");

            while(true){
                String color = scanner.next().trim().toLowerCase();

                switch(color){
                    case "rojo": return card.Color.ROJO;
                    case "azul": return card.Color.AZUL;
                    case "verde": return card.Color.VERDE;
                    case "amarillo": return card.Color.AMARILLO;
                }

                System.out.println("Color inválido");
            }
        }
        card.Color[] colores = {
            card.Color.ROJO,
            card.Color.AZUL,
            card.Color.VERDE,
            card.Color.AMARILLO
        };
        card.Color elegido = colores[(int)(Math.random()*4)];
        System.out.println(jugador.getNombre() + " cambia a: " + elegido);

        return elegido;
    }
    
    /** Incia el juego completo */
    public void iniciar(){
        repartirCartas(); 
        card inicial;

        do{
            inicial = deck.robarCarta();

            if(inicial == null){
                System.out.println("No hay cartas para iniciar");
                return;
            }

        }while(inicial.getTipo() != card.Tipo.NUMERO);
        pila.ponerCarta(inicial);

        System.out.println("Carta inicial: " + pila.getCartaActual());

        boolean fin = false;

        while(!fin){   
            fin = turno();
        }
    }
    
    /**Ejecuta un turno */
    private boolean turno(){

        player actual = jugadores.get(turnManager.getTurnoActual());

        mostrarEstado();
        System.out.println("Turno de: " + actual.getNombre());

        card jugada = actual.jugarTurno(this, pila.getCartaActual());

        boolean cartaEspecial = false;

        if(jugada != null){
            pila.ponerCarta(jugada);
            System.out.println(actual.getNombre() + " juega: " + jugada);

            cartaEspecial = (jugada.getTipo() != card.Tipo.NUMERO);

            ruleEngine.aplicarEfecto(
                jugada,
                actual,
                jugadores,
                turnManager,
                deck,
                pila,
                this
            );
        }
        if(actual.getMano().estaVacia()){
            System.out.println("¡" + actual.getNombre() + " ha ganado!");
            return true;
        }
        if(jugada == null){
            turnManager.siguienteTurno();
        }else if(!cartaEspecial){
            turnManager.siguienteTurno();
        }
        return false;
    }
    
    /** Reparte cartas iniciales */
    private void repartirCartas(){
        for(int i=0; i<7; i++){
            for(player p : jugadores){
                p.getMano().agregarCarta(deck.robarCarta());
            }
        }
    }
    
    /** Muestra estado actual del juego */
    public void mostrarEstado(){
        System.out.println("\n--------------------");
        System.out.println("Carta en mesa: " + pila.getCartaActual());
        
        System.out.println("--------------------\n");
    }
    }
