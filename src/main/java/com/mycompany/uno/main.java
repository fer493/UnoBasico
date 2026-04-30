/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *
 * Clase principal que arranca el juego Uno
 */
public class main {
    
    /**
     * Método main: crea una instancia del juego y la inicia
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args){
        game juego = new game();
        juego.iniciar();
    }
    
}
