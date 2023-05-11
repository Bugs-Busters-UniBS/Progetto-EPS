import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FinestraDettagli extends JDialog {
    private JPanel pannelloDati = new JPanel(new BorderLayout());

    public FinestraDettagli(String filename) {
        setLayout(new BorderLayout());
        try {
            BufferedImage immagine = ImageIO.read(new File(filename));
            Image immagineScal = immagine.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(immagineScal));
            add(picLabel,BorderLayout.LINE_START);
            
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
        setSize(500, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
