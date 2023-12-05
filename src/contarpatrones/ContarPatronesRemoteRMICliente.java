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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ContarPatronesRemoteRMICliente extends UnicastRemoteObject implements ContarPatronesClientRemote{

    int id =0;
    int numClients=2;
    conteoParalelo conteo = new conteoParalelo();
    String archivo;
    String patron;
    ContarPatronesRemote contarPatronesRemote;
    public ContarPatronesRemoteRMICliente() throws RemoteException{
        super();
        try {
          
            Registry registryClient = LocateRegistry.getRegistry("192.168.5.101", 1099);
            contarPatronesRemote = (ContarPatronesRemote) registryClient.lookup("ContarPatronesRemote");
            contarPatronesRemote.registerClient(this);
            
            System.out.println("Conectadooooooooo");
              
        } catch (Exception e) {
            e.printStackTrace();
        }
           SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
    
    private  void createAndShowGUI() {
       JFrame frame = new JFrame("File and Pattern Selection Dialog");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton selectFileButton = new JButton("Select File");
        JTextField filePathField = new JTextField(20);

        JLabel patronLabel = new JLabel("Pattern:");
        JTextField patronTextField = new JTextField(10);

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile(frame, filePathField, patronTextField);
            }
        });

        JPanel panel = new JPanel();
        panel.add(selectFileButton);
        panel.add(filePathField);
        panel.add(patronLabel);
        panel.add(patronTextField);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setSize(350, 70);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private  void selectFile(Component parentComponent, JTextField filePathField, JTextField patronTextField) {
         JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);

        int returnVal = fileChooser.showOpenDialog(parentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            archivo = selectedFile.getAbsolutePath(); // Obtén el nombre del archivo
            filePathField.setText(archivo);

            patron = patronTextField.getText(); // Obtén el patrón desde el JTextField
            
            
            System.out.println(this.patron +" "+ this.archivo);
        }
    
    }
    @Override
    public conteoParalelo realizarConteo(String archivo, String patron) throws RemoteException {
          System.out.println("Entrando proceso en "+id+" Archivo"+ archivo);
        
       
        try (BufferedReader reader = new BufferedReader(new FileReader(this.archivo))) {
                System.out.println("Entro buffer");
            int lineNumber = 0;
            Pattern pattern = Pattern.compile(patron);
            String line;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % numClients == id){
               //System.out.println("Entrando proceso en222 "+id+" Archivo"+ archivo);
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
             
    }
    
    @Override
        public  void contar(String patron) throws RemoteException {
            long START = System.nanoTime();
            realizarConteo(archivo,patron);
            conteo.END = (System.nanoTime()-START);
         
         
           conteo.tiempo = conteo.END / 1e9; // Convierte nanosegundos a segundos
            // Formatea en segundos con 6 decimales
            
            this.enviarResultado();
            
            conteo.getCount().set(0);
            conteo.tiempo=0;
           
            
        
    }
        
        public void enviarResultado() throws RemoteException{
            this.contarPatronesRemote.enviarResultado(conteo);
        }
    
    
    
    
    public static void main(String[] args) throws RemoteException {
        
         new ContarPatronesRemoteRMICliente();
         
    }

    
}
