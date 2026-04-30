/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *Representa un jugador del juego Uno
 * Puede ser humano o bot, y contiene su mano de cartas
 */
public class player {
     private String nombre;
    private hand mano;
    private boolean esHumano;
    
    /**
     * Constructor de jugador
     * @param nombre Nombre del jugador
     * @param esHumano true si es humano, false si es bot
     */
    public player(String nombre, boolean esHumano){
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.mano = new hand();
    }
    
    /** @return Nombre del jugador */
    public String getNombre(){
        return nombre;
    }
    
    /** @return  true si es humano */
    public boolean esHumano(){
        return esHumano;
    }
    
    /** @return Mano del jugador */
    public hand getMano(){
        return mano;
    }
    
    /**
    *Roba una carta del mazo
    * @param deck Mazo de donde se roba
    */
    public void robarCarta(deck deck){
         card c = deck.robarCarta();

        if(c != null){
            mano.agregarCarta(c);
        }else{
            System.out.println("Deck vacío, no se puede robar");
        }
    }
    
    /**
     * Verifica si el jugador tiene almenos una jugada válida
     * sobre la carta actual en mesa
     * @param cartaMesa Carta en mesa
     * @return  true si existe jugada válida, flase en caso contrario
     */
    public boolean tieneJugadaValida(card cartaMesa){
    return mano.tieneJugadaValida(cartaMesa);
    }
    
    /**
     * Lógica de turno para un jugador bot (IA)
     * -Espera 3 segundos simulando que el bot esta pensando y eligiendo carta a jugar
     * -si tienen 2 cartas, anuncia "uno"
     * -Busca una carta jugable en su mano
     * -Si no tiene jugada, roba una carta del mazo
     * @param game Instancia del juego
     * @param cartaMesa Carta actual en mesa
     * @return Carta jugada o null si no pudo jugar
     */
    private card turnoIA(game game, card cartaMesa){
        try{
            Thread.sleep(3000); // 3 segundos
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        if(mano.size() == 2){
            System.out.println(nombre + " dice UNO!");
        }
        
        // buscar carta jugable
        for(int i=0; i<mano.size(); i++){
            card c = mano.getCarta(i);

            if(c.esJugableSobre(cartaMesa)){
                return mano.jugarCarta(i);
            }
        }

        // si no hay jugada → roba
        card robada = game.getDeck().robarCarta();
        if(robada == null){
            System.out.println(nombre + " no puede robar, deck vacío");
            return null;
        }

        if(robada.esJugableSobre(cartaMesa)){
            System.out.println(nombre + " juega carta robada");
            return robada;
        }else{
            mano.agregarCarta(robada);
            System.out.println(nombre + " roba carta");
            return null;
        }
    }
    
    /**
     * Juega un turno completo
     * @param game Instancia del juego
     * @param cartaMesa Carta actual en mesa
     * @return Carta jugada o null si no jugó
     */
    public card jugarTurno(game game, card cartaMesa){

        if(esHumano){
            return turnoHumano(game, cartaMesa);
        }else{
            return turnoIA(game, cartaMesa);
        }
        }
    
    /**
     * Lógica d turno para jugador humano.
     * -Muestra la mano y pide al usuario elegir carta o robar
     * -Valida índice y jugabilidad de la carta seleccionada
     * -Si quedan 2 cartas, pregunta si quiere decir "UNO" el jugador
     * -Aplica penalización si no lo dice 
     * @param game Instancia del juego
     * @param cartaMesa Carta actual en mesa
     * @return  Carta jugada o null si no jugó
     */
    private card turnoHumano(game game, card cartaMesa){

        while(true){

            mano.mostrarMano();

            System.out.println("Elige carta (-1 para robar):");

            int opcion = game.leerEnteroValido();

            if(opcion == -1){
                robarCarta(game.getDeck());
                System.out.println(nombre + " roba carta");
                return null;
            }

            card seleccionada = mano.getCarta(opcion);

            if(seleccionada == null){
                System.out.println("Indice invalido");
                game.mostrarEstado();
                continue; 
            }

            if(!seleccionada.esJugableSobre(cartaMesa)){
                System.out.println("Carta no jugable");
                game.mostrarEstado();
                continue; 
            }
            if(mano.size() == 2){
                System.out.println("¿Quieres decir UNO? (S/N)");
                String resp = game.leerRespuestaSiNo();

            if(resp.equals("S")){
                System.out.println(nombre + " dice UNO!");
            }else{
                System.out.println("No dijiste UNO! Robas 2 cartas");
                robarCarta(game.getDeck());
                robarCarta(game.getDeck());
            }
            }       
            return mano.jugarCarta(opcion);
         }
    }
    
    
}

    
    
    
