/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

/**
 *
 * @author alanm
 */
import contarpatrones.conteoParalelo;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContarPatronesClientRemote extends Remote {
    conteoParalelo realizarConteo(String archivo, String patron) throws RemoteException;
     void contar(String patron) throws RemoteException;
}