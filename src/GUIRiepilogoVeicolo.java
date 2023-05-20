import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import enumerazioni.Paese;

/***
 * Classe per la finestra di visualizzazione dei dati del veicolo
 */

public class GUIRiepilogoVeicolo extends JFrame implements ActionListener {
    // Definizione pannelli principali
    private JPanel pannelloDati = new JPanel(new GridBagLayout());
    private JPanel pannelloImmagine = new JPanel(new BorderLayout());
    private GridBagConstraints gbcDati = new GridBagConstraints();

    //creazione bottoni
    private JButton eliminaButton = new JButton("Elimina");
    private JButton closeButton = new JButton("Chiudi");

    //Variabili interne
    private Veicolo veicolo;
    private Inventario inv;

    public <T extends Veicolo> GUIRiepilogoVeicolo(String titolo, T veicolo, Inventario inv, Component parent) {
        super(titolo);
        this.inv= inv;
        this.veicolo=veicolo;

        // Settaggio Posizione relativa rispetto al parent
        setLocationRelativeTo(parent);

        // Definizione layout e dimensioni
        this.setLayout(new BorderLayout(10,0));
        this.setSize(650,300);
        

        //creazione label
        JLabel titoloGUI = new JLabel("Dettagli Veicolo");
        JLabel tipo = new JLabel("Tipologia: " + veicolo.getTipo());;
        JLabel marca = new JLabel("Marca: " + veicolo.getMarca());
        JLabel modello = new JLabel("Modello: " + veicolo.getModello());
        JLabel caratteristicaAggiuntiva = new JLabel();
    
        // Definizione font label
        titoloGUI.setFont(new Font("Serif", Font.PLAIN, 20));
        tipo.setFont(new Font("Serif", Font.PLAIN, 16));
        marca.setFont(new Font("Serif", Font.PLAIN, 16));
        modello.setFont(new Font("Serif", Font.PLAIN, 16));

        
        gbcDati.anchor = GridBagConstraints.FIRST_LINE_START;
        gbcDati.insets = new Insets(2, 0, 10, 10);

        //Aggiunta delle label costanti al pannello dati nelle rispettive posizioni

        gbcDati.gridx = 0;
        gbcDati.gridy = 0;
        pannelloDati.add(titoloGUI, gbcDati);

        gbcDati.gridy += 1;
        gbcDati.insets = new Insets(2, 0, 0, 10);
        pannelloDati.add(tipo,gbcDati);

        gbcDati.gridy += 1;
        pannelloDati.add(marca,gbcDati);

        gbcDati.gridy += 1;
        pannelloDati.add(modello,gbcDati);
        

        // riconoscimento tipo veicolo e istanziazione label mancante
        if(veicolo instanceof Automobile){
            Automobile auto = (Automobile)veicolo;
            caratteristicaAggiuntiva = new JLabel("N Porte: " + String.valueOf(auto.getNumeroPorte()));
        }
        else if(veicolo instanceof Camion){
            Camion camion = (Camion)veicolo;
            caratteristicaAggiuntiva = new JLabel("Portata: " + String.valueOf(camion.getPortata()));
        }
        else if(veicolo instanceof Moto){
            Moto moto = (Moto)veicolo;
            caratteristicaAggiuntiva = new JLabel("Cilindrata: " + String.valueOf(moto.getCilindrata()));
        }

        gbcDati.gridy+=1;
        caratteristicaAggiuntiva.setFont(new Font("Serif", Font.PLAIN, 16));
        pannelloDati.add(caratteristicaAggiuntiva,gbcDati);

        //Inserimento pannello targa
        gbcDati.gridy+=1;
        gbcDati.gridwidth = 2;
        gbcDati.insets = new Insets(2, 0, 5, 10);
        TargaPanel targa = new TargaPanel(veicolo.getTarga());
        pannelloDati.add(targa, gbcDati);
        //Inserimento 
        gbcDati.insets = new Insets(0, 0, 0, 10);
        gbcDati.gridwidth = 1;

        //Configurazione pulsanti
        closeButton.addActionListener(ev -> this.dispose());
        eliminaButton.addActionListener(this);
        eliminaButton.setBackground(new java.awt.Color(222, 51, 72));
        eliminaButton.setForeground(Color.WHITE);

        //ridimensionamento pulsanti
        eliminaButton.setPreferredSize(new Dimension(150,40));
        closeButton.setPreferredSize(new Dimension(150,40));

        //aggiunta pulsanti al panel
        gbcDati.fill = GridBagConstraints.HORIZONTAL;
        gbcDati.gridy+=1;
        pannelloDati.add(eliminaButton, gbcDati);
        gbcDati.gridx+=1;
        pannelloDati.add(closeButton,gbcDati);

        //aggiunta pannello dati alla finestra
        add(pannelloDati, BorderLayout.CENTER);
        
        //aggiunta dell'immagine al panel
        try {
            BufferedImage immagineAuto = ImageIO.read(new File(veicolo.getImgFilename()));
            int h = immagineAuto.getHeight();
            double rapporto = h/300.0;
            int w = (int) (immagineAuto.getWidth()/rapporto);
            
            Image immagineScal = immagineAuto.getScaledInstance(w,300, Image.SCALE_SMOOTH);
            JLabel labelImmagine = new JLabel(new ImageIcon(immagineScal));
            this.setSize(w+350,300);
            pannelloImmagine.add(labelImmagine);
        }
        catch(Exception e ) {
            e.printStackTrace();
            System.out.println("Errore immagine non trovata");
        }
        finally{
            this.setResizable(false);
        }

        //aggiunta del pannello immagine alla finestra
        add(pannelloImmagine, BorderLayout.LINE_START);

    }

