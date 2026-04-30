/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *Reprsenta la pila de descarte del juego Uno
 * Contiene la carta actual en mesa
 */
public class DiscardPile {
     private card cartaActual;
    
     /**
      * Coloca una carta en la pila
      * @param c Carta a colocar
      */
     public void ponerCarta(card c){
         cartaActual = c;
     }
     
     /** @return Carta actual en la pila */
     public card getCartaActual(){
         return cartaActual;
     }
    
}
