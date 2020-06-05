import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graphics extends JFrame implements ActionListener, javax.swing.event.ChangeListener {

    ArrayList<JCheckBox> botones = new ArrayList();
    JButton loteria, otra;
    int[] numeros = new int[6];
    int[] seleccion = new int[6];
    Timer t;
    String titulo;
    int x, y;
    int contador = 0;
    int contTiempo = 0;
    ButtonGroup grupo;
    JLabel seleccionados, numSorteo;
    boolean jugado = false;
    public int contNumeros = 0;
    JMenuBar barra;
    JMenu menu;
    JMenuItem guardar, records;

    String lista = "";

    public Graphics() {
        super();
        setLayout(null);
        x = 25;
        y = 25;
        for (int i = 0; i < 49; i++) {
            botones.add(new JCheckBox("" + (i + 1)));
            botones.get(i).addActionListener(this);
            botones.get(i).setSize(50, 35);
            botones.get(i).setLocation(x, y);
            botones.get(i).addChangeListener(this);
            x += 75;
            if ((i + 1) % 7 == 0) {
                x = 25;
                y += 50;
            }
            add(botones.get(i));
        }

        loteria = new JButton("Juega!");
        loteria.setSize(loteria.getPreferredSize().width, loteria.getPreferredSize().height);
        loteria.setLocation(238, 50 * 8);
        loteria.addActionListener(this);
        add(loteria);
        loteria.setVisible(false);

        otra = new JButton("Otra?");
        otra.setSize(otra.getPreferredSize().width, otra.getPreferredSize().height);
        otra.setLocation(318, 50 * 8);
        otra.addActionListener(this);
        add(otra);
        otra.setVisible(false);

        seleccionados = new JLabel();
        seleccionados.setSize(400, 25);
        seleccionados.setLocation(25, 50 * 8 + 50);
        add(seleccionados);

        numSorteo = new JLabel();
        numSorteo.setSize(400, 25);
        numSorteo.setLocation(25, 50 * 8 + 75);
        add(numSorteo);

        barra = new JMenuBar();
        barra.setSize(600, 25);
        barra.setLocation(0, 0);

        menu = new JMenu("Menu");
        menu.setSize(100, 25);
        menu.setLocation(0, 0);

        guardar = new JMenuItem("Guardar Record");
        guardar.setSize(75, 25);
        guardar.addActionListener(this);
        records = new JMenuItem("Mostrar Records");
        records.setSize(75, 25);
        records.addActionListener(this);
        menu.add(guardar);
        menu.add(records);
        barra.add(menu);
        add(barra);

        t = new Timer(300, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String titulo = "Loteria";
        if (e.getSource() == t) {
            contTiempo++;
            if (contTiempo == 8) {
                contTiempo = 1;
            }
            this.setTitle(titulo.substring(0, contTiempo));
        } else {
            // System.err.println(e.getSource() == botones.get(0));
            // for (int i = 0; i < botones.size(); i++) {
            //     if (e.getSource() == botones.get(i)) {
            //         int res = 0;
            //         if (botones.get(i).isSelected()) {

            //             if (contador <= 6) {

            //                 System.err.println("sumo");
            //                 boolean meto = true;
            //                 for (int j = 0; j < 6; j++) {
            //                     if (seleccion[j] == 0 && meto) {
            //                         seleccion[j] = i + 1;
            //                         meto = false;
            //                     }
            //                 }
            //                 contador++;

            //             }

            //         } else {
            //             System.err.println("resto");

            //             contador--;
            //             for (int j = 0; j < 6; j++) {
            //                 if (seleccion[j] == i + 1) {
            //                     seleccion[j] = 0;
            //                 }
            //             }

            //         }

            //     }

            // }
            lista = "";
            for (int i = 0; i < 6; i++) {
                System.err.println(seleccion[i]);
                lista = lista + seleccion[i] + ",";
            }
            lista = lista.substring(0, lista.length() - 1);

            if (contador < 6) {
                for (int i = 1; i <= botones.size(); i++) {
                    botones.get(i - 1).setEnabled(true);
                }
            }

            if (contador == 6) {
                loteria.setVisible(true);
                seleccionados.setText("Numeros seleccionados: " + lista);
                for (int i = 1; i <= botones.size(); i++) {
                    boolean desmarca = false;
                    for (int j = 0; j < seleccion.length; j++) {
                        if (i == seleccion[j]) {
                            desmarca = true;
                        }
                    }
                    if (!desmarca) {
                        botones.get(i - 1).setEnabled(false);
                    }
                }

            } else {
                loteria.setVisible(false);
                seleccionados.setText("");
            }

            if (e.getSource() == loteria) {
                for (int i = 1; i <= botones.size(); i++) {
                    botones.get(i - 1).setEnabled(false);
                }
                lista = "";
                for (int i = 0; i < numeros.length; i++) {

                    int num = (int) (Math.random() * (49 - 1) + 1);
                    if (!estaYa(num)) {
                        numeros[i] = num;
                        lista = lista + num + ",";
                    } else {
                        i--;
                    }
                }
                numSorteo.setText("Numeros premiados: " + lista);
                for (int i = 0; i < numeros.length; i++) {
                    botones.get(numeros[i] - 1).setBackground(Color.blue);
                }
                for (int i = 0; i < seleccion.length; i++) {
                    botones.get(seleccion[i] - 1).setBackground(Color.red);
                    if (estaYa(seleccion[i])) {
                        botones.get(seleccion[i] - 1).setBackground(Color.green);
                        contNumeros++;
                    }
                }
                loteria.setEnabled(false);
                otra.setVisible(true);
            }

            if (e.getSource() == otra) {
                for (int i = 0; i < botones.size(); i++) {
                    botones.get(i).setBackground(new JButton().getBackground());
                    botones.get(i).setSelected(false);
                    botones.get(i).setEnabled(true);
                }
                for (int i = 0; i < seleccion.length; i++) {
                    seleccion[i] = 0;
                    numeros[i] = 0;
                }
                loteria.setEnabled(true);
                loteria.setVisible(false);
                otra.setVisible(false);
                numSorteo.setText("");
                seleccionados.setText("");
                contador = 0;
                contNumeros = 0;
            }

            if (e.getSource() == guardar) {
                Formulario form = new Formulario(this);
                form.setSize(250, 250);
                form.setVisible(true);
            }
            if (e.getSource() == records) {
                Muestra muestra = new Muestra(this);
                muestra.setSize(350, 450);
                muestra.setVisible(true);
            }
        }
    }

    public boolean estaYa(int num) {
        for (int i = 0; i < numeros.length; i++) {
            if (num == numeros[i]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int res = 0;
        for (int i = 0; i < botones.size(); i++) {
            if (botones.get(i).isSelected()) {

                if (contador <= 6) {

                    System.err.println("sumo");
                    boolean meto = true;
                    for (int j = 0; j < 6; j++) {
                        if (seleccion[j] == 0 && meto) {
                            seleccion[j] = i + 1;
                            meto = false;
                        }
                    }
                    contador++;

                }

            } else {
                System.err.println("resto");

                contador--;
                for (int j = 0; j < 6; j++) {
                    if (seleccion[j] == i + 1) {
                        seleccion[j] = 0;
                    }
                }

            }
        }

    }

}