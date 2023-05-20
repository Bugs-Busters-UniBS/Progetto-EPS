// Import GUI
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import enumerazioni.Paese;
import enumerazioni.VeicoloEnum;

import java.awt.*;

// Import event
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***
 * Classe GUI Destinata alla aggiunta di un nuovo veicolo all'internio del gestonale
 */
public class GUIAggiuntaVeicolo extends JFrame implements ActionListener{
    
    // Copia dei dati
    private Inventario inv;

    // Creazione dei panel di composizione della GUI
    private JPanel insertionPanel = new JPanel();
    private JPanel pulsantieraLabel = new JPanel();
    private JPanel pulsantiera = new JPanel();
    private JPanel labelPanel = new JPanel();
    
    // Creazione di tutte le label necessarie
    private JLabel labelTipoVeicolo;
    private JLabel labelMarca;
    private JLabel labelModello;
    private JLabel labePaese;
    private JLabel labelNumeroTarga;
    private JLabel labelPorte;
    private JLabel labelPortata;
    private JLabel labelCilindrata;
    private JLabel labelFileSelezionato;

    // Creazione dei Menu a Dropdown
    private JComboBox<String> dropdownVeicolo;
    private JComboBox<String> dropdownPaese;
    private JComboBox<String> dropdownPorte;

    // Creazione dei TextField di inserimento
    private JTextField inserimentoMarca;
    private JTextField inserimentoModello;
    private JTextField inserimentoNumeroTarga;
    private JTextField inserimentoPortata;
    private JTextField inserimentoCilindrata;

    // Creazione dei bottoni
    private JButton salvaVeicolo;
    private JButton aggiungiImmagine;

    // INIZIO COSTRUTTORE
    public GUIAggiuntaVeicolo(String titolo, Inventario inv, Component parent) {
        // Richiamo al costruttore della super classe
        super(titolo);
        this.inv = inv;

        // Settaggio posizione relativa rispetto al component passato
        setLocationRelativeTo(parent);
        
        // Seleziopne dimensione finestra e blocco della dimensionamento di quest'ultima e selezione del Layout
        this.setSize(400,300);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        
        // Selezine Layout del panel pulsantiera e inserimento
        insertionPanel.setLayout(new GridLayout(8,2));
        pulsantieraLabel.setLayout(new BorderLayout());

        // Istanziamnto delle Label
        labelTipoVeicolo = new JLabel("Tipo Veicolo", SwingConstants.CENTER);
        labelMarca = new JLabel("Marca", SwingConstants.CENTER);
        labelModello = new JLabel("Modello", SwingConstants.CENTER);
        labePaese = new JLabel("Paese", SwingConstants.CENTER);
        labelNumeroTarga = new JLabel("Targa", SwingConstants.CENTER);
        labelPorte = new JLabel("N Porte", SwingConstants.CENTER);
        labelPortata = new JLabel("Portata", SwingConstants.CENTER);
        labelCilindrata = new JLabel("Cilindrata", SwingConstants.CENTER);
        labelFileSelezionato = new JLabel("Non hai selezionato nessuna immagine");
        // Selezione colore standard della label di inseriemnto file se nojn è stato inserito alcun file
        labelFileSelezionato.setForeground(new java.awt.Color(222, 51, 72));
        
        // Inserimento veicoli supportati
        String[] stringVeicolo = VeicoloEnum.VEICOLI;
        dropdownVeicolo = new JComboBox<String>(stringVeicolo);

        // Inserimento paesi supportati
        String[] stringPaese = Paese.PAESI;
        dropdownPaese = new JComboBox<String>(stringPaese);

        // Inserimento N° porte
        String[] stringPorte = {"3", "5"};
        dropdownPorte = new JComboBox<String>(stringPorte);

        // Istanziamento dei FextFiled
        inserimentoMarca = new JTextField("", 15);
        inserimentoModello = new JTextField("", 15);
        inserimentoNumeroTarga = new JTextField("", 15);
        inserimentoPortata = new JTextField("", 15);
        inserimentoCilindrata = new JTextField("", 15);

        // Settaggio colori standard (automobile) per le label Portata e Cilindrata
        labelPortata.setForeground(new java.awt.Color(86, 86, 86));
        labelCilindrata.setForeground(new java.awt.Color(86, 86, 86));

        // Istanziamento pulsantiera
        aggiungiImmagine = new JButton("Aggiunta immagine");
        salvaVeicolo = new JButton("Salva veicolo");
        
        // Selezione colore per il pulsante di salvataggio
        salvaVeicolo.setBackground(new java.awt.Color(52, 120, 247));
        salvaVeicolo.setForeground(Color.WHITE);

        //aggiunta action Listener ai bottoni
        aggiungiImmagine.addActionListener(this);
        salvaVeicolo.addActionListener(this);
        
        // Aggiunta delle Label e TextField al insertionPanel
        insertionPanel.add(labelTipoVeicolo);
        insertionPanel.add(dropdownVeicolo);
        dropdownVeicolo.addActionListener(this);

        insertionPanel.add(labelMarca);
        insertionPanel.add(inserimentoMarca);

        insertionPanel.add(labelModello);
        insertionPanel.add(inserimentoModello);

        insertionPanel.add(labePaese);
        insertionPanel.add(dropdownPaese);

        insertionPanel.add(labelNumeroTarga);
        insertionPanel.add(inserimentoNumeroTarga);

        insertionPanel.add(labelPorte);
        insertionPanel.add(dropdownPorte);
        
        insertionPanel.add(labelPortata);
        insertionPanel.add(inserimentoPortata);
        inserimentoPortata.setEditable(false);

        insertionPanel.add(labelCilindrata);
        insertionPanel.add(inserimentoCilindrata);
        inserimentoCilindrata.setEditable(false);

        //Aggiunta della label file selezionato al labelPanel
        labelPanel.add(labelFileSelezionato, BorderLayout.PAGE_END);

        // Aggiunta pulsanti della Label alla pulsantiera
        pulsantiera.add(aggiungiImmagine);
        pulsantiera.add(salvaVeicolo);
        pulsantieraLabel.add(labelPanel, BorderLayout.PAGE_START);
        pulsantieraLabel.add(pulsantiera, BorderLayout.PAGE_END);

        // Aggiunta Panel secondari al Panel principale
        this.add(insertionPanel, BorderLayout.CENTER);
        this.add(pulsantieraLabel, BorderLayout.PAGE_END);
    }

