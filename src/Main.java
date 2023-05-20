// import GUI
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// import LAF
import javax.swing.UIManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UnsupportedLookAndFeelException;

public class Main{
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        
        // Settaggio immagine logo del programma
        ImageIcon logo = new ImageIcon("immagini/logo.png");
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        // GUI principale e completa
        GUI myGui = new GUI("Gestionale Veicoli");
        myGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGui.setVisible(true);
        myGui.setIconImage(logo.getImage());
    }

}