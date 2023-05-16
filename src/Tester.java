import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class Tester {
    public static void main(String[] args) {
        //=================TESTING CARICAMENTO==========================================================
        /* Inventario inventario = new Inventario();
        inventario.caricaInventario("Inventario.xml");

        for(Veicolo vec : inventario.getLista()) {
            System.out.printf("Veicolo: %s, marca: %s, modello: %s\n", vec.getTipo(), vec.getMarca(), vec.getModello());
            System.out.printf("Ha targa: %s del paese: %s\n", vec.getTarga().getNumero(), vec.getTarga().getPaese().toString());
        } */


        //================TESTING SALVATAGGIO===========================================================
        /* Inventario inv = new Inventario();

        Targa targa1 = new Targa("ADGSBS", "Italia");
        Targa targa2 = new Targa("FFFSAB", "Germania");
        Targa targa3 = new Targa("AAAAAV", "Italia");
        Targa targa4 = new Targa("QWERTY", "Germania");

        Veicolo auto1 = new Automobile("Fiat", "500", targa1, 5);
        Veicolo auto2 = new Automobile("Audi", "boh", targa2, 150);
        Veicolo camion = new Camion("Coso", "modello1", targa3, 40.23);
        Veicolo moto = new Moto("Honda", "nonoloso", targa4, 1235);

        inv.aggiungiVeicolo(auto1);
        inv.aggiungiVeicolo(auto2);
        inv.aggiungiVeicolo(camion);
        inv.aggiungiVeicolo(moto);

        inv.salvaInventario("Inv1.xml");

        Inventario inv2 = new Inventario();
        inv2.caricaInventario("Inv1.xml");
        inv2.rimuoviVeicolo("FFFSAB");
        inv2.salvaInventario("Inv2.xml"); */
       
        //======================TESTING GUI=================================================================
        
        FlatMacLightLaf.setup();

        ImageIcon logo = new ImageIcon("immagini/logo.png");

        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        GUI myGui = new GUI("Gestionale Veicoli");
        myGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGui.setVisible(true);
        myGui.setIconImage(logo.getImage());


        // GUIVeicolo addGUI = new GUIVeicolo();
        // addGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // addGUI.setVisible(true);
        
        // try{
        //     Moto auto = new Moto("Fiat", "500", new Targa("GK178MM", "Italia"), 3, "Fiaat500.png");
        //     GUIRiepilogoVeicolo riepGUI = new GUIRiepilogoVeicolo("Test",auto);
        //     riepGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //     riepGUI.setVisible(true);
        // }
        // catch(TargaException e){
        //     e.printStackTrace();
        // }
       
    }
}
