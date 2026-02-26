/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;

/**
 *
 * Representa la mano de un jugador
 */
public class hand {
    private ArrayList<card> cartas;
    
    public hand(){
        cartas = new ArrayList<>();
    }
    
    public void agregarCarta(card c){
        cartas.add(c);
    }
    
    public card jugarCarta(int indice){
        if (indice <0 || indice >= cartas.size()) return null;
        return cartas.remove(indice);
    }
    
    public int size(){
        return cartas.size();
    }
    
    public boolean estaVacia(){
        return cartas.isEmpty();
    }
    
    public void mostrarMano(){
        System.out.println("Cartas en mano:");
        for (int i=0; i< cartas.size(); i++){
            System.out.println(i + ": " + cartas.get(i));
        }
    }
    
    /**
     * Verifica si tienen al menos una jugada valida
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
