import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

// import per le icone
import jiconfont.swing.IconFontSwing;
import icone.GoogleMaterialDesignIcons;
import icone.Iconic;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// LAF
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class GUI extends JFrame{
    public int targaDEBUG = 0;

    //per i place holder
    String aggiunta;

    //bottone di scelta del tema
    private JButton bottoneTema;
    private boolean isDarkMode;

    //Inventario interno di veicoli
    private Inventario inVeicoli;

    //Titolo, logo
    private JLabel picLabel;
    private JLabel titoloProg;

    //Pannelli per il logo e titolo, bottoni, scrollpanel per la tabella
    private JPanel logoPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();
    private JPanel cercaPanel = new JPanel();
    private JScrollPane tabellaPanel;

    // Bottoni per aggiungere, rimuovere veicoli e salvare l'inventario
    private JButton botRimuovi;
    private JButton botAggiungi;
    private JButton botSalva;

    //Tabella, ricerca
    private TabellaInventario tabella;
    private JTextField cercaField;

    //easter egg
    private static final String VIDEO_PATH = "video/easter_egg.mp4";

    //INIZIO COSTRUTTORE
    public GUI(String titolo) {
        //Costruttore superclasse JFrame
        super(titolo);

        //Impostazione ampiezza finestra e layout manager
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(25, 15));

        //Impostazione layout manager dei pannelli
        topPanel.setLayout(new BorderLayout());
        cercaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 10));
        bottoniPanel.setLayout(new GridLayout(3, 1, 15, 15));


        //================================CREAZIONE LOGO===============================================================
        picLabel = new JLabel();
        setLogoImage("immagini/logo.png");
        logoPanel.add(picLabel);
        //===========================================================================================================


        //============================CREAZIONE LABEL CON NOME GRUPPO===============================================
        titoloProg = new JLabel("<html>Gestionale Veicoli<br/>By BugsBusters UniBS</html>");
        titoloProg.setFont(new Font("Arial", Font.ITALIC, 30));
        logoPanel.add(titoloProg);
        //===========================================================================================================


        //======================================CREAZIONE TABELLA=================================================
        //Instanziamento Inventario interno
        inVeicoli = new Inventario();
        inVeicoli.caricaInventario("database.xml");
        
        //Instanziamento tabella
        tabella = new TabellaInventario(inVeicoli);
        //Permette di rendere abilitato il pulsante di rimozione solo quando una riga è effettivamente selezionata
        tabella.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent ev) {
                if(tabella.getCheckedRows().size() == 0) {
                    botRimuovi.setEnabled(false);
                }
                else {
                    botRimuovi.setEnabled(true);
                }
            }
        });
        //impostazione dimensioni dei pulsanti elimina e dettagli
        tabella.getColumnModel().getColumn(5).setPreferredWidth(20);
        tabellaPanel = new JScrollPane(tabella);
        //=======================================================================================================


        //===================================BOTTONE AGGIUNTA NUOVO VEICOLO===================================
        botAggiungi = new JButton("Aggiungi nuovo veicolo");
        botAggiungi.setBackground(new java.awt.Color(55, 90, 129));
        botAggiungi.setForeground(Color.WHITE);
        //Azione di botAggiungi
        botAggiungi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta Veicoli", inVeicoli);
                GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta veicolo", inVeicoli);
                //aggiorna la tabella dopo aver aggiunto il veicolo
                addGUI.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        tabella.updateTable();
                    }
                });
                addGUI.setVisible(true);
            }
        });
        //===================================================================================================


        //====================BOTTONE SALVATAGGIO INVENTARIO=================================================
        botSalva = new JButton("Salva modifiche all'inventario");
        botSalva.setBackground(new java.awt.Color(55, 90, 129));
        botSalva.setForeground(Color.WHITE);
        //Azione di botSalva
        botSalva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inVeicoli.salvaInventario("database.xml");
                JOptionPane.showMessageDialog(null,"Inventario salvato correttamente");
        }});
        //===================================================================================================

        //====================BOTTONE SWICH TEMA INVENTARIO===========================
        IconFontSwing.register(Iconic.getIconFont());
        Icon iconaLuna = IconFontSwing.buildIcon(Iconic.MOON_FILL, 20, new Color(0, 0, 0));
        bottoneTema = new JButton(iconaLuna);
        bottoneTema.setOpaque(false);
        bottoneTema.setContentAreaFilled(false);
        bottoneTema.setBorderPainted(false);
        bottoneTema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleLookAndFeel();
            }
        });
        //=============================================================================

        //====================CASELLA DI RICERCA E BOTTONI E LABEL==============================
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        Icon iconaLente = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SEARCH, 20, Color.BLACK);
        cercaField = new JTextField(20);
        JLabel cercaLabel= new JLabel(iconaLente);
        JButton pulisciTesto = new JButton("Pulisci");
        cercaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(cercaField.getText().equalsIgnoreCase("RickRoll")){
                    getVideo("https://www.youtube.com/watch?v=dQw4w9WgXcQ");    
                }
                filterRows(cercaField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterRows(cercaField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterRows(cercaField.getText());
            }
        });

        pulisciTesto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                cercaField.setText("");
            }
            
        });
        cercaPanel.add(cercaLabel);
        cercaPanel.add(cercaField);
        cercaPanel.add(pulisciTesto);
        //===============================================================================================


        //===============================BOTTONE RIMOZIONE VEICOLI=======================================
        botRimuovi = new JButton("Rimuovi veicoli selezionati");
        botRimuovi.setBackground((new java.awt.Color(222, 51, 72)));
        botRimuovi.setForeground(Color.WHITE);

        //Inizialmente non abilitato
        botRimuovi.setEnabled(false);

        botRimuovi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String[] opzioni = {"Si, Sono sicuro", "No"};
                int scelta = JOptionPane.showOptionDialog(botRimuovi, "Sei sicuro di voler rimuovere i veicoli selezionati?", "Scelta", 
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, null);
                
                if(scelta == JOptionPane.YES_OPTION) {
                    //Ottiene una lista delle righe dalla tabella
                    ArrayList<Integer> selected = tabella.getCheckedRows();
                    //Cicla su tutte le righe selezionate dalla checkbox
                    for(int rowIndex : selected) {
                        //Ottiene il numero di targa e paese dalla tabella
                        String numero = (String)tabella.getModel().getValueAt(rowIndex, 3);
                        String paese = (String)tabella.getModel().getValueAt(rowIndex, 4);

                        try {
                            //Rimuove il veicolo identificandolo dalla targa
                            Targa targaRimozione = new Targa(numero, paese);
                            inVeicoli.rimuoviVeicolo(targaRimozione);
                        }
                        catch(TargaException ex) {
                            System.out.println("Errore nella rimozione della targa tramite checkbox!");
                        }
                       
                    }
                    //Infine aggiorna la tabella
                    tabella.updateTable();
                }
            }
        });
        //===============================================================================

        //Infine:
        //Composizione di topPanel....
        topPanel.add(logoPanel, BorderLayout.PAGE_START);
        topPanel.add(cercaPanel, BorderLayout.LINE_START);
        topPanel.add(bottoneTema, BorderLayout.LINE_END);

        //...di bottoniPanel ...    
        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botSalva);
        bottoniPanel.add(botRimuovi);

        //... e della finestra principale
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottoniPanel, BorderLayout.WEST);
        this.add(tabellaPanel, BorderLayout.CENTER);
    }
    //FINE COSTRUTTORE


    private void filterRows(String searchText) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabella.getTabellaInventarioModel());
        tabella.setRowSorter(sorter);

        RowFilter<TableModel, Object> rowFilter = RowFilter.regexFilter("(?i)" + searchText);
        sorter.setRowFilter(rowFilter);
    }

    private void toggleLookAndFeel() {
        Icon iconaSole = IconFontSwing.buildIcon(Iconic.SUN_FILL, 20, new Color(255,213,0));
        Icon iconaLuna = IconFontSwing.buildIcon(Iconic.MOON_FILL, 20, new Color(0, 0, 0));
        try {
            if (isDarkMode) {
                UIManager.setLookAndFeel(new FlatMacLightLaf());
                bottoneTema.setIcon(iconaLuna);
                setLogoImage("immagini/logo.png");
                isDarkMode = false;
            } else {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                bottoneTema.setIcon(iconaSole);
                setLogoImage("immagini/logo_white.png");
                isDarkMode = true;
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void setLogoImage(String path){
        try {
            BufferedImage logo = ImageIO.read(new File(path));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            picLabel.setIcon(new ImageIcon(logoScal));
            
        }
        catch(Exception e ) {
            System.out.println("Errore nel caricamento del logo!");
        }
    }
    
    private void getVideo(String URL){
        try {
            Desktop.getDesktop().browse(new URI(URL));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
