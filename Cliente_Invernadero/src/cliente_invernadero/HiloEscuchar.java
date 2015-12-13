package cliente_invernadero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloEscuchar extends Thread {
    public float amount1;
    public float amount2;
    private Socket kkSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public String fromServer = null;
    public String fromUser = null;
    public boolean peticion1=true;
    public boolean peticion2=true;
    
    public HiloEscuchar(String IP, int Puerto) throws IOException {
        try {
            //kkSocket = new Socket("148.220.90.110", 5557);
            kkSocket = new Socket(IP, Puerto);
            out = new PrintWriter(kkSocket.getOutputStream(), true);                    //paquete salida
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));  //paquete entrada
            //stdIn = new BufferedReader(new InputStreamReader(System.in)); //get server response
            System.out.println("Connected to web server");
            fromServer = in.readLine();
            System.out.println("Servidor: " + fromServer);
        } 
        catch (UnknownHostException e) {
            System.err.println("Don't know about host:");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    
    }
    
    public void mandar(String cadena) throws IOException{
        out.println(cadena);//socket
        System.out.println(cadena);//pantalla
    }
    

    @Override
    public void run() {
        System.out.println("Esperando...");
        while (true) {
            try {
                 fromServer = in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(HiloEscuchar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                mandar(fromUser);
            } catch (IOException ex) {
                Logger.getLogger(HiloEscuchar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (fromServer.equals("H")) {
                try {
                    fromServer = in.readLine();
                    this.amount2 = Float.parseFloat(fromServer);
                    System.out.println("Humedad = " + this.amount2);
                    peticion1=true;
                } catch (IOException ex) {
                    Logger.getLogger(HiloEscuchar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            if (fromServer.equals("T")) {
                try {
                    fromServer = in.readLine();
                    this.amount1 = Float.parseFloat(fromServer);
                    System.out.println("Temperatura = " + this.amount1);
                    peticion2=true;
                } catch (IOException ex) {
                    Logger.getLogger(HiloEscuchar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fromUser="";
            fromServer="";
        }
    }

}

