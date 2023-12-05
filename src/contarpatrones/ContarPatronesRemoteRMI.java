/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contarpatrones;

/**
 *
 * @author alanm
 */
import Interface.ContarPatronesClientRemote;
import Interface.ContarPatronesRemote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ContarPatronesRemoteRMI extends UnicastRemoteObject implements ContarPatronesRemote {
    private List<ContarPatronesClientRemote> clientes = new ArrayList<>();
    /*conteoParalelo conteo = new conteoParalelo();
    private int id=1;
    private int numClients=2;
    
*/
    int conteo = 0;
    double tiempo =0;
    JTextField txt;
    JTextArea txt1;
    public List<ContarPatronesClientRemote> getClientes() {
        return clientes;
    }

    public void setClientes(List<ContarPatronesClientRemote> clientes) {
        this.clientes = clientes;
    }

   
    public ContarPatronesRemoteRMI(JTextField txt,JTextArea txt1) throws RemoteException{
        
        super();
        this.txt=txt;
        this.txt1=txt1;
    }
    
   
    
    public void registerClient(ContarPatronesClientRemote client) throws RemoteException {
        System.out.println("clientee"+client);
        if(client!=null){
            
            this.clientes.add(client);
        }else {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        
     
             
    }

 


  /*  @Override
    public conteoParalelo contarPatrones(String archivo , String patron) throws RemoteException{
        System.out.println("Entrando proceso en "+id+" Archivo"+ archivo);
        
       
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                System.out.println("Entro buffer");
            int lineNumber = 0;
            Pattern pattern = Pattern.compile(patron);
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % numClients == id){
               System.out.println("Entrando proceso en222 "+id+" Archivo"+ archivo);
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
                }
             
                
                lineNumber++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return conteo;
            
      
    }*/
    /*
    public  void contar(String archivo,String patron,JTextArea txt,JTextArea txt2,JTextField txt3) throws IOException{
            long START = System.nanoTime();
            contarPatrones(archivo,patron);
            conteo.END = (System.nanoTime()-START);
            txt2.append("\n Patrones Repetidos " + conteo.count.get());
            txt2.setCaretPosition(txt2.getDocument().getLength());
            
            
            
           double segundos = conteo.END / 1e9; // Convierte nanosegundos a segundos
           String resultado = String.format("%.10f seconds", segundos); // Formatea en segundos con 6 decimales
          txt3.setText("\n" + resultado);
            conteo.count.set(0);
            
        
    }
    */
    /*public static void main(String[] args) throws IOException {
        System.out.println(12%4);
    }*/

  public void iniciar( String archivo, String patron) {
        // Crear un ExecutorService con el número de hilos deseado
        int numThreads = clientes.size(); // Un hilo por cliente
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Ejecutar cada cliente en un hilo separado
        for (ContarPatronesClientRemote client : clientes) {
            executorService.execute(() -> {
                try {
                    client.contar(patron);
                } catch (RemoteException e) {
                    e.printStackTrace(); // Manejar excepciones si es necesario
                }
            });
        }

        // Apagar el ExecutorService después de su uso
        executorService.shutdown();
    }

    @Override
    public void enviarResultado(conteoParalelo conteo) throws RemoteException {
          System.out.println("Resultado : "+conteo.getCount().get());
          
        tiempo += conteo.tiempo;
        String tiempoaux = String.format("%.10f seconds", tiempo);
        System.out.println("tiempo :" +tiempoaux);
        this.conteo += conteo.getCount().get();
        this.txt.setText(tiempoaux);
          this.txt1.append("\n Patrones Repetidos " + this.conteo);
            this.txt1.setCaretPosition(txt1.getDocument().getLength());
            this.tiempo =0;
            this.conteo=0;
            
            
      
    }

   /* @Override
    public int asignarCliente(ContarPatronesRemoteRMI client) throws RemoteException {
        if(client!=null){
            this.id++;
            client.id=id;
            client.numClients = numClients;
        }
    }*/

    /*public static void main(String[] args) {
        System.out.println(0%1);
    }
*/


}

    

