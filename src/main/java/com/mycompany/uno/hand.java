/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;

/**
 *
 * Representa la mano de un jugador
 * Contiene las cartas que se poseen y operaciones sobre ellas
 */
public class hand {
    private ArrayList<card> cartas;
    /** Crea una mano vacía */
    public hand(){
        cartas = new ArrayList<>();
    }
    
    /** 
    * Agrega una carta a la mano
    * @param c Carta a agregar
    */
    public void agregarCarta(card c){
        cartas.add(c);
    }
    
    /** 
    * Juega una carta de la mano
    * @param indice Indicde de la carta
    * @return Carta jugada o null si indice es invalido
    */
    public card jugarCarta(int indice){
        if (indice <0 || indice >= cartas.size()) return null;
        return cartas.remove(indice);
    }
    
    /** 
    * Obtiene una carta sin removerla
    * @param indice Indice de la carta
    * @return Carta o null si indice es invalido
    */
    public card getCarta(int indice){
        if (indice <0 || indice >= cartas.size()) return null;
        return cartas.get(indice);
    }
    
    /** @return Número de cartas en la mano */
    public int size(){
        return cartas.size();
    }
    
    /** @return Número de cartas en la mano */
    public boolean estaVacia(){
        return cartas.isEmpty();
    }
    
    /** Muestra las cartas en consola */
    public void mostrarMano(){
        System.out.println("Cartas en mano (" + cartas.size() + "):");
        for (int i=0; i< cartas.size(); i++){
            System.out.println(i + ": " + cartas.get(i));
        }
    }
    
    /**
     * Verifica si tienen al menos una jugada válida
     * @param cartaMesa Carta en mesa
     * @return true si hay jugada Válida
     */
    public boolean tieneJugadaValida(card cartaMesa){
        for (card c : cartas){
            if (c.esJugableSobre(cartaMesa)){
                return true;
            }
        }
        return false;
    }
}
