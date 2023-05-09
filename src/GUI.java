import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame{
    public int targaDEBUG = 0;
    private Inventario inVeicoli;

    private JPanel logoPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();

    public GUI(String titolo) {
        super(titolo);

        this.inVeicoli = new Inventario();
        inVeicoli.caricaInventario("databasepy.xml");
        /* Veicolo coso = new Automobile("Honda", "qualcosa", new Targa("STOCAZZO", "Italia"), 50);
        inVeicoli.aggiungiVeicolo(coso); */

        this.setSize(1000,800);
        this.setLayout(new GridLayout(4,1));

        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        bottoniPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        try {
            BufferedImage logo = ImageIO.read(new File("logo_unibs.png"));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(logoScal));

            logoPanel.add(picLabel);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        JLabel titoloProg = new JLabel("<html>Gestionale Veicoli<br/>By BugsBusters UniBS</html>");
        titoloProg.setFont(new Font("Lucida Grande", Font.ITALIC, 30));
        logoPanel.add(titoloProg);

        JButton botAggiungi = new JButton("Aggiungi nuovo veicolo");
        

        JButton botSalva = new JButton("Salva modifiche all'inventario");
        botSalva.addActionListener(ev -> inVeicoli.salvaInventario("databasepy.xml"));
    
        JLabel filtroText = new JLabel("Cerca:");
        JTextField barraRicerca = new JTextField("", 15);

        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botSalva);
        bottoniPanel.add(filtroText);
        bottoniPanel.add(barraRicerca);

        //--------------------------------------CREAZIONE TABELLA--------------------------------------------------------
        TabellaInventario tabella = new TabellaInventario(inVeicoli);
        JScrollPane tabellaPanel = new JScrollPane(tabella);

        //---------------------------------------------------------------------------------------------------------------

        // TEST AGGIUNTA RIGA
        /* Veicolo v = new Camion("marca", "modello", new Targa("ASIFHB", "Germania"), 150.0);
        tabella.addRow(v);
        inVeicoli.aggiungiVeicolo(v); */

        this.add(logoPanel);
        this.add(bottoniPanel);
        this.add(tabellaPanel);
    }
}
