import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame{

    private Inventario inVeicoli;

    private JPanel logoPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();

    public GUI(String titolo) {
        super(titolo);

        this.inVeicoli = new Inventario();

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

        JButton botAggiungi = new JButton("Aggiungi veicolo");
        JButton botRimuovi = new JButton("Rimuovi veicolo");
        JButton botCerca = new JButton("Cerca");
        JTextField barraRicerca = new JTextField("", 15);

        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botRimuovi);
        bottoniPanel.add(barraRicerca);
        bottoniPanel.add(botCerca);

        String[] nomiColonne = {"Tipo Veicolo", "Marca", "Modello", "Numero Targa", "Paese Targa"};
        Object[][] data = {{"Moto", "Marca1", "Modello1", "AAAAGGGG", "IT"}, {"Camion", "Marca2", "Modello2", "AAAAAA", "DE"}};
        JTable tabella = new JTable(data, nomiColonne);
        tabella.setEnabled(false);
        JScrollPane tabellaPanel = new JScrollPane(tabella);

        this.add(logoPanel);
        this.add(bottoniPanel);
        this.add(tabellaPanel);
    }
}
