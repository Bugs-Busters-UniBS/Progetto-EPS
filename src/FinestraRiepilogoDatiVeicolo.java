import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinestraRiepilogoDatiVeicolo extends JFrame {
    private final static String AUTO_PLACEHOLDER_PATH = "auto_place_holder.png";
    private final static String MOTO_PLACEHOLDER_PATH = "moto_place_holder.png";
    private final static String CAMION_PLACEHOLDER_PATH = "camion_place_holder.png";
    private JPanel pannelloDati = new JPanel(new BorderLayout());

    public FinestraRiepilogoDatiVeicolo() {
        setLayout(new BorderLayout());
        try {
            BufferedImage immagine = ImageIO.read(new File(AUTO_PLACEHOLDER_PATH));
            Image immagineScal = immagine.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(immagineScal));
            add(picLabel,BorderLayout.LINE_START);
            
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
