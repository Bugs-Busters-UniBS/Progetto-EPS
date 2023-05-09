import javax.swing.JFrame;
import javax.swing.UIManager;
// import javax.swing.UnsupportedLookAndFeelException;

// import com.formdev.flatlaf.FlatDarculaLaf;
// import com.formdev.flatlaf.FlatLightLaf;

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
        
        // FlatDarculaLaf.setup();

        // try {
        //     UIManager.setLookAndFeel(new FlatDarculaLaf());
        // } catch (UnsupportedLookAndFeelException e) {
        //     throw new RuntimeException(e);
        // }

        GUI myGui = new GUI("Gestionale Veicoli");
        myGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGui.setVisible(true);

        // GUIVeicolo addGUI = new GUIVeicolo();
        //         addGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //         addGUI.setVisible(true);
    }
}
