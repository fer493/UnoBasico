/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.uno;

/**
 *
 * @author USER
 */
public class card {
    private String color;
    private int numero;
    
    public card(String color, int numero){
        this.color=color;
        this.numero=numero;   
    }
    public String getColor(){
        return color;
    }
    public int getNumero(){
        return numero;
    }
    /**
     * Regla basica de uno
     * se puede jugar si coincide numero o color
     */
    public boolean esJugableSobre(card otra){
        if(otra == null) return true;
        return this.color.equals(otra.color) || this.numero==otra.numero;
    }
    @Override
    public  String toString(){
        return color + " " + numero;
    }
    
    
    
    
}
