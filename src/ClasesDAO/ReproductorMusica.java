package ClasesDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReproductorMusica extends JFrame {
    private boolean esFavorito = false; // Estado inicial, no favorito
    private JButton botonFavorito;

    public ReproductorMusica() {
        // Crear ventana
        setTitle("Reproductor de Música");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Crear botón con el ícono de corazón vacío (no favorito)
        botonFavorito = new JButton(new ImageIcon("corazon_vacio.png"));
        botonFavorito.setBounds(100, 100, 50, 50); // Posición y tamaño del botón
        botonFavorito.setBorderPainted(false);
        botonFavorito.setContentAreaFilled(false);

        // Agregar acción al botón
        botonFavorito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Alternar estado entre favorito y no favorito
                esFavorito = !esFavorito;

                // Cambiar ícono según el estado
                if (esFavorito) {
                    botonFavorito.setIcon(new ImageIcon("corazon_lleno.png"));
                } else {
                    botonFavorito.setIcon(new ImageIcon("corazon_vacio.png"));
                }
            }
        });

        // Agregar botón a la ventana
        add(botonFavorito);

        // Hacer visible la ventana
        setVisible(true);
    }

    public static void main(String[] args) {
        new ReproductorMusica();
    }
}
