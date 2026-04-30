/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import com.mycompany.uno.card.Color;
import com.mycompany.uno.card.Tipo;
import java.util.ArrayList;
import java.util.Collections;
/**
 *Representa el mazo de cartas del juego Uno
 * contiene la lógica para crear, barajar y robar cartas
 */
public class deck {
    private ArrayList<card> cartas;
    
    /** Crea un mazo completo y lo baraja */
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
        for(Color color : Color.values()){

        if(color == Color.NEGRO) continue;

        // 0 una vez
        cartas.add(new card(color, 0));

        // 1–9 dos veces
        for(int i = 1; i <= 9; i++){
            for(int j = 0; j < 2; j++){
                cartas.add(new card(color, i));
            }
        }

        // especiales
        for(int i = 0; i < 2; i++){
            cartas.add(new card(color, Tipo.SALTO));
            cartas.add(new card(color, Tipo.REVERSA));
            cartas.add(new card(color, Tipo.ROBA2));
        }
    }

    // comodines
    for(int i = 0; i < 4; i++){
        cartas.add(new card(Color.NEGRO, Tipo.COMODIN));
        cartas.add(new card(Color.NEGRO, Tipo.ROBA4));
    }
        
    }
    
    /** Baraja las cartas del mazo */
    public void barajar(){
        Collections.shuffle(cartas);
    }
    
    /** 
    * Roba una carta del mazo
    * @return Carta robada o null si el mazo está vacio
    */
    public card robarCarta(){
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    } 
    
    /** @return Npumero de cartas restantes en el mazo */
    public int cartasRestantes(){
        return cartas.size();
    }
    
}
