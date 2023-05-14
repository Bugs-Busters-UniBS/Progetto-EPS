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
import javax.swing.table.TableModel;

public class GUI extends JFrame{
    public int targaDEBUG = 0;

    //Inventario interno di veicoli
    private Inventario inVeicoli;

    //Pannelli per il logo e titolo, bottoni, tabella
    private JPanel logoPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();
    private JScrollPane tabellaPanel;

    public GUI(String titolo) {
        //Costruttore superclasse JFrame
        super(titolo);

        //Impostazione ampiezza finestra e layout manager
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(25, 15));

        //Impostazione layout manager dei pannelli
        logoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        bottoniPanel.setLayout(new GridLayout(3, 1, 15, 15));

        //Inserimento Logo UniBS
        try {
            BufferedImage logo = ImageIO.read(new File("logo_unibs.png"));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(logoScal));

            // Composizione logoPanel
            logoPanel.add(picLabel);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        //LABEL CON NOME DEL GRUPPO
        JLabel titoloProg = new JLabel("<html>Gestionale Veicoli<br/>By BugsBusters UniBS</html>");
        titoloProg.setFont(new Font("Lucida Grande", Font.ITALIC, 30));
        logoPanel.add(titoloProg);

        //======================================CREAZIONE TABELLA=================================================
        //Instanziamento Inventario interno
        this.inVeicoli = new Inventario();
        inVeicoli.caricaInventario("database.xml");

        TabellaInventario tabella = new TabellaInventario(inVeicoli);
        //impostazione dimensioni dei pulsanti elimina e dettagli
        TableColumn colonna = tabella.getColumnModel().getColumn(5);
        colonna.setPreferredWidth(20);
        tabellaPanel = new JScrollPane(tabella);
        //=======================================================================================================


        //===================================BOTTONE AGGIUNTA NUOVO VEICOLO===================================
        JButton botAggiungi = new JButton("Aggiungi nuovo veicolo");
        botAggiungi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta Veicoli", inVeicoli);
                GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta veicolo", inVeicoli);
                //aggiorna la tabella dopo aver aggiunto il veicolo
                addGUI.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        tabella.updateTable();
                    }
                });
                addGUI.setVisible(true);
            }
        });
        //===================================================================================================


        //====================BOTTONE SALVATAGGIO INVENTARIO=============================
        JButton botSalva = new JButton("Salva modifiche all'inventario");
        botSalva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inVeicoli.salvaInventario("database.xml");
                JOptionPane.showMessageDialog(null,"Inventario salvato correttamente");
        }});
        //=============================================================================

        
        //====================BOTTONE RIMOZIONE VEICOLI=================================
        JButton botRimuovi = new JButton("Rimuovi veicoli selezionati");
        botRimuovi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String[] opzioni = {"Si", "No"};
                int scelta = JOptionPane.showOptionDialog(botRimuovi, "Sei sicuro di voler rimuovere i veicoli selezionati?", "Scelta", 
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opzioni, null);
                
                if(scelta == JOptionPane.YES_OPTION) {
                    //Ottiene una lista delle righe dalla tabella
                    TableModel model = tabella.getModel();
                    int numRows = model.getRowCount();
                    //Per ogni riga controlla se è selezionata e ne ottiene la targa
                    for(int i=0; i<numRows; i++) {
                        //Rimuove il veicolo dalla targa ottenuta se selezionato
                        if((Boolean)model.getValueAt(i, 5) == true) {
                            String numero = (String)model.getValueAt(i, 3);
                            String paese = (String)model.getValueAt(i, 4);
                            try {
                                Targa targaRimozione = new Targa(numero, paese);
                                inVeicoli.rimuoviVeicolo(targaRimozione);
                            }
                            catch(TargaException ex) {
                                System.out.println("Errore nella rimozione della targa tramite checkbox!");
                            }
                        }       
                    }
                    //Infine aggiorna la tabella
                    tabella.updateTable();
                }
            }
        });
        //===============================================================================

        // Agggiunta campo di ricerca
        // JLabel filtroText = new JLabel("Cerca:");
        // JTextField barraRicerca = new JTextField("", 15);

        // Composizione dei pannelli ...
        bottoniPanel.add(botSalva);
        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botRimuovi);

        // ... e della finestra principale
        this.add(logoPanel, BorderLayout.NORTH);
        this.add(bottoniPanel, BorderLayout.WEST);
        this.add(tabellaPanel, BorderLayout.CENTER);
    }

    //Non funziona
    public void eliminaVeicolo(Veicolo veicolo){
        
        try {
            String targaNum = veicolo.getTarga().getNumero();
            String targaPaese = veicolo.getTarga().getPaese().toString();
            Targa targaRimozione = new Targa(targaNum, targaPaese);

            inVeicoli.rimuoviVeicolo(targaRimozione);
            System.out.println(targaNum);

        } catch (TargaException e) {
            System.out.println("Errore nella rimozione della targa tramite schermata dettagli!");
        }
    }
}
