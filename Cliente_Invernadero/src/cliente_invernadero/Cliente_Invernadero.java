package cliente_invernadero;

import java.io.*;

public class Cliente_Invernadero {

    public static void main(String[] args) throws IOException, InterruptedException {
        cliente greenhouse = new cliente("192.168.43.47", 5000);
        greenhouse.setVisible(true);
        while (true) {
            if (greenhouse.conectado&&greenhouse.beagle.peticion1&&greenhouse.beagle.peticion2) {
                greenhouse.beagle.mandar("factores");
                greenhouse.beagle.peticion1=false;
                greenhouse.beagle.peticion2=false;
            }
            //System.out.println(greenhouse.beagle.amount2+" "+greenhouse.beagle.amount1);
            greenhouse.graf.punto((int) greenhouse.beagle.amount2, (int) greenhouse.beagle.amount1);
            Thread.sleep(500);
        }

    }
}
