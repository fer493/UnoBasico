/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Interfaz gráfica principal del juego UNO.
 * esta clase se encarga de:
 * mostrar la interfaz del juego
 * administrar eventos de botones
 * actualizar cartas y jugadores en pantalla
 * controlar turnos del jugador y bots
 * mostrar mensajes y registros del juego
 * 
 * Utiliza sqing para la interfaz gráfica
 * @author USER
 */
public class UnoUI extends JFrame {
    private game juego;
    private JTextArea log;
    private JScrollPane scrollLog;
    private JLabel cartaMesa;
    private JLabel mazoCartas;
    private JButton btnRobar;
    private JButton btnUNO;
    private JLabel colorMesa;
    private JLabel nombreArriba;
    private JLabel nombreIzquierda;
    private JLabel nombreDerecha;
    private JLabel nombreJugador;
    private boolean turnoBots=false;
    private boolean dijoUNO=false;
    private JButton btnReiniciar;
    /**
     * Constructor principal de la interfaz UNO
     * La ventana principal
     * Los componentes g´raficos
     * Los eventos de botones
     * El juego y los jugadores
     */
    public UnoUI() {
        juego = new game("Jugador");
        juego.iniciar();
        // =========================
        // CONFIG VENTANA
        // =========================
        setTitle("UNO");
        setSize(1000, 700);
        setLayout(null);
        getContentPane().setBackground(
                new Color(80,0,120)
        );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        // =========================
        // COMPONENTES
        // =========================
        cartaMesa = new JLabel();
        mazoCartas=new JLabel();
        colorMesa = new JLabel();
        colorMesa.setOpaque(true);
        nombreArriba = new JLabel(juego.getJugadores().get(2).getNombre());
        nombreIzquierda = new JLabel(juego.getJugadores().get(1).getNombre());
        nombreDerecha = new JLabel(juego.getJugadores().get(3).getNombre());
        nombreJugador = new JLabel(juego.getJugadores().get(0).getNombre());
        btnUNO = new JButton("UNO!");
        btnReiniciar=new JButton("Reiniciar");
        btnRobar = new JButton("Robar Carta");
        log = new JTextArea();
        scrollLog=new JScrollPane(log);
        log.setEditable(false);
        agregarLog("carta en mesa: "+juego.getPila().getCartaActual());
        nombreArriba.setForeground(Color.WHITE);
        nombreIzquierda.setForeground(Color.WHITE);
        nombreDerecha.setForeground(Color.WHITE);
        nombreJugador.setForeground(Color.WHITE);
        // =========================
        // EVENTO ROBAR
        // =========================
        btnRobar.addActionListener(e -> {
            if(juego.getTurnManager().getTurnoActual() !=0 || turnoBots){
                return;
            }
            player jugador =juego.getJugadores().get(0);
            jugador.robarCarta(juego.getDeck());
            card robada = jugador.getMano().getCarta(jugador.getMano().size()-1);
            agregarLog(jugador.getNombre()+ " roba carta");
            card mesa= juego.getPila().getCartaActual();
            if(robada.esJugableSobre(mesa)){
                jugador.getMano().removerCarta(jugador.getMano().size()-1);
                if(robada.getTipo()==card.Tipo.COMODIN || robada.getTipo() ==card.Tipo.ROBA4){
                    card.Color nuevoColor=seleccionarColor();
                    robada=new card(nuevoColor,robada.getTipo());
                }
                juego.getPila().ponerCarta(robada);
                agregarLog(jugador.getNombre() + " Juega desde su mano " +robada);
                juego.getRuleEngine().aplicarEfecto(robada, jugador, juego.getJugadores(), juego.getTurnManager(), juego.getDeck(), juego.getPila(), juego);
                if(robada.getTipo() == card.Tipo.NUMERO){
                    juego.getTurnManager().siguienteTurno();
                }
            }else{
                juego.getTurnManager().siguienteTurno();
            }
            actualizarUI();
            turnoBots=true;
            javax.swing.Timer timer=new javax.swing.Timer(1000,ev ->ejecutarTurnoBots());
            timer.setRepeats(false);
            timer.start();
        });

        // =========================
        // EVENTO UNO
        // =========================

        btnUNO.addActionListener(e -> {
            if(juego.getTurnManager().getTurnoActual() != 0){
                return;
            }
            player jugador =juego.getJugadores().get(0);

            if(jugador.getMano().size() == 2){
                dijoUNO = true;
                agregarLog(jugador.getNombre()+ " dice UNO!");

            }else{
                agregarLog("No puedes decir UNO ahora");
            }
        });
        
        btnReiniciar.addActionListener(e ->{
            dispose();
            new UnoUI();
        });

        // =========================
        // REDIMENSIONAR
        // =========================

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarUI();
            }
        });

        // =========================
        // MOSTRAR
        // =========================
        setVisible(true);
        actualizarUI();
    }
    /**
     * Actualiza el color visual de la carta en mesa
     * cambia el color del papel indicador dependiendo
     * del color actual de la carta jugada
     * @param carta Carta actual en mesa
     */
    public void actualizarColorMesa(card carta){
       
        switch(carta.getColor()){
            case ROJO:
                colorMesa.setBackground(java.awt.Color.RED);
                break;
            case AZUL:
                colorMesa.setBackground(java.awt.Color.BLUE);
                break;
            case VERDE:
                colorMesa.setBackground(java.awt.Color.GREEN);
                break;
            case AMARILLO:
                colorMesa.setBackground(java.awt.Color.YELLOW);
                break;
            default:
                colorMesa.setBackground(java.awt.Color.BLACK);
               
        }
    }
    
    /**
     * Actualiza la imagen visual del mazo de cartas
     * carga y escala la imagen del mazo principal
     */
    public void actualizarMazo(){
        ImageIcon original=new ImageIcon(getClass().getResource("/cartas/deck.png"));
        Image escalada = original.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        mazoCartas.setIcon(new ImageIcon(escalada));
    }
    
    /**
     * Actualiza completamente de la interfaz gráfica.
     * Reorganiza:
     * Carta en mesa
     * Cartas de jugadores
     * Botones
     * Paneles y etiquetas
     */
    public void actualizarUI(){
        getContentPane().removeAll();
        getContentPane().setBackground(
                new Color(80,0,120)
        );
        // =========================
        // MEDIDAS
        // =========================
        int anchoVentana = getWidth();
        int altoVentana = getHeight();
        int centroX = anchoVentana / 2;
        nombreArriba.setBounds(
            centroX -40,
            145,
            100,
            20
        );
        nombreIzquierda.setBounds(
            20,
            120,
            100,
            20
        );
        nombreDerecha.setBounds(
            getWidth() - 140,
            120,
            100,
            20
        );
        nombreJugador.setBounds(
            centroX -40,
            getHeight() - 55,
            100,
            20
        );
        // =========================
        // CARTA CENTRAL
        // =========================
        cartaMesa.setBounds(
                centroX - 40,
                altoVentana / 2 - 120,
                80,
                120
        );
        colorMesa.setBounds(
            centroX -40,
            altoVentana / 2 -5,
            80,
            25
        );
        mazoCartas.setBounds(centroX-140,altoVentana/2-120,80,120);
        // =========================
        // BOTONES
        // =========================
        btnUNO.setBounds(
                centroX - 100,
                altoVentana / 2 + 20,
                80,
                30
        );
        btnRobar.setBounds(
                centroX,
                altoVentana / 2 + 20,
                120,
                30
        );
        // =========================
        // LOG
        // =========================
        scrollLog.setBounds(
                centroX - 150,
                altoVentana / 2 + 60,
                300,
                80
        );
        // =========================
        // AGREGAR
        // =========================
        add(cartaMesa);
        add(mazoCartas);
        add(btnUNO);
        add(btnRobar);
        add(scrollLog);
        add(colorMesa);
        add(nombreArriba);
        add(nombreIzquierda);
        add(nombreDerecha);
        add(nombreJugador);
        // =========================
        // ACTUALIZAR DATOS
        // =========================
        actualizarCartaMesa(juego.getPila().getCartaActual());
        actualizarMazo();
        actualizarColorMesa(juego.getPila().getCartaActual());
        mostrarJugadores(juego.getJugadores());
        mostrarMano(juego.getJugadores().get(0));
        repaint();
        revalidate();
    }
    
    /**
     * Agrega un mensaje al registro del juego
     * El mensaje se añade al JTextArea y dezplaza
     * automaticamente el scroll hacia abajo
     * @param mensaje Texto a mostrar en el log
     */
    public void agregarLog(String mensaje){
        log.append(mensaje + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }
    
    /**
     * Actualiza la imagen de la carta central
     * @param carta Carta actual colocada en mesa
     */
    public void actualizarCartaMesa(card carta){
        cartaMesa.setIcon(carta.getImagen()
        );
    }
    
    /**
     * Muestra las cartas de los jugadores CPU.
     * Posiciona visualmente las cartas de:
     * Jugador superior
     * Jugador izquierdo
     * Jugador derecho
     * @param jugadores Lista de jugadores del juego 
     */
    public void mostrarJugadores(ArrayList<player> jugadores){
        // ======================
        // ARRIBA
        // ======================
        int cantidadSuperior =jugadores.get(2).getMano().size();
        int totalSuperior =(cantidadSuperior - 1) * 30 + 80;
        int xSuperior =(getWidth() - totalSuperior) / 2;
        for(int i = 0;i < cantidadSuperior;i++){
            JButton carta = crearCartaBack();
            carta.setBounds(
                    xSuperior,
                    20,
                    80,
                    120
            );
            add(carta);
            xSuperior += 30;
        }
        // ======================
        // IZQUIERDA
        // ======================
        int yIzquierda = 150;
        for(int i = 0;i < jugadores.get(1).getMano().size();i++){
            JButton carta = crearCartaBackVertical();
            carta.setBounds(
                    20,
                    yIzquierda,
                    120,
                    80
            );
            add(carta);
            yIzquierda += 30;
        }
        // ======================
        // DERECHA
        // ======================
        int yDerecha = 150;
        for(int i = 0;i < jugadores.get(3).getMano().size();i++){
            JButton carta = crearCartaBackVertical();
            carta.setBounds(
                    getWidth() - 160,
                    yDerecha,
                    120,
                    80
            );
            add(carta);
            yDerecha += 30;
        }
    }
    
    /**
     * Muestra las cartas de ljugador principal
     * Genera botones interactivos para cada carta
     * permitiendo jugarlas durante el turno
     * @param jugador Jugador humano.
     */
    public void mostrarMano(player jugador){
        int cantidad =jugador.getMano().size();
        int anchoCarta = 80;
        int separacion = 60;
        int totalAncho =(cantidad - 1) * separacion+ anchoCarta;
        int x =(getWidth() - totalAncho) / 2;
        for(int i = 0;i < cantidad;i++){
            card c =jugador.getMano().getCarta(i);
            JButton cartaBtn =new JButton(c.getImagen());
            cartaBtn.setBounds(
                    x,
                    getHeight() - 180,
                    anchoCarta,
                    120
            );
            cartaBtn.setBorderPainted(false);
            cartaBtn.setContentAreaFilled(false);
            cartaBtn.setFocusPainted(false);
            int indice = i;
            cartaBtn.addActionListener(e -> {
                if(juego.getTurnManager().getTurnoActual() !=0 || turnoBots){
                    return;
                }
                card mesa =juego.getPila().getCartaActual();
                if(c.esJugableSobre(mesa)){
                    card jugada=jugador.getMano().jugarCarta(indice);
                    juego.getPila().ponerCarta(jugada);
                    //canbio de color
                    if(jugada.getTipo() == card.Tipo.COMODIN || jugada.getTipo() == card.Tipo.ROBA4){
                        card.Color nuevoColor=seleccionarColor();
                        jugada = new card(nuevoColor,jugada.getTipo());
                        juego.getPila().ponerCarta(jugada);
                    }
                    juego.setMensaje(jugador.getNombre()+ " juega "+ jugada);
                    agregarLog(juego.getMensaje());
                    //Uno
                    if(jugador.getMano().size()==1){
                        if(!dijoUNO){
                            agregarLog(jugador.getNombre()+ " no dijo UNO! Roba 2 cartas");
                            jugador.robarCarta(juego.getDeck());
                            jugador.robarCarta(juego.getDeck());
                        }else{
                            agregarLog(jugador.getNombre()+ " dice UNO!");
                        }
                        dijoUNO = false;
                    }
                    //GANADOR
                    if(jugador.getMano().estaVacia()){
                        actualizarUI();
                        mostrarGanador(jugador.getNombre());
                        return;
                    }
                    //Siguiente turno
                    juego.getRuleEngine().aplicarEfecto(
                         jugada,
                         jugador,
                         juego.getJugadores(),
                         juego.getTurnManager(),
                         juego.getDeck(),
                         juego.getPila(),
                         juego
                         );
                    if(jugada.getTipo() == card.Tipo.ROBA2 ||
                       jugada.getTipo() == card.Tipo.ROBA4 ||
                       jugada.getTipo() == card.Tipo.SALTO ||
                       jugada.getTipo() == card.Tipo.REVERSA){
                        agregarLog("->" + juego.getMensaje());
                    }
                    if(jugada.getTipo() == card.Tipo.NUMERO){
                           juego.getTurnManager().siguienteTurno();
                    }
                    actualizarUI();
                    //TURNO BOTS
                    javax.swing.Timer timer=new javax.swing.Timer(1000,ev -> ejecutarTurnoBots());
                    timer.setRepeats(false);
                    timer.start();
                }else{
                    agregarLog("Carta no jugable");
                }
            });
            add(cartaBtn);
            x += separacion;
        }
    }
    
    /**
     * Crea una carta trasera horizontalmente
     * Se utiliza para representar cartas ocultas
     * de jugadores CPU
     * @return Botón con imagen de carta trasera
     */
    private JButton crearCartaBack(){
        ImageIcon original = new ImageIcon(getClass().getResource("/cartas/blank.png"));
        Image escalada =original.getImage().getScaledInstance(
                        80,
                        120,
                        Image.SCALE_SMOOTH
                );
        ImageIcon back =new ImageIcon(escalada);
        JButton carta =new JButton(back);
        carta.setBorderPainted(false);
        carta.setContentAreaFilled(false);
        carta.setFocusPainted(false);
        return carta;
    }
    
    /**
     * Crea una carta trasera vertical
     * Se utiliza para mostrar cartas laterales
     * de los jugadores CPU
     * @return Botón con imagen vertical de carta trasera
     */
    private JButton crearCartaBackVertical(){
        ImageIcon original = new ImageIcon(getClass().getResource("/cartas/blank_1.png"));
        Image escalada =original.getImage().getScaledInstance(
                        120,
                        80,
                        Image.SCALE_SMOOTH
                );
        ImageIcon back =new ImageIcon(escalada);
        JButton carta =new JButton(back);
        carta.setBorderPainted(false);
        carta.setContentAreaFilled(false);
        carta.setFocusPainted(false);
        return carta;
    }
    
    /**
     * Ejecuta automaticamente los turno de los bots
     * El bot:
     * Busca cartas jugables
     * Aplica efectos especiales
     * Roba cartas si no puede jugar
     * Actualiza la interfaz
     */
    public void ejecutarTurnoBots(){
        if(juego.getTurnManager().getTurnoActual() == 0){
            turnoBots = false;
            actualizarUI();
            return;
        }
        player bot =juego.getJugadores().get(juego.getTurnManager().getTurnoActual());
        card mesa =juego.getPila().getCartaActual();
        boolean jugo = false;
        // =========================
        // BUSCAR CARTA JUGABLE
        // =========================
        for(int i = 0; i < bot.getMano().size(); i++){
            card c =bot.getMano().getCarta(i);
            if(c.esJugableSobre(mesa)){
                card jugada =bot.getMano().jugarCarta(i);
                // =====================
                // COMODIN / ROBA4
                // =====================
                if(jugada.getTipo() == card.Tipo.COMODIN ||
                   jugada.getTipo() == card.Tipo.ROBA4){
                    int random =(int)(Math.random() * 4);
                    card.Color nuevoColor;
                    switch(random){
                        case 0:
                            nuevoColor = card.Color.ROJO;
                            break;
                        case 1:
                            nuevoColor = card.Color.AZUL;
                            break;
                        case 2:
                            nuevoColor = card.Color.VERDE;
                            break;
                       default:
                            nuevoColor = card.Color.AMARILLO;
                    }
                    jugada=new card(nuevoColor,jugada.getTipo());
                    agregarLog(bot.getNombre()+ " cambia color a "+ nuevoColor);
                }
                juego.getPila().ponerCarta(jugada);
                agregarLog(bot.getNombre()+ " juega "+ jugada);
                // =====================
                // UNO
                // =====================
                if(bot.getMano().size() == 1){
                    agregarLog(bot.getNombre()+ " dice UNO!");
            }
                // =====================
                // GANADOR
                // =====================
                if(bot.getMano().estaVacia()){
                    turnoBots = false;
                    actualizarUI();
                    mostrarGanador(bot.getNombre());
                    return;
                }
                // =====================
                // EFECTOS
                // =====================
                juego.getRuleEngine().aplicarEfecto(
                    jugada,
                    bot,
                    juego.getJugadores(),
                    juego.getTurnManager(),
                    juego.getDeck(),
                    juego.getPila(),
                    juego
                );
                if(jugada.getTipo() == card.Tipo.ROBA2 ||
                   jugada.getTipo() == card.Tipo.ROBA4 ||
                   jugada.getTipo() == card.Tipo.SALTO ||     
                   jugada.getTipo() == card.Tipo.REVERSA){
                    agregarLog("->" + juego.getMensaje());
                }
                
                if(jugada.getTipo() == card.Tipo.NUMERO){
                    juego.getTurnManager().siguienteTurno();
                }
                jugo = true;
                break;
            }
        }
        // =========================
        // SI NO PUDO JUGAR
        // =========================
        if(!jugo){
            bot.robarCarta(juego.getDeck());
            agregarLog(bot.getNombre()+ " roba carta");
            juego.getTurnManager().siguienteTurno();
        }
         actualizarUI();
        // =========================
        // SIGUIENTE BOT
        // =========================
        int tiempoBot=1500 + (int)(Math.random()*3000);
        javax.swing.Timer timer =new javax.swing.Timer(tiempoBot,e -> ejecutarTurnoBots());
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Muestra un diálogo para seleccionar color
     * Se utiliza cuando se juega:
     * comodín
     * Roba 4
     * @return Color seleccionado por el jugador 
     */
    public card.Color seleccionarColor(){
        javax.swing.JDialog dialog= new javax.swing.JDialog(this,"Sleccionar un color",true);
        dialog.setSize(300,150);
        dialog.setLayout(new java.awt.GridLayout(1,4,10,10));
        dialog.setLocationRelativeTo(this);
        final card.Color[] colorSeleccionado=new card.Color[1];
        JButton rojo = new JButton();
        rojo.setBackground(Color.RED);
        rojo.addActionListener(e ->{
           colorSeleccionado[0]=card.Color.ROJO; 
            dialog.dispose();
        });
        JButton azul = new JButton();
        azul.setBackground(Color.BLUE);
        azul.addActionListener(e ->{
           colorSeleccionado[0]=card.Color.AZUL; 
            dialog.dispose();
        });
        JButton verde = new JButton();
        verde.setBackground(Color.GREEN);
        verde.addActionListener(e ->{
           colorSeleccionado[0]=card.Color.VERDE; 
            dialog.dispose();
        });
        JButton amarillo = new JButton();
        amarillo.setBackground(Color.YELLOW);
        amarillo.addActionListener(e ->{
           colorSeleccionado[0]=card.Color.AMARILLO; 
            dialog.dispose();
        });
        dialog.add(rojo);
        dialog.add(azul);
        dialog.add(verde);
        dialog.add(amarillo);
        dialog.setVisible(true);
        if(colorSeleccionado[0]==null){
            return card.Color.ROJO;
        }
        return colorSeleccionado[0];
    }
    
    /**
     * Muestra el mensaje de ganador del juego
     * También habilita el botón de reinicio 
     * para comenzar una nueva partida
     * @param nombre Nombre del jugador ganador
     */
    public void mostrarGanador(String nombre){
        JOptionPane.showMessageDialog(
                this,
                "Ganador: " + nombre
        );
        btnReiniciar.setBounds(getWidth()/2-60,getHeight()/2 +120,120,40);
        add(btnReiniciar);
        repaint();
        revalidate();
    }
    
    //Para pruebas
    public game getJuego(){
        return juego;
    }
    public JTextArea getLog(){
        return log;
    }
    public JButton getBtnUno(){
        return btnUNO;
    }
    public JLabel getColorMesa(){
        return colorMesa;
    }
    public JButton getBtnRobar(){
        return btnRobar;
    }
    public ArrayList<player> getJugadores(){
        return juego.getJugadores(); 
    }
    
    
}