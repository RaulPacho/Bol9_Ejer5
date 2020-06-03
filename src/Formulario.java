import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
public class Formulario extends JDialog implements ActionListener {
    JLabel explicacion;
    JTextField nombre;
    JButton aceptar, cancelar;
    Graphics objGra;

    public Formulario(Graphics gra){
        super(gra);
        setLayout(new FlowLayout());
        setTitle("Guardar numeros");
        objGra = gra;
        explicacion = new JLabel("Introduce tu nombre");
        add(explicacion);

        nombre = new JTextField(10);
        add(nombre);

        aceptar = new JButton("Guardar");
        aceptar.addActionListener(this);
        add(aceptar);
        cancelar = new JButton ("Cancelar");
        cancelar.addActionListener(this);
        add(cancelar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == aceptar){
            File f = new File(".records.txt");
            try(PrintWriter pw= new PrintWriter(new FileWriter(f, true))){
                pw.println("El jodido " + nombre.getText() + "! numeros acertados: " + objGra.contNumeros);
            }catch(IOException err){
                System.err.println("No se pudo guardar el dato");
            }finally{
                this.dispose();
            }
        }else{
            this.dispose();
        }

    }
    
}