package contarpatrones;


import java.io.*;
import java.lang.System.Logger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContarPatrones {
    private conteo conteo = new conteo();

    public void contar(String archivo, String patron, int numThreads,JTextArea txt,JTextArea txt2,JTextField txt3) {
        long START = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads);
        forkJoinPool.invoke(new PatronesTask(archivo, patron, -1,false,numThreads,txt));
        conteo.END = (System.nanoTime()-START);
        txt2.append("\n Patrones Repetidos " +conteo.count.get());
        txt2.setCaretPosition(txt2.getDocument().getLength());
        

           double segundos = conteo.END / 1e9; // Convierte nanosegundos a segundos
           String resultado = String.format("%.10f seconds", segundos); // Formatea en segundos con 6 decimales
          txt3.setText("\n" + resultado);
        conteo.count.set(0);
        
        
    }

    private class PatronesTask extends RecursiveAction {
        private String archivo;
        private String patron;
        private int id;
        private boolean created;
        private int numThreads;
        private JTextArea txt;

        public PatronesTask(String archivo, String patron,int id,boolean created,int numThreads,JTextArea txt) {
            this.archivo = archivo;
            this.patron = patron;
            this.created=created;
            this.id=id;
            this.numThreads=numThreads;
            this.txt=txt;
            
        }

        @Override
        protected void compute() {
            if(!created){
               
                List<PatronesTask> listPatronesTask = new ArrayList<>();
                for(int i =0;i<numThreads;i++){
                    listPatronesTask.add(new PatronesTask(archivo,patron,i,true,numThreads,txt));
                }
                this.created=true;
                invokeAll(listPatronesTask);
            }
               if (id != -1) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            int lineNumber = 0;
            Pattern pattern = Pattern.compile(patron);
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % numThreads == id) {
                  //  System.out.println("Hilo id: "+id+" Buscando en Linea: "+ line);
                    // txt.setCaretPosition(txt.getDocument().getLength());
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        // System.out.println("Hilo id: "+id+" Encontro patron en linea: "+ line);
                          //txt.setCaretPosition(txt.getDocument().getLength());
                        conteo.count.incrementAndGet();
                      
                    }
                }
                lineNumber++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        
        }
    }

    public static void main(String[] args) {
        
    }
}
