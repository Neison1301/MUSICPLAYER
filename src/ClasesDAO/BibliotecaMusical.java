package ClasesDAO;

import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BibliotecaMusical {

    private lista list;
    private DefaultListModel<String> listaModelo;

    public BibliotecaMusical(lista list, DefaultListModel<String> listaModelo) {
        this.list = list;
        this.listaModelo = listaModelo;
    }

    public void agregarcancion() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos MP3", "mp3"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);

        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            boolean noMp3 = false, repetidos = false;

            for (File file : files) {
                String name = file.getName();
                if (name.length() < 4 || !name.substring(name.length() - 4).equalsIgnoreCase(".mp3")) {
                    noMp3 = true;
                    continue;
                }
                if (list.buscar(file.getName(), file.getPath())) {
                    repetidos = true;
                    continue;
                }

                JFileChooser imageChooser = new JFileChooser();
                imageChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
                int seleccionImagen = imageChooser.showOpenDialog(null);
                String rutaImagen = null;
                if (seleccionImagen == JFileChooser.APPROVE_OPTION) {
                    File imagenSeleccionada = imageChooser.getSelectedFile();
                    rutaImagen = imagenSeleccionada.getAbsolutePath();
                }
                list.insertar(file.getName(), file.getPath(),rutaImagen);
                listaModelo.addElement(file.getName());
            }

            if (noMp3) {
                JOptionPane.showMessageDialog(null, "Se encontraron archivos que no son MP3.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            if (repetidos) {
                JOptionPane.showMessageDialog(null, "Se encontraron archivos duplicados.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
