/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import contarpatrones.ContarPatronesRemoteRMI;
import contarpatrones.conteoParalelo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JTextArea;

public interface ContarPatronesRemote extends Remote {
    void registerClient(ContarPatronesClientRemote client) throws RemoteException; 
   // conteoParalelo contarPatrones(String archivo, String patron) throws RemoteException;
    void iniciar(String archivo , String patron) throws RemoteException;
    void enviarResultado(conteoParalelo conteo) throws RemoteException;
    //int asignarCliente(ContarPatronesRemoteRMI client) throws RemoteException;
}
