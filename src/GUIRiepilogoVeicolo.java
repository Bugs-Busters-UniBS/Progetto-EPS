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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUIRiepilogoVeicolo extends GUI implements ActionListener {
    private JPanel pannelloDati = new JPanel(new GridLayout(7,1));
    private JPanel pannelloImmagine = new JPanel(new BorderLayout());
    private JPanel pannelloBottoni = new JPanel(new GridLayout(1, 2));
    //creazione bottone per eliminare auto
    private JButton eliminaButton = new JButton("Elimina");
    //Veicolo passato
    Veicolo veicolo;
    //inventario
    private Inventario inv;

    public GUIRiepilogoVeicolo(String titolo, Veicolo veicolo, Inventario inv) {
        super(titolo);
        this.inv= inv;
        this.veicolo=veicolo;
        this.setLayout(new BorderLayout(10,0));
        this.setSize(600,300);
        this.setResizable(false);

        //creazione label
        JLabel titoloGUI = new JLabel("Dettagli Veicolo");
        JLabel tipo = new JLabel("Tipologia: " + veicolo.getTipo());;
        JLabel marca = new JLabel("Marca: " + veicolo.getMarca());
        JLabel modello = new JLabel("Modello: " + veicolo.getModello());
        // JLabel paese = new JLabel("Paese di Provenienza: " + veicolo.getTarga().getPaese().name());
        //JLabel targa = new JLabel("Targa: " + veicolo.getTarga().getNumero());
        JLabel porte;
        JLabel portata;
        JLabel cilindrata;

        //aggiunta delle label costanti al panel
        titoloGUI.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
        pannelloDati.add(titoloGUI);
        tipo.setFont(new Font("Serif", Font.PLAIN, 16));
        pannelloDati.add(tipo);
        marca.setFont(new Font("Serif", Font.PLAIN, 16));
        pannelloDati.add(marca);
        modello.setFont(new Font("Serif", Font.PLAIN, 16));
        pannelloDati.add(modello);
        //pannelloDati.add(paese);
        

        //riconoscimento veicolo e istanziazione dei label mancanti
        if(veicolo instanceof Automobile){
            Automobile auto = (Automobile)veicolo;
            porte = new JLabel("N Porte: " + String.valueOf(auto.getNumeroPorte()));
            pannelloDati.add(porte);
            
        }
        else if(veicolo instanceof Camion){
            Camion camion = (Camion)veicolo;
            portata = new JLabel("Portata: " + String.valueOf(camion.getPortata()));
            pannelloDati.add(portata);
        }
        else if(veicolo instanceof Moto){
            Moto moto = (Moto)veicolo;
            cilindrata = new JLabel("Cilindrata: " + String.valueOf(moto.getCilindrata()));
            pannelloDati.add(cilindrata);
        }
        TargaPanel targa = new TargaPanel(veicolo.getTarga());
        pannelloDati.add(targa);

        //aggiunta del bottone di chiusura e della sua azione
        JButton closeButton = new JButton("Chiudi");
        //aggiunta azioni pulsanti
        closeButton.addActionListener(ev -> this.dispose());
        eliminaButton.addActionListener(this);
        eliminaButton.setBackground(new java.awt.Color(222, 51, 72));
        eliminaButton.setForeground(Color.WHITE);
        //aggiunta pulsanti al panel
        pannelloBottoni.add(eliminaButton);
        pannelloBottoni.add(closeButton);
        pannelloDati.add(pannelloBottoni, BorderLayout.PAGE_END);

        //aggiunta del panel al frame
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
            System.out.println("Errore immagine non trovata");
        }

        //aggiunta del panel alla gui
        add(pannelloImmagine, BorderLayout.LINE_START);

    }

    private class TargaPanel extends JPanel {

        TargaPanel(Targa t) {

            String numeroTarga = t.getNumero();
            if (t.getPaese() == Targa.Paese.FRANCIA){
                numeroTarga = numeroTarga.substring(0, 2)+"-"+numeroTarga.substring(2, 5)+"-"+numeroTarga.substring(5, 7);
            }
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel pannelloTarga = new JPanel();
            add(pannelloTarga);
            pannelloTarga.setBorder(BorderFactory.createLineBorder(Color.black));
            
            pannelloTarga.setLayout(new BorderLayout());
            JPanel leftPanel = creaLeftPanel(t);
            
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(0,61,163,255));
            rightPanel.setPreferredSize(new Dimension(13, 32));
            
            JLabel targaLabel = new JLabel(numeroTarga);
            targaLabel.setVerticalAlignment(JLabel.TOP);
            targaLabel.setFont(new Font("Serif", Font.BOLD, 20));
            targaLabel.setForeground(Color.BLACK);

            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    
            centerPanel.setBackground(new Color(255,255,255,255));
            targaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
            pannelloTarga.setPreferredSize(new Dimension(dimensioniLabel, 25));

            
        }

        private JPanel creaLeftPanel(Targa t){
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(new Color(0,61,163,255));
            leftPanel.setPreferredSize(new Dimension(13, 32));
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            JLabel paeseTargaLabel = new JLabel(t.getSigla());
            paeseTargaLabel.setFont(new Font("Serif", Font.PLAIN, 8));
            paeseTargaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            

            try {
                BufferedImage immagineStelle = ImageIO.read(new File("immagini/stelle.png"));
                Image immagineScal = immagineStelle.getScaledInstance(8, 10, Image.SCALE_SMOOTH);
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

