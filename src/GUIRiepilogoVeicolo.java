import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIRiepilogoVeicolo extends JFrame {

    //TODO fixare targa

    private JPanel pannelloDati = new JPanel(new GridLayout(8,1));
    private JPanel pannelloImmagine = new JPanel(new BorderLayout());

    public GUIRiepilogoVeicolo(String titolo, Veicolo veicolo) {
        super(titolo);
        this.setLayout(new BorderLayout(10,0));
        this.setSize(500,300);
        this.setResizable(false);

        //creazione label
        JLabel titoloGUI = new JLabel("Dettagli Veicolo");
        JLabel tipo = new JLabel("Tipologia: " + veicolo.getTipo());;
        JLabel marca = new JLabel("Marca: " + veicolo.getMarca());
        JLabel modello = new JLabel("Modello: " + veicolo.getModello());
        JLabel paese = new JLabel("Paese di Provenienza: " + veicolo.getTarga().getPaese().name());
        JLabel targa = new JLabel("Targa: " + veicolo.getTarga().getNumero());
        JLabel porte;
        JLabel portata;
        JLabel cilindrata;

        //creazione bottone di chiusura
        JButton closeButton = new JButton("Chiudi");

        //aggiunta delle label costanti al panel
        titoloGUI.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
        pannelloDati.add(titoloGUI, BorderLayout.PAGE_START);
        pannelloDati.add(tipo);
        pannelloDati.add(marca);
        pannelloDati.add(modello);
        pannelloDati.add(paese);
        pannelloDati.add(targa);

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

        //aggiunta del bottone di chiusura e della sua azione
        pannelloDati.add(closeButton);
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
}
