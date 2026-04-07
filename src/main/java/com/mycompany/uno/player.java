/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *
 * @author USER
 */
public class player {
     private String nombre;
    private hand mano;
    private boolean esHumano;

    public player(String nombre, boolean esHumano){
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.mano = new hand();
    }
    public String getNombre(){
        return nombre;
    }
    public boolean esHumano(){
        return esHumano;
    }
    public hand getMano(){
        return mano;
    }
    public void robarCarta(deck deck){
    mano.agregarCarta(deck.robarCarta());
    }
    public boolean tieneJugadaValida(card cartaMesa){
    return mano.tieneJugadaValida(cartaMesa);
    }
    private card turnoIA(game game, card cartaMesa){
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

        if(robada.esJugableSobre(cartaMesa)){
            System.out.println(nombre + " juega carta robada");
            return robada;
        }else{
            mano.agregarCarta(robada);
            System.out.println(nombre + " roba carta");
            return null;
        }
    }
    public card jugarTurno(game game, card cartaMesa){

        if(esHumano){
            return turnoHumano(game, cartaMesa);
        }else{
            return turnoIA(game, cartaMesa);
        }
        }
    private card turnoHumano(game game, card cartaMesa){

        while(true){

            mano.mostrarMano();

            System.out.println("Elige carta (-1 para robar):");

            int opcion = game.leerNumero();

            if(opcion == -1){
                robarCarta(game.getDeck());
                return null;
            }

            card seleccionada = mano.getCarta(opcion);

            if(seleccionada == null){
                System.out.println("Indice invalido");
                continue; 
            }

            if(!seleccionada.esJugableSobre(cartaMesa)){
                System.out.println("Carta no jugable");
                continue; 
            }
            if(mano.size() == 2){
                System.out.println("¿Quieres decir UNO? (S/N)");
                String resp = game.leerSN();

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

    
    
    
