/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contarpatrones;

import contarpatronesVisual.Visual;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author alanm
 */
public class ContarPatronesSecuencial {
    conteo conteo = new conteo();
    
    public ContarPatronesSecuencial(){
        
    }
    public void calcular(String archivo , String patron , JTextArea txt) throws FileNotFoundException, IOException{
       
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            int lineNumber = 0;
            Pattern pattern = Pattern.compile(patron);
            String line;
            
            while ((line = reader.readLine()) != null) {
                
              // txt.append("Buscando en Linea: "+ line);
             //txt.setCaretPosition(txt.getDocument().getLength());
             // System.out.println("Buscando en linea: "+ line);
             
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                       //  txt.append("Encontro patron en linea: "+ line);
                       // txt.setCaretPosition(txt.getDocument().getLength());
                      //  System.out.println("Encontro patron en linea: "+ line);
                        conteo.count.incrementAndGet();
                      
                    }
                
                lineNumber++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
            
      
    }
    
    public  void contar(String archivo,String patron,JTextArea txt,JTextArea txt2,JTextField txt3) throws IOException{
            long START = System.nanoTime();
            calcular(archivo,patron,txt);
            conteo.END = (System.nanoTime()-START);
            txt2.append("\n Patrones Repetidos " + conteo.count.get());
            txt2.setCaretPosition(txt2.getDocument().getLength());
            
            
            
           double segundos = conteo.END / 1e9; // Convierte nanosegundos a segundos
           String resultado = String.format("%.10f seconds", segundos); // Formatea en segundos con 6 decimales
          txt3.setText("\n" + resultado);
            conteo.count.set(0);
            
        
    }
    
    /*public static void main(String[] args) throws IOException {
        System.out.println(12%4);
    }*/
    
}