    /***
     * Classe per la finestra di visualizzazione di un pannello targa in formato europeo
     */
    private class TargaPanel extends JPanel {
        //Costruttore
        TargaPanel(Targa t) {
            int altezza = 30;
            //Inizializzazione pannelli
            JPanel pannelloTarga = new JPanel();
            JPanel leftPanel = creaLeftPanel(t,altezza);
            JPanel rightPanel = new JPanel();
            JPanel centerPanel = new JPanel();

            //Definizione label
            JLabel targaLabel;


            //aggiunta pannello principale
            add(pannelloTarga);

            String numeroTarga = t.getNumero();

            //Aggiunta trattini nel caso di targa francese
            if (t.getPaese() == Paese.FRANCIA){
                numeroTarga = numeroTarga.substring(0, 2)+"-"+numeroTarga.substring(2, 5)+"-"+numeroTarga.substring(5, 7);
            }
            
            //Setup layout
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            pannelloTarga.setLayout(new BorderLayout());
            
            //Setup pannello laterale (BLU)

            rightPanel.setBackground(new Color(0,61,163,255));
            rightPanel.setPreferredSize(new Dimension(13, altezza));
            
            //Setup label numero targa

            targaLabel = new JLabel(numeroTarga);
            targaLabel.setVerticalAlignment(JLabel.TOP);
            targaLabel.setFont(new Font("Serif", Font.BOLD, altezza-4));

            //Setup pannello centale
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(new Color(255,255,255,255));
            targaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            targaLabel.setForeground(Color.BLACK);
            centerPanel.add(targaLabel);
            
            int dimensioniLabel = (int)targaLabel.getPreferredSize().getWidth();
            
            //Aggiunta pannelli
            pannelloTarga.add(leftPanel,BorderLayout.LINE_START);
            pannelloTarga.add(centerPanel,BorderLayout.CENTER);

            //Aggiunta pannello blu di destra solo per i paesi che lo richiedono
            switch(t.getPaese()){
                case ITALIA, FRANCIA:
                    pannelloTarga.add(rightPanel,BorderLayout.LINE_END);
                    dimensioniLabel+=40;
                break;
                case GERMANIA:
                    dimensioniLabel+=20;
                break; 
            }
            pannelloTarga.setPreferredSize(new Dimension(dimensioniLabel, altezza));
            pannelloTarga.setBorder(BorderFactory.createLineBorder(Color.black));
        }

        //Metodo per la creazione del pannello blu di sinistra
        private JPanel creaLeftPanel(Targa t, int altezza){
            //Setup pannello
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(new Color(0,61,163,255));
            leftPanel.setPreferredSize(new Dimension(13, altezza));
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            JLabel paeseTargaLabel = new JLabel(t.getSigla());
            paeseTargaLabel.setFont(new Font("Serif", Font.PLAIN, 8));
            paeseTargaLabel.setForeground(Color.white);
            paeseTargaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            //Aggiunta stelle UE
            try {
                BufferedImage immagineStelle = ImageIO.read(new File("immagini/stelle.png"));
                Image immagineScal = immagineStelle.getScaledInstance(14, 15, Image.SCALE_SMOOTH);
                JLabel stelleLabel = new JLabel(new ImageIcon(immagineScal));
                leftPanel.add(stelleLabel);
                stelleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            }
            catch(Exception e){

            }
            //Aggiunta sigla paese
            leftPanel.add(paeseTargaLabel);

            return leftPanel;
        }
    }

    // Listener per rimozione del veicolo dall'inventario
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==eliminaButton){
            Object[] opzioni = {"Si, sono sicuro", "No"};
            int dialogButton = JOptionPane.showOptionDialog(this, "Sei sicuro di voler rimuovere il veicolo?", "Conferma rimozione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
            if(dialogButton == JOptionPane.YES_OPTION){
                inv.rimuoviVeicolo(veicolo.getTarga());
                this.dispose();
            }   
        }
    }    
}

