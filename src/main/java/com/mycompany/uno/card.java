/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Representa una carta del juego Uno
 * Puede ser númerica o especial (salto, reversa, roba2, roba4, comodin)
 *
 * contiene color, número y tipo, además de lógica para validar jugadas
 */
public class card {
    private Color color;
    private int numero;
    private Tipo tipo;
    // NUEVO
    private ImageIcon imagen;
    /**
     * Constructor para cartas númericas
     * @param color Color de la carta
     * @param numero Numero de la carta
     */
    public card(Color color, int numero){
        this.color = color;
        this.numero = numero;
        this.tipo = Tipo.NUMERO;
        cargarImagen();
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
        cargarImagen();
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
    // NUEVO
    public ImageIcon getImagen(){
        return imagen;
    }
    // NUEVO
    private void cargarImagen(){
        String nombreArchivo;
        // =========================
        // COMODINES SIEMPRE NEGROS
        // =========================
        if(tipo == Tipo.COMODIN ||tipo == Tipo.ROBA4){
            nombreArchivo ="negro_" + tipo.toString().toLowerCase()+ ".png";
        }else if(tipo == Tipo.NUMERO){
            nombreArchivo =color.toString().toLowerCase()+ "_"+ numero+ ".png";
        }else{
            nombreArchivo =color.toString().toLowerCase()+ "_"+ tipo.toString().toLowerCase()+ ".png";
        }
        java.net.URL location =getClass().getResource("/cartas/" + nombreArchivo);
        if(location == null){
            System.out.println("No se encontró imagen: "+ nombreArchivo);
            return;
        }
        ImageIcon original =new ImageIcon(location);
        Image escalada =original.getImage().getScaledInstance(
                80,
                120,
                Image.SCALE_SMOOTH
            );
        imagen = new ImageIcon(escalada);
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
        if(tipo == Tipo.NUMERO &&
           otra.tipo == Tipo.NUMERO &&
           numero == otra.numero){

            return true;
        }

        // mismo tipo (salto, reversa, etc.)
        if(tipo == otra.tipo &&
           tipo != Tipo.NUMERO){

            return true;
        }

        return false;
    }
    
    public void setColor(Color color){
        this.color=color;
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