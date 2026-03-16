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
    private  boolean dijoUNO = false;
    private boolean compudijoUNO = false;
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
        jugador = new hand(nombreJugador);
        computadora = new hand("Computadora");
       
        
        
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
        System.out.println("----------------------------------");
    boolean juegoActivo = true;
    while (juegoActivo) {
    turnoJugador();
    if(jugador.estaVacia()){
        if(dijoUNO){
            System.out.println("Ganaste!!");
            System.exit(0);
        }else{
            System.out.println("No dijiste UNO! Robas 2 cartas");
            jugador.agregarCarta(deck.robarCarta());
            jugador.agregarCarta(deck.robarCarta());            
        }
        juegoActivo = false;
        continue;
    }
    turnoComputadora();
    if(computadora.estaVacia()){
        if(compudijoUNO){
            System.out.println("La computadora gana");
        }
        
        juegoActivo = false;
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
public void mostrarEstado(String turno){
    System.out.println("--------------------");
    System.out.println("Turno de: " + turno);
    System.out.println("Cartas del jugador: " + jugador.size());
    System.out.println("Carta de la computadora: " + computadora.size());
    System.out.println("Cartas restantes en el mazo: "+deck.cartasRestantes());
    System.out.println("---------------------");
    
}
private boolean efectos(card carta, boolean jugadorHumano){
    String tipo = carta.getTipo();
    if(tipo.equals("roba2")){
        if(jugadorHumano){
            System.out.println("La computadora roba 2 cartas!");
                for(int i=0;i<2;i++){
                    computadora.agregarCarta(deck.robarCarta());
                }
        }else{
             System.out.println("Jugador roba 2 cartas!");
                for(int i=0;i<2;i++){
                    jugador.agregarCarta(deck.robarCarta());   
                }
        }
        return true;
    }
                
    if(tipo.equals("roba4")){
        if(jugadorHumano){
            System.out.println("La computadora roba 4 cartas!");
                for(int i=0;i<4;i++){
                    computadora.agregarCarta(deck.robarCarta());
                }
                System.out.println("Elige color: rojo, azul, verde, amarillo!");
                while(true){
                     String nuevoColor = scanner.next().trim().toLowerCase();
                     if(nuevoColor.equals("rojo") || nuevoColor.equals("azul") ||
                             nuevoColor.equals("verde")|| nuevoColor.equals("amarillo")){
                         cartaMesa = new card(nuevoColor,"roba4");
                         break;
                     }
                     System.out.println("Color invalido. Elige: rojo, azul, verde, amarillo");
                }
               
               
             
        }else{
             System.out.println("Jugador roba 4 cartas!");
                for(int i=0;i<4;i++){
                    jugador.agregarCarta(deck.robarCarta());   
                }
                String[] colores ={"rojo", "azul", "verde", "amarillo"};
                int r = (int)(Math.random()*4);
                cartaMesa = new card(colores[r],"roba4");
                System.out.println("Computadora cambia a color a: " + colores[r]);
        }
        return true;        
    }
                
  
    if(tipo.equals("salto")){
        if(jugadorHumano){
            System.out.println("La computadora pierde turno!");    
        }else{
            System.out.println("Jugador pierde turno");
        }
         return true;       
    }
    
    if(tipo.equals("comodin")){
        if(jugadorHumano){
            System.out.println("Elige color: rojo, azul, verde, amarillo!");
            while(true){
                     String nuevoColor = scanner.next().trim().toLowerCase();
                     if(nuevoColor.equals("rojo") || nuevoColor.equals("azul") ||
                             nuevoColor.equals("verde")|| nuevoColor.equals("amarillo")){
                         cartaMesa = new card(nuevoColor,"comodin");
                         break;
                     }
                     System.out.println("Color invalido. Elige: rojo, azul, verde, amarillo");
                }
        }else{
            String[] colores ={"rojo", "azul", "verde", "amarillo"};
            int r = (int)(Math.random()*4);
            cartaMesa = new card(colores[r],"comodin");
            System.out.println("Computadora cambia a color a: " + colores[r]);
        }       
    }
    
    if(tipo.equals("reversa")){
       if(jugadorHumano){
           System.out.println("Reversa!, La computadora pierde turno!");    
       }else{
            System.out.println("Reversa!, Jugador pierde turno");
       }
       return true;       
    }
    return false;
}


/**
 * Turno jugador humano
 */
private void turnoJugador(){
    if(jugador.size() > 1){
        dijoUNO = false;
    }
    
    mostrarEstado(jugador.getNombre());
    System.out.println("---TU TURNO---");
    System.out.println("Carta en mesa: " + cartaMesa);
    jugador.mostrarMano();
    
    if(!jugador.tieneJugadaValida(cartaMesa)){
        System.out.println("No tienes jugada valida, robas carta..");
        card robada = deck.robarCarta();
        System.out.println("Robaste: "+ robada);
        
        if(robada.esJugableSobre(cartaMesa)){
            System.out.println("La carta robada es jugable");
            System.out.println("¿Quieres jugarla? (S/N)");
            String respuesta;
            while(true){
                respuesta = scanner.next();
                if(respuesta.equalsIgnoreCase("S") || respuesta.equalsIgnoreCase("N")){
                    break;
                }
                System.out.println("Opcion invalida. Escribe S o N:");
            }
            if(respuesta.equalsIgnoreCase("S")){
                cartaMesa = robada;
                System.out.println("Jugaste: " + cartaMesa);
                boolean repetir = efectos(cartaMesa,true);
                if(repetir){
                    turnoJugador();
                }
            }else{
               jugador.agregarCarta(robada); 
            }
        }else{
            jugador.agregarCarta(robada);
        }
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
        card seleccionada = jugador.getCarta(opcion);
        
        if(seleccionada == null) {
            System.out.println("Indice invalido");
            continue;
        }
        
        if (seleccionada.esJugableSobre(cartaMesa)){
            //verificar que se diga uno antes de jugar
            if(jugador.size() == 2){
                System.out.println("Antes de jugar carta, ¿quieres decir UNO? (S/N)");
                String resp;
                while(true){
                    resp = scanner.next();
                    if(resp.equalsIgnoreCase("S") || resp.equalsIgnoreCase("N")){
                        break;
                    }
                    System.out.println("Opcion invalida. Escribe S o N:");
                }
                if(resp.equalsIgnoreCase("S")){
                    dijoUNO = true;
                }else{
                    System.out.println("No dijiste UNO! Robas 2 cartas ");
                    jugador.agregarCarta(deck.robarCarta());
                    jugador.agregarCarta(deck.robarCarta());
                    dijoUNO = false;
                }
            }
            cartaMesa = jugador.jugarCarta(opcion);
            System.out.println("Jugaste: " + cartaMesa);
            
            boolean repetir = efectos(cartaMesa,true);
            if(repetir){
                turnoJugador();
            }
            
            return;
        } else{
             System.out.println("Esa carta no es jugable sobre: " + cartaMesa);
             System.out.println("Elije otra carta");
        }
    }
    }
/**
 * Turno computadora (IA basica)
 */
private void turnoComputadora(){
    if(computadora.size() > 1){
        compudijoUNO = false;
    }
    
    mostrarEstado(computadora.getNombre());
    System.out.println("---TURNO COMPUTADORA---");
    System.out.println("Carta en mesa: " + cartaMesa);
    
    for (int i=0; i< computadora.size(); i++){
        card c= computadora.getCarta(i);
        
        if (c.esJugableSobre(cartaMesa)){
            if(computadora.size() == 2){
                System.out.println("Computadora dice Uno!");
                compudijoUNO = true;
            }
            cartaMesa = computadora.jugarCarta(i);
            System.out.println("Computadora juega: " + cartaMesa);
            
            boolean repetir = efectos(cartaMesa,false);
            if(repetir){
                turnoComputadora();
            }
            
            
            
            return;
        }
    }
    //si no pudo jugar
    System.out.println("computadora roba carta");
    card robada = deck.robarCarta();
    if(robada.esJugableSobre(cartaMesa)){
            System.out.println("La carta robada por la computadora es jugable");
            cartaMesa = robada;
            System.out.println("Computadora jugo: " + cartaMesa);
            boolean repetir = efectos(cartaMesa,false);
            if(repetir){
                turnoComputadora();
            }
            
    }else{
        computadora.agregarCarta(robada);
    }
    
    
    
}



}
