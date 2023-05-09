import javax.lang.model.util.ElementScanner14;
import javax.swing.*;
import javax.xml.transform.TransformerFactoryConfigurationError;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIVeicolo extends JFrame implements ActionListener{
 
    private Inventario inv;

    private JPanel insertionPanel = new JPanel();
    private JPanel pulsantiera = new JPanel();
    
    private JLabel labelTipoVeicolo;
    private JLabel labelMarca;
    private JLabel labelModello;
    private JLabel labePaese;
    private JLabel labelNumeroTarga;
    private JLabel labelPorte;
    private JLabel labelPortata;
    private JLabel labelCilindrata;
    private JComboBox<String> dropdownVeicolo;
    private JComboBox<String> dropdownPaese;
    private JComboBox<String> dropdownPorte;
    private JTextField inserimentoMarca;
    private JTextField inserimentoModello;
    private JTextField inserimentoNumeroTarga;
    private JTextField inserimentoPortata;
    private JTextField inserimentoCilindrata;

    
    public GUIVeicolo(String titolo, Inventario inv){
        super(titolo);
        this.inv=inv;

        this.setSize(400,300);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        //definizione due panel
        insertionPanel.setLayout(new GridLayout(8,2));
        pulsantiera.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));

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

        insertionPanel.add(labelCilindrata);
        insertionPanel.add(inserimentoCilindrata);

        //definizione pulsantiera
        JButton salvaVeicolo = new JButton("Salva veicolo");

        // JButton aggiungiFoto = new JButton("aggiungi foto");
        pulsantiera.add(salvaVeicolo);
        salvaVeicolo.addActionListener(this);
        

        //aggiunta panel
        this.add(insertionPanel, BorderLayout.CENTER);
        this.add(pulsantiera, BorderLayout.PAGE_END);

        //funzionalita bottoni pulsantiera
        //salvaVeicolo.addActionListener(ev -> inVeicoli.salvaInventario("databasepy.xml"));

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String veicolo = (String)dropdownVeicolo.getSelectedItem();
        String marca = inserimentoMarca.getText();
        String modello = inserimentoModello.getText();
        String paese = (String)dropdownPaese.getSelectedItem();
        String numeroTarga = inserimentoNumeroTarga.getText();
        String porte = (String)dropdownPorte.getSelectedItem();
        String portata = inserimentoPortata.getText();
        String cilindrata = inserimentoCilindrata.getText();
        System.out.println(veicolo+" "+marca+" "+modello+" "+paese+" "+numeroTarga+" "+porte+" "+portata+" "+cilindrata);

        //TODO aggiungere controllo sui valori di portata e cilindrata per fare in modo che siano numeri
        if(veicolo.equalsIgnoreCase("Automobile"))
           inv.aggiungiVeicolo(new Automobile(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(porte)));
        else if(veicolo.equalsIgnoreCase("Camion"))
            inv.aggiungiVeicolo(new Camion(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(portata)));
        else
            inv.aggiungiVeicolo(new Moto(marca, modello, new Targa(numeroTarga, paese), Integer.parseInt(cilindrata)));
        this.dispose();
    }   
    
}
