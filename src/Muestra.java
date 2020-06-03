import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
public class Muestra extends JDialog{
    JLabel datos;
    String records = "<html><body>";
    public Muestra(Graphics gra){
        super(gra);
        setLayout(new FlowLayout());


        datos = new JLabel();
        add(datos);

        File f = new File(".records.txt");
        try(Scanner sc = new Scanner(f)){
            while(sc.hasNext()){
                records = records + sc.nextLine()+"<br>";
            }
            records = records + "</body></html>";
        }catch(FileNotFoundException err){
            System.err.println("El archivo aun no existe");
            this.dispose();
        }

        datos.setText(records);
    }
}