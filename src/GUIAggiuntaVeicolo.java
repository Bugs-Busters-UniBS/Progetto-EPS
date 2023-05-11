import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
=======
>>>>>>> a211975a9eab170aebfb6525bcc6427f5dfa5fa1

public class GUIAggiuntaVeicolo extends JFrame implements ActionListener{
 
    private Inventario inv;

    private JPanel insertionPanel = new JPanel();
    private JPanel pulsantieraLabel = new JPanel();
    private JPanel pulsantiera = new JPanel();
    private JPanel label = new JPanel();
    
    private JLabel labelTipoVeicolo;
    private JLabel labelMarca;
    private JLabel labelModello;
    private JLabel labePaese;
    private JLabel labelNumeroTarga;
    private JLabel labelPorte;
    private JLabel labelPortata;
    private JLabel labelCilindrata;
    private JLabel labelFileSelezionato;
    private JComboBox<String> dropdownVeicolo;
    private JComboBox<String> dropdownPaese;
    private JComboBox<String> dropdownPorte;
    private JTextField inserimentoMarca;
    private JTextField inserimentoModello;
    private JTextField inserimentoNumeroTarga;
    private JTextField inserimentoPortata;
    private JTextField inserimentoCilindrata;
    private JButton salvaVeicolo;
    private JButton aggiungiImmagine;


<<<<<<< HEAD
    //da riaggiungere String titolo, Inventario inv   
    public GUIAggiuntaVeicolo(){
        // super(titolo);
        // this.inv=inv;
=======
    public GUIAggiuntaVeicolo(String titolo, Inventario inv) {
        super(titolo);
        this.inv=inv;
>>>>>>> a211975a9eab170aebfb6525bcc6427f5dfa5fa1

        this.setSize(400,300);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        //definizione due panel
        insertionPanel.setLayout(new GridLayout(8,2));
        pulsantieraLabel.setLayout(new GridLayout(1, 2));

        //titolo
        JLabel titoloGUI = new JLabel("Aggiunta Veicoli",  SwingConstants.CENTER);
        titoloGUI.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
        this.add(titoloGUI, BorderLayout.PAGE_START);

        //definizione insertionPanel
        labelTipoVeicolo = new JLabel("Tipo Veicolo", SwingConstants.CENTER);
        labelMarca = new JLabel("Marca", SwingConstants.CENTER);
        labelModello = new JLabel("Modello", SwingConstants.CENTER);
        labePaese = new JLabel("Paese", SwingConstants.CENTER);
        labelNumeroTarga = new JLabel("Targa", SwingConstants.CENTER);
        labelPorte = new JLabel("N Porte", SwingConstants.CENTER);
        labelPortata = new JLabel("Portata", SwingConstants.CENTER);
        labelCilindrata = new JLabel("Cilindrata", SwingConstants.CENTER);
        labelFileSelezionato = new JLabel("Non hai selezionato nessuna immagine");
        

        String[] stringVeicolo = {"Automobile", "Camion", "Moto"};
        dropdownVeicolo = new JComboBox<String>(stringVeicolo);

        String[] stringPaese = {"ITALIA", "GERMANIA", "FRANCIA"};
        dropdownPaese = new JComboBox<String>(stringPaese);

        String[] stringPorte = {"3", "5"};
        dropdownPorte = new JComboBox<String>(stringPorte);

        inserimentoMarca = new JTextField("", 15);
        inserimentoModello = new JTextField("", 15);
        inserimentoNumeroTarga = new JTextField("", 15);
        inserimentoPortata = new JTextField("", 15);
        inserimentoCilindrata = new JTextField("", 15);

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

        //standart automobile
        labelPortata.setForeground(Color.lightGray);
        labelCilindrata.setForeground(Color.lightGray);

        //definizione pulsantiera
        aggiungiImmagine = new JButton("Aggiunta immagine");
        salvaVeicolo = new JButton("Salva veicolo");

        // JButton aggiungiFoto = new JButton("aggiungi foto");
        pulsantiera.add(aggiungiImmagine);
        pulsantiera.add(salvaVeicolo);
        label.add(labelFileSelezionato, BorderLayout.PAGE_END);
        aggiungiImmagine.addActionListener(this);
        salvaVeicolo.addActionListener(this);
        
        //aggiunta alla pulsantiera label
        pulsantieraLabel.setLayout(new BorderLayout());
        pulsantieraLabel.add(label, BorderLayout.PAGE_START);
        pulsantieraLabel.add(pulsantiera, BorderLayout.PAGE_END);
        
        //aggiunta panel
        this.add(insertionPanel, BorderLayout.CENTER);
        this.add(pulsantieraLabel, BorderLayout.PAGE_END);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==salvaVeicolo){
            // Ottiene i parametri del veicolo dalla finestra
            String veicolo = (String)dropdownVeicolo.getSelectedItem();
            String marca = inserimentoMarca.getText();
            String modello = inserimentoModello.getText();
            String paese = (String)dropdownPaese.getSelectedItem();
            String numeroTarga = inserimentoNumeroTarga.getText();
            String porte = (String)dropdownPorte.getSelectedItem();
            String portata = inserimentoPortata.getText();
            String cilindrata = inserimentoCilindrata.getText();

            // Crea Filename da marca e modello
            System.out.println(veicolo+" "+marca+" "+modello+" "+paese+" "+numeroTarga+" "+porte+" "+portata+" "+cilindrata);

            if(veicolo.equalsIgnoreCase("Automobile")){
                if(labelFileSelezionato.getText().equalsIgnoreCase("Non hai selezionato nessuna immagine"))
                    inv.aggiungiVeicolo(new Automobile(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(porte)));
                else
                    inv.aggiungiVeicolo()new Automobile(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(porte));
            }
                
            else if(veicolo.equalsIgnoreCase("Camion")){
                //creazione stringa portata e apparizione messaggio di errore se non e' un double 
                try {
                    Double.parseDouble(portata);
                }
                catch (NumberFormatException errorPortata) {
                    JOptionPane.showInternalMessageDialog(null,"Inserire un numero (la virgola non e' accettata usare il .)","Errore inserimento portata",JOptionPane.ERROR_MESSAGE);
                }
                inv.aggiungiVeicolo(new Camion(marca, modello, new Targa(numeroTarga, paese), Double.parseDouble(portata)));
            }
            else if(veicolo.equalsIgnoreCase("Moto")){
                //creazione stringa portata e apparizione messaggio di errore se non e' un int 
                try {
                    Integer.parseInt(cilindrata);
                }
                catch (NumberFormatException errorPortata) {
                    JOptionPane.showInternalMessageDialog(null,"Inserire un numero intero","Errore inserimento cilindrata",JOptionPane.ERROR_MESSAGE);
                }
                inv.aggiungiVeicolo(new Moto(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(cilindrata)));
            }
            this.dispose();
        }
        //tasto di aggiunta immagine
        else if(e.getSource()==aggiungiImmagine){
            final JFileChooser file = new JFileChooser();
            file.showOpenDialog(this);
            
            try {
                labelFileSelezionato.setText(file.getSelectedFile().toString());
            }
            catch(Exception er){
                System.out.println("il file non è stato trovato");
            }

        }
        //disitabilazione spazi non necessari all'inserimento del particolare veicolo
        else if(e.getSource()==dropdownVeicolo){
            switch(dropdownVeicolo.getSelectedIndex()){
                case 0:
                dropdownPorte.setEnabled(true);
                inserimentoPortata.setEditable(false);
                inserimentoCilindrata.setEditable(false);
                labelPorte.setForeground(Color.black);
                labelPortata.setForeground(Color.lightGray);
                labelCilindrata.setForeground(Color.lightGray);
                break;
                case 1:
                dropdownPorte.setEnabled(false);
                inserimentoPortata.setEditable(true);
                inserimentoCilindrata.setEditable(false);
                labelPorte.setForeground(Color.lightGray);
                labelPortata.setForeground(Color.black);
                labelCilindrata.setForeground(Color.lightGray);
                break;
                case 2:
                dropdownPorte.setEnabled(false);
                inserimentoPortata.setEditable(false);
                inserimentoCilindrata.setEditable(true);
                labelPorte.setForeground(Color.lightGray);
                labelPortata.setForeground(Color.lightGray);
                labelCilindrata.setForeground(Color.black);
                break;
            }

        }
    }   
}
