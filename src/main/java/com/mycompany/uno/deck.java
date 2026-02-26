/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author USER
 */
public class deck {
    private ArrayList<card> cartas;
    
    public deck(){
        cartas=new ArrayList<>();
        crearBaraja();
        barajar();
    }
    /**
     * Crea baraja basica:
     * 4 colores, numeros 0-9, dos de cada numero
     */
    private void crearBaraja(){
        String [] colores = {"rojo", "azul", "verde", "amarillo"};
        
        for (String color : colores){
            for (int i=0; i<=9; i++){
                cartas.add(new card(color,i));
                cartas.add(new card(color,i)); //dos de cada
            }
        }
    }
    public void barajar(){
        Collections.shuffle(cartas);
    }
    
    public card robarCarta(){
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
    
    public int cartasRestantes(){
        return cartas.size();
    }
}
