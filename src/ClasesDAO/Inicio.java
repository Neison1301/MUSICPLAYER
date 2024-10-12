package ClasesDAO;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Inicio extends javax.swing.JFrame {

    private lista list = new lista();
    private nodo actual = null;
    Zplayer player = new Zplayer();
    private short x = 0;
    private DefaultListModel<String> lista_modelo = new DefaultListModel<>();
    private String ultimaLista = "vacio";
    private boolean cambios = false;
    protected boolean detenido = false;
    public boolean esFavorito;
    public boolean esAleatorio = false;
    public boolean esRepetir = false;
    private boolean modoAleatorio = false;
    private BibliotecaMusical bibliotecaMusical;
    private CancionDAO cancionDAO;

    public Inicio() {
        setTitle("Reproductor de musica mp3");
        setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono.png"));
        setIconImage(icon);
        initComponents();
        setLocationRelativeTo(null);
        nombre_can.setEditable(false);
        cancionactual.setEditable(false);

        bibliotecaMusical = new BibliotecaMusical(list, lista_modelo);
        lista_can.setModel(lista_modelo);
        jSlider1.setEnabled(false);
        cancionDAO = new CancionDAO(list, player, actual, x, nombre_can, jSlider1, cancionactual, play, portadaLabel111, portadaLabel12,
                portadaLabel13, portadaLabel14, portadaLabel15, portadaimagen);

        lista_can.setFixedCellHeight(40);

        lista_can.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList lista = (JList) evt.getSource();
                if (evt.getClickCount() == 1) {
                    int index = lista.locationToIndex(evt.getPoint());
                    if (index != -1) {
                        actual = list.get_cancion(index);
                        x = 0;
                        cancionDAO.reproducir();
                    }
                }
            }
        });
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        /* play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancionDAO.manejarReproduccion();
            }
        });*/
        try {
            BufferedReader tec = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\config"));
            String aux = tec.readLine();
            if (aux.equals("Si")) {
                aux = tec.readLine();
                if (!aux.equals("vacio")) {
                    cargarLista(aux);
                }
            } else {
                cargarListaInicio.setSelected(false);
            }
        } catch (Exception e) {
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                if (!list.IsEmpety() && cambios) {
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                    if (opcion == JOptionPane.YES_OPTION) {
                        if (ultimaLista.equals("vacio")) {
                            ultimaLista = crearArchivoLista();
                        }
                        if (ultimaLista == null) {
                            ultimaLista = "vacio";
                        } else {
                            guardarLista(ultimaLista);
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(
                            new FileWriter(System.getProperty("user.dir") + "\\config"));
                    if (cargarListaInicio.isSelected()) {
                        bw.write("Si\r\n");
                        bw.write(ultimaLista + "\r\n");
                    } else {
                        bw.write("No\r\n");
                    }
                    bw.close();
                } catch (Exception e) {
                }
                System.exit(0);
            }
        });
        player = new Zplayer(this);
    }

    public void cargarLista(String ruta) {
        try {
            FileInputStream fis = new FileInputStream(new File(ruta));
            BufferedReader tec = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String input[];
            tec.readLine();

            while (tec.ready()) {
                input = tec.readLine().split("<");
                System.out.println(input[0] + " , " + input[1]);
                list.insertar(input[0], input[1]);
                lista_modelo.addElement(input[0]);
            }
            ultimaLista = ruta;
            cambios = false;
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error\nal cargar la lista!!!", "alerta", 1);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error!!!", "alerta", 1);
        }
        lista_can.setModel(lista_modelo);
    }

    public void guardarLista(String dir) {
        try {
            BufferedWriter tec = new BufferedWriter(new FileWriter(dir));
            tec.write("\r\n");

            nodo aux = list.first;
            while (aux != null) {
                tec.append(aux.nombre + "<" + aux.direccion + "\r\n");
                aux = aux.siguiente;
            }

            tec.close();
            cambios = false;
        } catch (Exception e) {
        }
    }

    public String crearArchivoLista() {
        String n = JOptionPane.showInputDialog("digite el nombre de la lista");
        if (n == null || n.isEmpty()) {
            return null;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = chooser.showOpenDialog(this);
        File ruta;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            ruta = chooser.getSelectedFile();
        } else {
            return null;
        }
        File save = new File(ruta.getAbsolutePath() + "\\" + n + ".lis");
        if (save.exists()) {
            save.delete();
        }
        return save.getAbsolutePath();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelRound2 = new ClasesDAO.PanelRound();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panelRound1 = new ClasesDAO.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        cancionactual = new javax.swing.JTextField();
        btnfavorito1 = new javax.swing.JButton();
        tipo_reproduccion2 = new javax.swing.JButton();
        btnrepetir = new javax.swing.JButton();
        play = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        anterior = new javax.swing.JButton();
        siguiente = new javax.swing.JButton();
        portadaimagen = new javax.swing.JLabel();
        panelRound5 = new ClasesDAO.PanelRound();
        jLabel9 = new javax.swing.JLabel();
        btnagregar = new javax.swing.JButton();
        tipo_reproduccion = new javax.swing.JComboBox<>();
        panelRound6 = new ClasesDAO.PanelRound();
        lista_can = new javax.swing.JList<>();
        panelRound7 = new ClasesDAO.PanelRound();
        btnfavorito2 = new javax.swing.JButton();
        btnfavorito3 = new javax.swing.JButton();
        btnfavorito4 = new javax.swing.JButton();
        panelRound11 = new ClasesDAO.PanelRound();
        portadaLabel12 = new javax.swing.JLabel();
        panelRound19 = new ClasesDAO.PanelRound();
        portadaLabel13 = new javax.swing.JLabel();
        panelRound23 = new ClasesDAO.PanelRound();
        portadaLabel14 = new javax.swing.JLabel();
        panelRound22 = new ClasesDAO.PanelRound();
        portadaLabel15 = new javax.swing.JLabel();
        nombre_can = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        imagenhome = new javax.swing.JLabel();
        portadaLabel11 = new ClasesDAO.PanelRound();
        portadaLabel111 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        cargarListaInicio = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 531, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.setBackground(new java.awt.Color(30, 30, 30));
        panelRound2.setRoundBottomLeft(15);
        panelRound2.setRoundBottomRight(15);
        panelRound2.setRoundTopLeft(15);
        panelRound2.setRoundTopRight(15);

        jLabel10.setFont(new java.awt.Font("Cambria Math", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("MusicPlayer");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icon.png"))); // NOI18N

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(301, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 220, 340));

        panelRound1.setBackground(new java.awt.Color(30, 30, 30));
        panelRound1.setToolTipText("");
        panelRound1.setRoundBottomLeft(15);
        panelRound1.setRoundBottomRight(15);
        panelRound1.setRoundTopLeft(15);
        panelRound1.setRoundTopRight(15);

        jLabel5.setBackground(new java.awt.Color(30, 30, 30));

        cancionactual.setBackground(new java.awt.Color(30, 30, 30));
        cancionactual.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        cancionactual.setForeground(new java.awt.Color(255, 255, 255));
        cancionactual.setBorder(null);
        cancionactual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancionactualActionPerformed(evt);
            }
        });

        btnfavorito1.setBackground(new java.awt.Color(30, 30, 30));
        btnfavorito1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favoritovacio.png"))); // NOI18N
        btnfavorito1.setToolTipText("");
        btnfavorito1.setBorder(null);
        btnfavorito1.setBorderPainted(false);
        btnfavorito1.setContentAreaFilled(false);
        btnfavorito1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfavorito1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfavorito1ActionPerformed(evt);
            }
        });

        tipo_reproduccion2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/aleatorio.png"))); // NOI18N
        tipo_reproduccion2.setBorder(null);
        tipo_reproduccion2.setBorderPainted(false);
        tipo_reproduccion2.setContentAreaFilled(false);
        tipo_reproduccion2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tipo_reproduccion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipo_reproduccion2ActionPerformed(evt);
            }
        });

        btnrepetir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/repetir.png"))); // NOI18N
        btnrepetir.setBorder(null);
        btnrepetir.setBorderPainted(false);
        btnrepetir.setContentAreaFilled(false);
        btnrepetir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnrepetir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrepetirActionPerformed(evt);
            }
        });

        play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/play.png"))); // NOI18N
        play.setBorder(null);
        play.setBorderPainted(false);
        play.setContentAreaFilled(false);
        play.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playActionPerformed(evt);
            }
        });

        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        anterior.setBackground(new java.awt.Color(30, 30, 30));
        anterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anterior.png"))); // NOI18N
        anterior.setBorder(null);
        anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anteriorActionPerformed(evt);
            }
        });

        siguiente.setBackground(new java.awt.Color(30, 30, 30));
        siguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/siguiente.png"))); // NOI18N
        siguiente.setBorder(null);
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(portadaimagen, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cancionactual, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnfavorito1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                .addComponent(tipo_reproduccion2)
                .addGap(61, 61, 61)
                .addComponent(anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(play)
                .addGap(12, 12, 12)
                .addComponent(siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(63, 63, 63)
                .addComponent(btnrepetir)
                .addGap(123, 123, 123)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(play))
                                    .addComponent(tipo_reproduccion2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnrepetir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panelRound1Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(btnfavorito1))
                                    .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cancionactual, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(portadaimagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 612, 1140, 70));

        panelRound5.setBackground(new java.awt.Color(30, 30, 30));
        panelRound5.setRoundBottomLeft(15);
        panelRound5.setRoundBottomRight(15);
        panelRound5.setRoundTopLeft(15);
        panelRound5.setRoundTopRight(15);

        jLabel9.setFont(new java.awt.Font("Cambria Math", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tus Generos musicales");

        btnagregar.setText("agregarcancion");
        btnagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnagregarActionPerformed(evt);
            }
        });

        tipo_reproduccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "normal", "inversa", "aleatoria" }));

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addGroup(panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnagregar, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(tipo_reproduccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addGap(29, 29, 29)
                .addComponent(btnagregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(tipo_reproduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jPanel1.add(panelRound5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 397, 220, 190));

        panelRound6.setBackground(new java.awt.Color(30, 30, 30));
        panelRound6.setRoundBottomLeft(15);
        panelRound6.setRoundBottomRight(15);
        panelRound6.setRoundTopLeft(15);
        panelRound6.setRoundTopRight(15);

        lista_can.setBackground(new java.awt.Color(30, 30, 30));
        lista_can.setBorder(null);
        lista_can.setForeground(new java.awt.Color(255, 255, 255));
        lista_can.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lista_can.setPreferredSize(new java.awt.Dimension(100, 400));

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(lista_can, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(lista_can, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 397, 440, 190));

        panelRound7.setBackground(new java.awt.Color(30, 30, 30));
        panelRound7.setRoundBottomLeft(15);
        panelRound7.setRoundBottomRight(15);
        panelRound7.setRoundTopLeft(15);
        panelRound7.setRoundTopRight(15);

        btnfavorito2.setBackground(new java.awt.Color(30, 30, 30));
        btnfavorito2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favoritovacio.png"))); // NOI18N
        btnfavorito2.setToolTipText("");
        btnfavorito2.setBorder(null);
        btnfavorito2.setBorderPainted(false);
        btnfavorito2.setContentAreaFilled(false);
        btnfavorito2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfavorito2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfavorito2ActionPerformed(evt);
            }
        });

        btnfavorito3.setBackground(new java.awt.Color(30, 30, 30));
        btnfavorito3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favoritovacio.png"))); // NOI18N
        btnfavorito3.setToolTipText("");
        btnfavorito3.setBorder(null);
        btnfavorito3.setBorderPainted(false);
        btnfavorito3.setContentAreaFilled(false);
        btnfavorito3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfavorito3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfavorito3ActionPerformed(evt);
            }
        });

        btnfavorito4.setBackground(new java.awt.Color(30, 30, 30));
        btnfavorito4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/favoritovacio.png"))); // NOI18N
        btnfavorito4.setToolTipText("");
        btnfavorito4.setBorder(null);
        btnfavorito4.setBorderPainted(false);
        btnfavorito4.setContentAreaFilled(false);
        btnfavorito4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnfavorito4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfavorito4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound7Layout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addGroup(panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnfavorito4)
                    .addComponent(btnfavorito3)
                    .addComponent(btnfavorito2))
                .addGap(124, 124, 124))
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnfavorito2)
                .addGap(34, 34, 34)
                .addComponent(btnfavorito3)
                .addGap(40, 40, 40)
                .addComponent(btnfavorito4)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel1.add(panelRound7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 397, 390, 190));

        panelRound11.setBackground(new java.awt.Color(255, 255, 255));
        panelRound11.setRoundBottomLeft(15);
        panelRound11.setRoundBottomRight(15);
        panelRound11.setRoundTopLeft(15);
        panelRound11.setRoundTopRight(15);

        javax.swing.GroupLayout panelRound11Layout = new javax.swing.GroupLayout(panelRound11);
        panelRound11.setLayout(panelRound11Layout);
        panelRound11Layout.setHorizontalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panelRound11Layout.setVerticalGroup(
            panelRound11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.add(panelRound11, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, -1, 100));

        panelRound19.setBackground(new java.awt.Color(255, 255, 255));
        panelRound19.setRoundBottomLeft(15);
        panelRound19.setRoundBottomRight(15);
        panelRound19.setRoundTopLeft(15);
        panelRound19.setRoundTopRight(15);

        javax.swing.GroupLayout panelRound19Layout = new javax.swing.GroupLayout(panelRound19);
        panelRound19.setLayout(panelRound19Layout);
        panelRound19Layout.setHorizontalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panelRound19Layout.setVerticalGroup(
            panelRound19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.add(panelRound19, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, -1, 100));

        panelRound23.setBackground(new java.awt.Color(255, 255, 255));
        panelRound23.setRoundBottomLeft(15);
        panelRound23.setRoundBottomRight(15);
        panelRound23.setRoundTopLeft(15);
        panelRound23.setRoundTopRight(15);

        javax.swing.GroupLayout panelRound23Layout = new javax.swing.GroupLayout(panelRound23);
        panelRound23.setLayout(panelRound23Layout);
        panelRound23Layout.setHorizontalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panelRound23Layout.setVerticalGroup(
            panelRound23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.add(panelRound23, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 250, -1, 100));

        panelRound22.setBackground(new java.awt.Color(255, 255, 255));
        panelRound22.setRoundBottomLeft(15);
        panelRound22.setRoundBottomRight(15);
        panelRound22.setRoundTopLeft(15);
        panelRound22.setRoundTopRight(15);

        javax.swing.GroupLayout panelRound22Layout = new javax.swing.GroupLayout(panelRound22);
        panelRound22.setLayout(panelRound22Layout);
        panelRound22Layout.setHorizontalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panelRound22Layout.setVerticalGroup(
            panelRound22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.add(panelRound22, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 250, -1, 100));

        nombre_can.setEditable(false);
        nombre_can.setBackground(new java.awt.Color(30, 30, 30));
        nombre_can.setForeground(new java.awt.Color(255, 255, 255));
        nombre_can.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        nombre_can.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombre_canActionPerformed(evt);
            }
        });
        jPanel1.add(nombre_can, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 410, -1));

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Escuchado recientemente");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, -1, -1));

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bienvenido");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        imagenhome.setBackground(new java.awt.Color(30, 30, 30));
        imagenhome.setForeground(new java.awt.Color(30, 30, 30));
        imagenhome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/home.png"))); // NOI18N
        jPanel1.add(imagenhome, new org.netbeans.lib.awtextra.AbsoluteConstraints(294, 40, 880, 170));

        portadaLabel11.setRoundBottomLeft(15);
        portadaLabel11.setRoundBottomRight(15);
        portadaLabel11.setRoundTopLeft(15);
        portadaLabel11.setRoundTopRight(15);

        javax.swing.GroupLayout portadaLabel11Layout = new javax.swing.GroupLayout(portadaLabel11);
        portadaLabel11.setLayout(portadaLabel11Layout);
        portadaLabel11Layout.setHorizontalGroup(
            portadaLabel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        portadaLabel11Layout.setVerticalGroup(
            portadaLabel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(portadaLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel1.add(portadaLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 160, 100));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 720));

        jMenu1.setText("File");

        cargarListaInicio.setSelected(true);
        cargarListaInicio.setText("Cargar ultima lista al abrir");
        cargarListaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarListaInicioActionPerformed(evt);
            }
        });
        jMenu1.add(cargarListaInicio);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnfavorito1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfavorito1ActionPerformed
        esFavorito = !esFavorito;

        if (esFavorito) {
            btnfavorito1.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritoselec.png")));
        } else {
            btnfavorito1.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritovacio.png")));
        }
    }//GEN-LAST:event_btnfavorito1ActionPerformed

    private void btnfavorito2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfavorito2ActionPerformed
        esFavorito = !esFavorito;

        if (esFavorito) {
            btnfavorito2.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritoselec.png")));
        } else {
            btnfavorito2.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritovacio.png")));
        }
    }//GEN-LAST:event_btnfavorito2ActionPerformed

    private void btnfavorito3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfavorito3ActionPerformed
        esFavorito = !esFavorito;
        if (esFavorito) {
            btnfavorito3.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritoselec.png")));
        } else {
            btnfavorito3.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritovacio.png")));
        }    }//GEN-LAST:event_btnfavorito3ActionPerformed

    private void btnfavorito4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfavorito4ActionPerformed
        esFavorito = !esFavorito;

        if (esFavorito) {
            btnfavorito4.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritoselec.png")));
        } else {
            btnfavorito4.setIcon(new ImageIcon(getClass().getResource("../imagenes/favoritovacio.png")));
        }
    }//GEN-LAST:event_btnfavorito4ActionPerformed

    private void tipo_reproduccion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipo_reproduccion2ActionPerformed
        esFavorito = !esFavorito;
        esRepetir = false;

        if (esFavorito) {
            tipo_reproduccion2.setIcon(new ImageIcon(getClass().getResource("../imagenes/aleatorioselect.png")));
            modoAleatorio = true;
        } else {
            tipo_reproduccion2.setIcon(new ImageIcon(getClass().getResource("../imagenes/aleatorio.png")));
            modoAleatorio = false;
        }
    }//GEN-LAST:event_tipo_reproduccion2ActionPerformed

    private void btnrepetirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrepetirActionPerformed
        esFavorito = !esFavorito;
        esAleatorio = false;
        if (esFavorito) {
            btnrepetir.setIcon(new ImageIcon(getClass().getResource("../imagenes/repetirselec.png")));
        } else {
            btnrepetir.setIcon(new ImageIcon(getClass().getResource("../imagenes/repetir.png")));
        }
    }//GEN-LAST:event_btnrepetirActionPerformed


    private void cancionactualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancionactualActionPerformed
    }//GEN-LAST:event_cancionactualActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
         // Verificar si player y su control están inicializados
   if (player == null || player.control == null) {
       Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, "El reproductor no está inicializado");
       return;
   }

   // Comprobar el rango de ganancia
   double gainValue = (double) jSlider1.getValue() / 100; // Ajustar el valor
   gainValue = Math.min(0.0, Math.max(-80.0, gainValue)); // Ajustar al rango permitido

   // Ajustar el volumen
   try {
       player.control.setGain(gainValue);
   } catch (BasicPlayerException ex) {
       Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, "Error al ajustar el volumen: ", ex);
       ex.printStackTrace(); // Imprimir la traza de la excepción
   }
    }//GEN-LAST:event_jSlider1StateChanged

    private void nombre_canActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombre_canActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombre_canActionPerformed

    private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
        cancionDAO.reproducir();
    }//GEN-LAST:event_playActionPerformed

    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed
        cancionDAO.anterior(tipo_reproduccion);
    }//GEN-LAST:event_anteriorActionPerformed

    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed
        cancionDAO.siguiente(tipo_reproduccion);
    }//GEN-LAST:event_siguienteActionPerformed

    private void cargarListaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarListaInicioActionPerformed
    }//GEN-LAST:event_cargarListaInicioActionPerformed

    private void btnagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnagregarActionPerformed
        bibliotecaMusical.agregarcancion();
    }//GEN-LAST:event_btnagregarActionPerformed
    public void eventoSiguiente() {
        siguienteActionPerformed(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anterior;
    private javax.swing.JButton btnagregar;
    private javax.swing.JButton btnfavorito1;
    private javax.swing.JButton btnfavorito2;
    private javax.swing.JButton btnfavorito3;
    private javax.swing.JButton btnfavorito4;
    private javax.swing.JButton btnrepetir;
    private javax.swing.JTextField cancionactual;
    private javax.swing.JCheckBoxMenuItem cargarListaInicio;
    private javax.swing.JLabel imagenhome;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JList<String> lista_can;
    private javax.swing.JTextField nombre_can;
    private ClasesDAO.PanelRound panelRound1;
    private ClasesDAO.PanelRound panelRound11;
    private ClasesDAO.PanelRound panelRound19;
    private ClasesDAO.PanelRound panelRound2;
    private ClasesDAO.PanelRound panelRound22;
    private ClasesDAO.PanelRound panelRound23;
    private ClasesDAO.PanelRound panelRound5;
    private ClasesDAO.PanelRound panelRound6;
    private ClasesDAO.PanelRound panelRound7;
    private javax.swing.JButton play;
    private ClasesDAO.PanelRound portadaLabel11;
    private javax.swing.JLabel portadaLabel111;
    private javax.swing.JLabel portadaLabel12;
    private javax.swing.JLabel portadaLabel13;
    private javax.swing.JLabel portadaLabel14;
    private javax.swing.JLabel portadaLabel15;
    private javax.swing.JLabel portadaimagen;
    private javax.swing.JButton siguiente;
    private javax.swing.JComboBox<String> tipo_reproduccion;
    private javax.swing.JButton tipo_reproduccion2;
    // End of variables declaration//GEN-END:variables
}
