import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumn;

public class GUI extends JFrame{
    public int targaDEBUG = 0;
    private Inventario inVeicoli;

    private JPanel logoPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();

    public GUI(String titolo) {
        // Costruttore superclasse JFrame
        super(titolo);

        // Creazione Inventario interno
        this.inVeicoli = new Inventario();
        inVeicoli.caricaInventario("database.xml");
    
        // Impostazione ampiezza finestra e layout manager
        this.setSize(1000,800);
        this.setLayout(new GridLayout(4,1));

        // Impostazione layout manager dei pannelli
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        bottoniPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

        try {
            // Inserimento logo
            BufferedImage logo = ImageIO.read(new File("logo_unibs.png"));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(logoScal));

            logoPanel.add(picLabel);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        // Inserimento titolo del programma e nome del gruppo
        JLabel titoloProg = new JLabel("<html>Gestionale Veicoli<br/>By BugsBusters UniBS</html>");
        titoloProg.setFont(new Font("Lucida Grande", Font.ITALIC, 30));
        logoPanel.add(titoloProg);

        //--------------------------------------CREAZIONE TABELLA--------------------------------------------------------
        TabellaInventario tabella = new TabellaInventario(inVeicoli);
        //impostazione dimensioni dei pulsanti elimina e dettagli
        TableColumn colonna = tabella.getColumnModel().getColumn(5);
        colonna.setPreferredWidth(20);
            // if (i==3) {
            //     colonna.setPreferredWidth(100); //third column is bigger
            // } else {
            //     colonna.setPreferredWidth(50);
            // }


        JScrollPane tabellaPanel = new JScrollPane(tabella);
        //---------------------------------------------------------------------------------------------------------------

        // Aggiunta bottone di creazione nuovo veicolo
        JButton botAggiungi = new JButton("Aggiungi nuovo veicolo");
        botAggiungi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta Veicoli", inVeicoli);
                //aggiorna la tabella dopo aver aggiunto il veicolo
                addGUI.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        tabella.updateTable();
                    }
                });
                addGUI.setVisible(true);
            }
        });

        // Aggiunta bottone salvataggio dell'inventario
        JButton botSalva = new JButton("Salva modifiche all'inventario");
        botSalva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inVeicoli.salvaInventario("database.xml");
                JOptionPane.showMessageDialog(null,"Inventario salvato correttamente");
        }});
    
        // Agggiunta campo di ricerca
        // JLabel filtroText = new JLabel("Cerca:");
        // JTextField barraRicerca = new JTextField("", 15);

        // Composizione dei pannelli ...
        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botSalva);
        // bottoniPanel.add(filtroText);
        // bottoniPanel.add(barraRicerca);
        // ... e della finestra principale
        this.add(logoPanel);
        this.add(bottoniPanel);
        this.add(tabellaPanel);
    }
}
