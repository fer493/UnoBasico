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
    private String tipo;
    
    public card(String color, int numero){
        this.color=color;
        this.numero=numero;   
        this.tipo="numero";
    }
    public card(String color, String tipo){
        this.color=color;
        this.numero=-1;   
        this.tipo=tipo;
    }
    public String getTipo(){
       return tipo; 
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
        if(this.tipo.equals("comodin") || this.tipo.equals("roba4")) return true;
        if(this.color.equals(otra.color)) return true;
        if(this.numero == otra.numero && this.numero != -1) return true;
        if(this.tipo.equals(otra.tipo) && !this.tipo.equals("numero"))return true;
        return false;
    }    
    @Override
    public String toString(){
        if(tipo.equals("numero")){
            return color + " " + numero;
        }else{
            return color + " " + tipo;
        }    
    }
    
    
    
    
}
