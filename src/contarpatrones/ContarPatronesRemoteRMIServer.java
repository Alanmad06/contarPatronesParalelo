/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contarpatrones;

/**
 *
 * @author alanm
 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContarPatronesRemoteRMIServer {
    ContarPatronesRemoteRMI contarPatronesRemote;

    public void iniciar(JTextField txt, JTextArea txt1){
        
         try {
            // Inicia el registro RMI en el puerto 1099 (puedes cambiar el puerto si es necesario)
             Registry registry=LocateRegistry.createRegistry(1099);

            // Crea una instancia del servicio remoto
            contarPatronesRemote = new ContarPatronesRemoteRMI(txt,txt1);

            // Registra el servicio remoto con el nombre "ContarPatronesRemote"
            registry.rebind("ContarPatronesRemote", contarPatronesRemote);

            System.out.println("Servidor RMI iniciado.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void iniciarCalculo(String archivo,String patron) throws RemoteException{
        contarPatronesRemote.iniciar(archivo, patron);
    }

    public static void main(String[] args) {
       
    }
}
