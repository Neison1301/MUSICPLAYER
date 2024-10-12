package ClasesDAO;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class CancionDAO {

    private boolean detenido = false;
    private lista list;
    private Zplayer player;
    private short x = 0;
    private nodo actual = null;
    private JTextField nombre_can;
    private JSlider jSlider1;
    private JTextField cancionactual;
    private JButton play;
    private JLabel portadaLabel, portadaLabel1, portadaLabel2, portadaLabel3, portadaLabel4, portadaLabel5;

    private LinkedList<ImageIcon> portadas; // Para almacenar las últimas 5 portadas reproducidas
    private JLabel[] etiquetasPortada;

    public CancionDAO(lista list, Zplayer player, nodo actual, short x,
            JTextField nombre_can, JSlider jSlider1, JTextField cancionactual,
            JButton play, JLabel portadaLabel1, JLabel portadaLabel2,
            JLabel portadaLabel3, JLabel portadaLabel4, JLabel portadaLabel5, JLabel portadaLabel) {
        this.list = list;
        this.x = x;
        this.player = player;
        this.actual = actual;
        this.nombre_can = nombre_can;
        this.jSlider1 = jSlider1;
        this.cancionactual = cancionactual;
        this.play = play;
        this.portadaLabel1 = portadaLabel1;
        this.portadaLabel2 = portadaLabel2;
        this.portadaLabel3 = portadaLabel3;
        this.portadaLabel4 = portadaLabel4;
        this.portadaLabel5 = portadaLabel5;
        this.portadaLabel = portadaLabel;
        portadas = new LinkedList<>();
        etiquetasPortada = new JLabel[]{portadaLabel1, portadaLabel2, portadaLabel3, portadaLabel4, portadaLabel5};

    }

    public void reproducir() {
        if (list.IsEmpety()) {
            JOptionPane.showMessageDialog(null, "No hay canciones", "Alerta", 1);
            return;
        }

        if (actual == null) {
            actual = list.first;
        }

        try {
            switch (x) {
                case 0:
                    player.control.open(new URL("file:///" + actual.direccion));
                    player.control.play();
                    nombre_can.setText(actual.nombre);
                    cancionactual.setText(actual.nombre);

                    jSlider1.setEnabled(true);
                    x = 1;
                    play.setIcon(new ImageIcon(getClass().getResource("/imagenes/pausa.png")));
                    System.out.println("Estado actual de x: " + x);
                    System.out.println("Cambiando a estado: REPRODUCIENDO");

                     if (actual.imagenPortada != null && !actual.imagenPortada.isEmpty()) {
                        ImageIcon portadaIcon = new ImageIcon(actual.imagenPortada);

                        int labelWidth = portadaLabel.getWidth();
                        int labelHeight = portadaLabel.getHeight();

                        Image scaledImage = portadaIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);

                        portadaLabel.setIcon(scaledIcon);
                    } else {
                        portadaLabel.setIcon(null);  
                    }
                    
                    if (actual.imagenPortada != null && !actual.imagenPortada.isEmpty()) {
                        nuevaCancionReproducida(actual.imagenPortada); 
                    } else {
                        portadaLabel.setIcon(null); 
                    }
                    break;
                case 1:
                    player.control.pause();
                    x = 2;
                    play.setIcon(new ImageIcon(getClass().getResource("/imagenes/play.png")));
                    System.out.println("Estado actual de x (después de pausar): " + x);
                    System.out.println("Cambiando a estado: PAUSADO");
                    break;
                case 2:
                    player.control.resume();
                    x = 1;
                    play.setIcon(new ImageIcon(getClass().getResource("/imagenes/pausa.png")));
                    System.out.println("Estado actual de x (después de reanudar): " + x);
                    System.out.println("Cambiando a estado: REPRODUCIENDO");
                    break;
            }
        } catch (BasicPlayerException | MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error al manejar la canción.", "Alerta", 1);
            x = 0;
        }
    }

    public void siguiente(JComboBox<String> tipo_reproduccion) {
        if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0: // Siguiente canción
                if (actual.siguiente == null) {
                    return; // No hay siguiente canción
                }
                actual = actual.siguiente;
                break;

            case 1: // Canción anterior
                if (actual.anterior == null) {
                    return; // No hay canción anterior
                }
                actual = actual.anterior;
                break;

            default: // Aleatorio
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        reproducir();
    }

    public void anterior(JComboBox<String> tipo_reproduccion) {
        if (actual == null) {
            return;
        }

        switch (tipo_reproduccion.getSelectedIndex()) {
            case 0:
                if (actual.anterior == null) {
                    return;
                }
                actual = actual.anterior;
                break;

            case 1:
                if (actual.siguiente == null) {
                    return;
                }
                actual = actual.siguiente;
                break;

            default:
                int index = (int) (Math.random() * list.tam);
                actual = list.get_cancion(index);
                break;
        }

        x = 0;
        reproducir();
    }

    public void nuevaCancionReproducida(String rutaPortadaNuevaCancion) {
        // Cargar la nueva imagen de portada
        ImageIcon portadaNueva = new ImageIcon(rutaPortadaNuevaCancion);

        // Verificar que la imagen sea válida antes de continuar
        if (portadaNueva.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            // Si ya hay 5 portadas, eliminamos la última (la que estaba más al fondo)
            if (portadas.size() == 5) {
                portadas.removeLast();
            }

            // Agregamos la nueva portada al inicio de la lista
            portadas.addFirst(portadaNueva);

            // Actualizamos las etiquetas con las portadas actuales
            actualizarEtiquetas();
        }
    }

    // Método para actualizar los JLabel con las portadas en la lista
    private void actualizarEtiquetas() {
        for (int i = 0; i < etiquetasPortada.length; i++) {
            if (i < portadas.size()) {
                // Redimensionar la imagen al tamaño del JLabel
                ImageIcon portadaIcon = portadas.get(i);
                Image imagenEscalada = portadaIcon.getImage().getScaledInstance(
                        etiquetasPortada[i].getWidth(), etiquetasPortada[i].getHeight(), Image.SCALE_SMOOTH);
                ImageIcon imagenEscaladaIcon = new ImageIcon(imagenEscalada);

                // Asignar la imagen redimensionada al JLabel
                etiquetasPortada[i].setIcon(imagenEscaladaIcon);
            } else {
                // Si no hay suficientes portadas, dejamos el JLabel vacío
                etiquetasPortada[i].setIcon(null);
            }
        }
    }

}
