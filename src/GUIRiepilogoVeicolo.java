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

import org.xml.sax.SAXException;


public class GUIRiepilogoVeicolo extends JFrame implements ActionListener {
    private JPanel pannelloDati = new JPanel(new GridBagLayout());
    private JPanel pannelloImmagine = new JPanel(new BorderLayout());
    // private JPanel pannelloBottoni = new JPanel(new GridLayout(1, 2));
    GridBagConstraints gbcDati = new GridBagConstraints();
    //creazione bottone per eliminare auto
    private JButton eliminaButton = new JButton("Elimina");
    //Veicolo passato
    Veicolo veicolo;
    //inventario
    private Inventario inv;

    public <T extends Veicolo> GUIRiepilogoVeicolo(String titolo, T veicolo, Inventario inv) {
        super(titolo);
        this.inv= inv;
        this.veicolo=veicolo;
        this.setLayout(new BorderLayout(10,0));
        this.setSize(650,300);
        this.setResizable(false);

        //creazione label
        JLabel titoloGUI = new JLabel("Dettagli Veicolo");
        JLabel tipo = new JLabel("Tipologia: " + veicolo.getTipo());;
        JLabel marca = new JLabel("Marca: " + veicolo.getMarca());
        JLabel modello = new JLabel("Modello: " + veicolo.getModello());
        JLabel caratteristicaAggiuntiva = new JLabel();
    
        //aggiunta delle label costanti al panel
        gbcDati.anchor = GridBagConstraints.FIRST_LINE_START;
        // gbcDati.fill = GridBagConstraints.BOTH;
        gbcDati.insets = new Insets(5, 0, 0, 10);

        titoloGUI.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
        gbcDati.gridx = 0;
        gbcDati.gridy = 0;
        pannelloDati.add(titoloGUI, gbcDati);
        // pannelloDati.add(titoloGUI);
        tipo.setFont(new Font("Serif", Font.PLAIN, 16));
        gbcDati.gridy += 1;
        pannelloDati.add(tipo,gbcDati);
        marca.setFont(new Font("Serif", Font.PLAIN, 16));
        gbcDati.gridy += 1;
        pannelloDati.add(marca,gbcDati);
        modello.setFont(new Font("Serif", Font.PLAIN, 16));
        gbcDati.gridy += 1;
        pannelloDati.add(modello,gbcDati);
        

        // riconoscimento veicolo e istanziazione dei label mancanti
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

        gbcDati.gridy+=1;
        gbcDati.gridwidth = 2;
        TargaPanel targa = new TargaPanel(veicolo.getTarga());
        pannelloDati.add(targa, gbcDati);
        gbcDati.gridwidth = 1;
        //aggiunta del bottone di chiusura e della sua azione
        JButton closeButton = new JButton("Chiudi");
        // //aggiunta azioni pulsanti
        closeButton.addActionListener(ev -> this.dispose());
        eliminaButton.addActionListener(this);
        eliminaButton.setPreferredSize(new Dimension(150,40));
        closeButton.setPreferredSize(new Dimension(150,40));

        // //aggiunta pulsanti al panel
        // pannelloBottoni.add(eliminaButton);
        // pannelloBottoni.add(closeButton);
        // pannelloDati.add(pannelloBottoni, BorderLayout.PAGE_END);
        gbcDati.fill = GridBagConstraints.HORIZONTAL;
        gbcDati.gridy+=1;
        pannelloDati.add(eliminaButton, gbcDati);
        gbcDati.gridx+=1;
        pannelloDati.add(closeButton,gbcDati);
        // //aggiunta del panel al frame

        
        add(pannelloDati, BorderLayout.CENTER);
        

        //aggiunta dell'immagine al panel
        try {
            BufferedImage immagineAuto = ImageIO.read(new File(veicolo.getImgFilename()));
            Image immagineScal = immagineAuto.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel labelImmagine = new JLabel(new ImageIcon(immagineScal));
            pannelloImmagine.add(labelImmagine);
        }
        catch(Exception e ) {
            e.printStackTrace();
            try{
                String fileSostitutivo = null;
                if(veicolo instanceof Automobile)
                    fileSostitutivo = "immagini/auto_place_holder.png"; 
                else if(veicolo instanceof Camion)
                    fileSostitutivo = "immagini/camion_place_holder.png"; 
                else if(veicolo instanceof Moto) 
                    fileSostitutivo = "immagini/moto_place_holder.png";
                
                BufferedImage immagineAutoStandard = ImageIO.read(new File(fileSostitutivo));
                Image immagineStandardScal = immagineAutoStandard.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                JLabel labelImmagine = new JLabel(new ImageIcon(immagineStandardScal));
                pannelloImmagine.add(labelImmagine);
            }
            catch(Exception ec){
                ec.printStackTrace();
            }
        }

        //aggiunta del panel alla gui
        add(pannelloImmagine, BorderLayout.LINE_START);

    }

    private class TargaPanel extends JPanel {

        TargaPanel(Targa t) {
            int altezza = 30;

            String numeroTarga = t.getNumero();
            if (t.getPaese() == Targa.Paese.FRANCIA){
                numeroTarga = numeroTarga.substring(0, 2)+"-"+numeroTarga.substring(2, 5)+"-"+numeroTarga.substring(5, 7);
            }
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel pannelloTarga = new JPanel();
            add(pannelloTarga);
            
            pannelloTarga.setLayout(new BorderLayout());
            JPanel leftPanel = creaLeftPanel(t,altezza);
            
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(0,61,163,255));
            rightPanel.setPreferredSize(new Dimension(13, altezza));
            
            JLabel targaLabel = new JLabel(numeroTarga);
            targaLabel.setVerticalAlignment(JLabel.TOP);
            targaLabel.setFont(new Font("Serif", Font.BOLD, altezza-4));

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    
            centerPanel.setBackground(new Color(255,255,255,255));
            targaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            targaLabel.setForeground(Color.BLACK);

            centerPanel.add(targaLabel);
            pannelloTarga.add(centerPanel,BorderLayout.CENTER);
            int dimensioniLabel = (int)targaLabel.getPreferredSize().getWidth();

            pannelloTarga.add(leftPanel,BorderLayout.LINE_START);
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

        private JPanel creaLeftPanel(Targa t, int altezza){
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(new Color(0,61,163,255));
            leftPanel.setPreferredSize(new Dimension(13, altezza));
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            JLabel paeseTargaLabel = new JLabel(t.getSigla());
            paeseTargaLabel.setFont(new Font("Serif", Font.PLAIN, 8));
            paeseTargaLabel.setForeground(Color.white);
            paeseTargaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            

            try {
                BufferedImage immagineStelle = ImageIO.read(new File("immagini/stelle.png"));
                Image immagineScal = immagineStelle.getScaledInstance(14, 15, Image.SCALE_SMOOTH);
                JLabel stelleLabel = new JLabel(new ImageIcon(immagineScal));
                leftPanel.add(stelleLabel);
                stelleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            }
            catch(Exception e){

            }

            leftPanel.add(paeseTargaLabel);

            return leftPanel;
        }
    }

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