    // Metodo di override per la Selezione dell azione da eseguire su ciascun componente
    @Override
    public void actionPerformed(ActionEvent e) {

        // Caso del bottone di salvataggio
        if(e.getSource() == salvaVeicolo){
            // Ottiene i parametri del veicolo dai TextField
            String veicolo = (String)dropdownVeicolo.getSelectedItem();
            String marca = inserimentoMarca.getText();
            String modello = inserimentoModello.getText();
            String paese = (String)dropdownPaese.getSelectedItem();
            String numeroTarga = inserimentoNumeroTarga.getText();
            String porte = (String)dropdownPorte.getSelectedItem();
            String portata = inserimentoPortata.getText();
            String cilindrata = inserimentoCilindrata.getText();
            
            // Riconosce se e' stata inserita un immagine personalizzata
            Boolean selezioneImmagine = true;
            if(labelFileSelezionato.getText().equalsIgnoreCase("Non hai selezionato nessuna immagine")){
                selezioneImmagine = false;
            }
            
            try{
                //Se il veicolo selezionato è un auto
                if(veicolo.equalsIgnoreCase("Automobile")){
                    // Se non è stata selezionata nessuna immagine
                    if(!selezioneImmagine) {
                        inv.aggiungiVeicolo(new Automobile(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(porte)));
                    }
                    // Se è stata selezionata un immagine
                    else {
                        inv.aggiungiVeicolo(new Automobile(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(porte), labelFileSelezionato.getText()));
                    }

                    JOptionPane.showMessageDialog(insertionPanel, "Veicolo aggiunto correttamente!", null, JOptionPane.INFORMATION_MESSAGE, null);
                    this.dispose();
                } 
                else if(veicolo.equalsIgnoreCase("Camion")){
                    // Creazione stringa portata e apparizione messaggio di errore se non e' un double e replacement del . con la ,
                    try {
                        // Potrebbe lanciare un eccezione se l'utente inserisce dei caratteri 
                        Double portataCamion = Double.parseDouble(portata.replace(",","."));

                        if(!selezioneImmagine) {
                            inv.aggiungiVeicolo(new Camion(marca, modello, new Targa(numeroTarga, paese), portataCamion));
                        }
                        else {
                            inv.aggiungiVeicolo(new Camion(marca, modello, new Targa(numeroTarga, paese), portataCamion, labelFileSelezionato.getText()));
                        }

                        JOptionPane.showMessageDialog(insertionPanel, "Veicolo aggiunto correttamente!", null, JOptionPane.INFORMATION_MESSAGE, null);
                        this.dispose();
                    }
                    // Errore inserimento valore portata
                    catch (NumberFormatException errorPortata) {
                        JOptionPane.showMessageDialog(insertionPanel,"Inserire un numero","Errore inserimento portata",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(veicolo.equalsIgnoreCase("Moto")){
                    // Creazione stringa cilindrata e apparizione messaggio di errore se non e' un int 
                    try {
                        int cilindrataMoto = Integer.parseInt(cilindrata);
                        if(!selezioneImmagine) {
                            inv.aggiungiVeicolo(new Moto(marca, modello, new Targa(numeroTarga, paese), cilindrataMoto));
                        }
                        else {
                            inv.aggiungiVeicolo(new Moto(marca, modello, new Targa(numeroTarga, paese), cilindrataMoto, labelFileSelezionato.getText()));
                        }
                        JOptionPane.showMessageDialog(insertionPanel, "Veicolo aggiunto correttamente!", null, JOptionPane.INFORMATION_MESSAGE, null);
                        this.dispose();
                    }
                    catch (NumberFormatException errorPortata) {
                        JOptionPane.showMessageDialog(insertionPanel,"Inserire un numero intero","Errore inserimento cilindrata",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // Catch dell errore targa su tutte le tipologie di Veicolo
            catch(TargaException te){
                JOptionPane.showMessageDialog(insertionPanel,te.getMessage(),"Errore inserimento Targa",JOptionPane.ERROR_MESSAGE);
            }
        }

        // Caso Bottone di aggiunta immagine
        else if(e.getSource() == aggiungiImmagine){
            // Finestra di Scelta del file
            final JFileChooser file = new JFileChooser();
            file.setAcceptAllFileFilterUsed(false);
            FileFilter pngFileFilter = new FileTypeFilter(".png", "Immagine PNG");
            FileFilter jpgFileFilter = new FileTypeFilter(".jpg", "Immagine JPG");
            file.addChoosableFileFilter(jpgFileFilter);
            file.addChoosableFileFilter(pngFileFilter);
            
            file.showOpenDialog(this);

            try {
                labelFileSelezionato.setText(file.getSelectedFile().toString());
                labelFileSelezionato.setForeground(null);
            }
            // Catch di errori con il file selezionato
            catch(Exception er){
                System.out.println("Il File scelto non è stato trovato!");
            }

        }

        // Disitabilazione degliu spazi non necessari all'inserimento del particolare veicolo
        else if(e.getSource() == dropdownVeicolo){
            switch(dropdownVeicolo.getSelectedIndex()){
                //Automobile
                case 0:
                dropdownPorte.setEnabled(true);
                inserimentoPortata.setEditable(false);
                inserimentoCilindrata.setEditable(false);
                labelPorte.setForeground(null);
                labelPortata.setForeground(new java.awt.Color(86, 86, 86));
                labelCilindrata.setForeground(new java.awt.Color(86, 86, 86));
                break;

                //Camion
                case 1:
                dropdownPorte.setEnabled(false);
                inserimentoPortata.setEditable(true);
                inserimentoCilindrata.setEditable(false);
                labelPorte.setForeground(new java.awt.Color(86, 86, 86));
                labelPortata.setForeground(null);
                labelCilindrata.setForeground(new java.awt.Color(86, 86, 86));
                break;

                //Moto
                case 2:
                dropdownPorte.setEnabled(false);
                inserimentoPortata.setEditable(false);
                inserimentoCilindrata.setEditable(true);
                labelPorte.setForeground(new java.awt.Color(86, 86, 86));
                labelPortata.setForeground(new java.awt.Color(86, 86, 86));
                labelCilindrata.setForeground(null);
                break;
            }
        }
    }   
}
