/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.uno;

/** Representa una carta del juego Uno
 *Pueder ser númerica o especial (salto, reversa, roba2, roba4, comodin)
 * 
 * contiene color, número y tipo, además de lógica para validar jugadas
 */
public class card {
     private Color color;
    private int numero;
    private Tipo tipo;
    
    /** 
    * Constructor para cartas númericas
    * @param color Color de la carta
    * @param numero Numero de la carta
    */
    public card(Color color, int numero){
        this.color = color;
        this.numero = numero;
        this.tipo = Tipo.NUMERO;
    }

    /** 
    * constructor para cartas especiales
    * @param color Color de la carta
    * @param tipo Tipo de carta especial
    */
    public card(Color color, Tipo tipo){
        this.color = color;
        this.numero = -1;
        this.tipo = tipo;
    }
    /** 
    * Enumeración de tipos de carta 
    */
    public enum Tipo {
        NUMERO, SALTO, REVERSA, ROBA2, ROBA4, COMODIN
    }
    
    /** 
    * Enumeración de colores de carta 
    */
    public enum Color {
        ROJO, AZUL, VERDE, AMARILLO, NEGRO
    }

    /** @return Tipo de la carta */
    public Tipo getTipo(){
        return tipo;
    }

    /** @return Color de la carta */
    public Color getColor(){
        return color;
    }

    /** @return Número de la carta (o -1 si es especial) */
    public int getNumero(){
        return numero;
    }

    /** 
    * verifica si una carta puede jugarse sobre otra
    * @param otra Carta en mesa
    * @return true si es jugable, false en caso contrario
    */
    public boolean esJugableSobre(card otra){

        if(otra == null) return true;

        // comodines siempre se pueden jugar
        if(tipo == Tipo.COMODIN || tipo == Tipo.ROBA4){
            return true;
        }

        // mismo color
        if(color == otra.color){
            return true;
        }

        // mismo número
        if(tipo == Tipo.NUMERO && otra.tipo == Tipo.NUMERO &&
           numero == otra.numero){
            return true;
        }

        // mismo tipo (salto, reversa, etc.)
        if(tipo == otra.tipo && tipo != Tipo.NUMERO){
            return true;
        }

        return false;
    }
    
    /** @return Representación en texto de la carta */
    @Override
    public String toString(){
        if(tipo == Tipo.NUMERO){
            return color + " " + numero;
        }else{
            return color + " " + tipo;
        }
    }
}