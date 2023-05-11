import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUIRiepilogoVeicolo extends JFrame {

    //TODO fixare targa

    private JPanel pannelloDati = new JPanel(new GridLayout(7,1));
    private JPanel pannelloImmagine = new JPanel(new BorderLayout());

    public GUIRiepilogoVeicolo(String titolo, Veicolo veicolo) {
        super(titolo);
        this.setLayout(new BorderLayout(10,0));
        this.setSize(600,300);
        this.setResizable(false);

        //creazione label
        JLabel titoloGUI = new JLabel("Dettagli Veicolo");
        JLabel tipo = new JLabel("Tipologia: " + veicolo.getTipo());;
        JLabel marca = new JLabel("Marca: " + veicolo.getMarca());
        JLabel modello = new JLabel("Modello: " + veicolo.getModello());
        JLabel paese = new JLabel("Paese di Provenienza: " + veicolo.getTarga().getPaese().name());
        //JLabel targa = new JLabel("Targa: " + veicolo.getTarga().getNumero());
        JLabel porte;
        JLabel portata;
        JLabel cilindrata;

        //creazione bottone di chiusura
        JButton closeButton = new JButton("Chiudi");

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
        pannelloDati.add(closeButton, BorderLayout.PAGE_END);
        closeButton.addActionListener(ev -> this.dispose());

        //aggiunta del panel al frame
        add(pannelloDati, BorderLayout.CENTER);
        

        //aggiunta dell'immagine al panel
        try {
            BufferedImage immagineAuto = ImageIO.read(new File("auto_place_holder.png"));
            Image immagineScal = immagineAuto.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel labelImmagine = new JLabel(new ImageIcon(immagineScal));
            pannelloImmagine.add(labelImmagine);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        //aggiunta del panel alla gui
        add(pannelloImmagine, BorderLayout.LINE_START);

    }

    private class TargaPanel extends JPanel {

        TargaPanel(Targa t) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            JPanel pannelloTarga = new JPanel();
            add(pannelloTarga);
            pannelloTarga.setBorder(BorderFactory.createLineBorder(Color.black));
            pannelloTarga.setPreferredSize(new Dimension(150, 35));
            pannelloTarga.setLayout(new BorderLayout());
            JPanel leftPanel = new JPanel();
            leftPanel.setBackground(new Color(0,61,163,255));
            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(0,61,163,255));
            JLabel targaLabel = new JLabel(t.getNumero());
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(new Color(255,255,255,255));
            centerPanel.add(targaLabel);
            pannelloTarga.add(centerPanel,BorderLayout.CENTER);
    
            pannelloTarga.add(leftPanel,BorderLayout.LINE_START);
            pannelloTarga.add(rightPanel,BorderLayout.LINE_END);
        }
    }
}