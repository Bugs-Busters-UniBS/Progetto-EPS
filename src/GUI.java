// data
import java.util.ArrayList;

// gui import
import java.awt.*;
import javax.swing.*;

// table import
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// event import
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

// image
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// easter import
import java.net.URI;
import java.net.URISyntaxException;

// import per le icone
import jiconfont.swing.IconFontSwing;
import icone.GoogleMaterialDesignIcons;
import icone.Iconic;

// LAF
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

/***
 * Classe GUI principale destinata alle aperture degli altri panel di interazione con l'utente
 */
public class GUI extends JFrame{
    public int targaDEBUG = 0;

    // Titolo, logo
    private JLabel picLabel;
    private JLabel titoloProg;

    // Bottone e booleano di scelta tema
    private JButton bottoneTema;
    private boolean isDarkMode;

    // Inventario interno di veicoli
    private Inventario inVeicoli;

    // Pannelli per il logo e titolo, bottoni, ricerca, scrollpanel per la tabella
    private JPanel logoPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();
    private JPanel cercaPanel = new JPanel();
    private JScrollPane tabellaPanel;

    // Bottoni per aggiungere, rimuovere veicoli e salvare l'inventario
    private JButton botRimuovi;
    private JButton botAggiungi;
    private JButton botSalva;

    // Tabella 
    private TabellaInventario tabella;

    // Ricerca nella tabella
    private JTextField cercaField;
    private JLabel cercaLabel;
    private JButton pulisciTesto;

    // INIZIO COSTRUTTORE
    public GUI(String titolo) {
        // Costruttore superclasse JFrame
        super(titolo);

        // Impostazione ampiezza finestra e layout manager
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(25, 15));

        // Impostazione layout manager dei pannelli
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
        //==========================================================================================================


        //======================================CREAZIONE TABELLA===================================================
        // Instanziamento Inventario interno
        inVeicoli = new Inventario();
        inVeicoli.caricaInventario("database.xml");
        
        // Instanziamento tabella
        tabella = new TabellaInventario(inVeicoli);

        // Permette di rendere abilitato il pulsante di rimozione solo quando una riga è effettivamente selezionata
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

        // impostazione dimensioni dei pulsanti elimina e dettagli
        tabella.getColumnModel().getColumn(5).setPreferredWidth(20);
        tabellaPanel = new JScrollPane(tabella);
        //==========================================================================================================


        //===================================BOTTONE AGGIUNTA NUOVO VEICOLO=========================================
        // Instanziamento Bottone
        botAggiungi = new JButton("Aggiungi nuovo veicolo");

        // Selezione colori
        botAggiungi.setBackground(new java.awt.Color(55, 90, 129));
        botAggiungi.setForeground(Color.WHITE);

        //Azione di botAggiungi
        botAggiungi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Aggiunta di un nuovo veicolo
                GUIAggiuntaVeicolo addGUI = new GUIAggiuntaVeicolo("Aggiunta veicolo", inVeicoli);

                // Aggiorno la tabella dopo aver aggiunto il veicolo
                addGUI.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        tabella.updateTable();
                    }
                });
                addGUI.setVisible(true);
            }
        });
        //=======================================================================================================


        //====================BOTTONE SALVATAGGIO INVENTARIO=====================================================
        // Instanziamento bottone
        botSalva = new JButton("Salva modifiche all'inventario");
        
        // Selezione Colori
        botSalva.setBackground(new java.awt.Color(55, 90, 129));
        botSalva.setForeground(Color.WHITE);

        // Azione di botSalva
        botSalva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inVeicoli.salvaInventario("database.xml");
                JOptionPane.showMessageDialog(null,"Inventario salvato correttamente");
        }});
        //=====================================================================================================

        //====================BOTTONE SWICH TEMA INVENTARIO====================================================
        // Istanziamento libreria da cui prelevare l'icona
        IconFontSwing.register(Iconic.getIconFont());

        // Creazione icona
        Icon iconaLuna = IconFontSwing.buildIcon(Iconic.MOON_FILL, 20, new Color(0, 0, 0));

        // Instanziamento bottone
        bottoneTema = new JButton(iconaLuna);

        // Rimozione bordi
        bottoneTema.setOpaque(false);
        bottoneTema.setContentAreaFilled(false);
        bottoneTema.setBorderPainted(false);

        // Azione bottone
        bottoneTema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleLookAndFeel();
            }
        });
        //===================================================================================================

        //====================CASELLA DI RICERCA E BOTTONI E ICONA===========================================
        // Istanziamento libreria da cui prelevare l'icona
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());

        // Creazione icona
        Icon iconaLente = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SEARCH, 20, Color.BLACK);

        // Istanziamento e dimensionamento della JTextField di ricerca
        cercaField = new JTextField(20);

        // Creazione label con icona lente
        cercaLabel= new JLabel(iconaLente);

        // Creazione bottone di pulizia
        pulisciTesto = new JButton("Pulisci");

        // Creazione di DocumentListener() per la ricerca
        cercaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // easter add
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

        // Azione bottone di pulizia
        pulisciTesto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                cercaField.setText("");
            }
            
        });
        //===============================================================================================


        //===============================BOTTONE RIMOZIONE VEICOLI=======================================
        // Istanziamento Bottone rimozione
        botRimuovi = new JButton("Rimuovi veicoli selezionati");

        // Selezione Colori
        botRimuovi.setBackground((new java.awt.Color(222, 51, 72)));
        botRimuovi.setForeground(Color.WHITE);

        //Inizialmente non abilitato
        botRimuovi.setEnabled(false);

        // Azione bottone rimuovi
        botRimuovi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Istanziamento scelte per il JOptionPane
                String[] opzioni = {"Si, Sono sicuro", "No"};
                int scelta = JOptionPane.showOptionDialog(botRimuovi, "Sei sicuro di voler rimuovere i veicoli selezionati?", "Scelta", 
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, null);
                
                // Analisi della scelta
                if(scelta == JOptionPane.YES_OPTION) {
                    // Ottiene una lista delle righe dalla tabella
                    ArrayList<Integer> selected = tabella.getCheckedRows();

                    // Cicla su tutte le righe selezionate dalla checkbox
                    for(int rowIndex : selected) {
                        // Ottiene il numero di targa e paese dalla tabella
                        String numero = (String)tabella.getModel().getValueAt(rowIndex, 3);
                        String paese = (String)tabella.getModel().getValueAt(rowIndex, 4);

                        try {
                            // Rimuove il veicolo identificandolo dalla targa
                            Targa targaRimozione = new Targa(numero, paese);
                            inVeicoli.rimuoviVeicolo(targaRimozione);
                        }
                        catch(TargaException ex) {
                            System.out.println("Errore nella rimozione della targa tramite checkbox!");
                        }
                       
                    }
                    // Infine aggiorna la tabella
                    tabella.updateTable();
                }
            }
        });
        //===========================================================================================

        // Infine:
        // Composizione di topPanel....
        topPanel.add(logoPanel, BorderLayout.PAGE_START);
        topPanel.add(cercaPanel, BorderLayout.LINE_START);
        topPanel.add(bottoneTema, BorderLayout.LINE_END);

        // ...di cercaPanel ...
        cercaPanel.add(cercaLabel);
        cercaPanel.add(cercaField);
        cercaPanel.add(pulisciTesto);

        // ...di bottoniPanel ...    
        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botSalva);
        bottoniPanel.add(botRimuovi);

        //... e della finestra principale
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottoniPanel, BorderLayout.WEST);
        this.add(tabellaPanel, BorderLayout.CENTER);
    }
    //FINE COSTRUTTORE

    // Metodo di filtraggio per la ricerca nella tabella
    private void filterRows(String testoCercato) {
        // Definizione e istanziamento del Sorter sul modello della tabella
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabella.getTabellaInventarioModel());
        tabella.setRowSorter(sorter);

        // Definizione e istanziamento del Filtro del sorter sulle righe della tabella
        RowFilter<TableModel, Object> filtroRighe = RowFilter.regexFilter("(?i)" + testoCercato);
        sorter.setRowFilter(filtroRighe);
    }

    // Metodo per il toggle del LAF da chiaro a scuro e viceversa
    private void toggleLookAndFeel() {
        // Istanziamento libreria da cui prelevare l'icona
        IconFontSwing.register(Iconic.getIconFont());

        // Definizione e istanziamento delle icone
        Icon iconaSole = IconFontSwing.buildIcon(Iconic.SUN_FILL, 20, new Color(255,213,0));
        Icon iconaLuna = IconFontSwing.buildIcon(Iconic.MOON_FILL, 20, new Color(0, 0, 0));

        // Swiching tra temi
        try {
            if (isDarkMode) {
                // Selezione tema
                UIManager.setLookAndFeel(new FlatMacLightLaf());

                // Swiching dell'icona del bottone
                bottoneTema.setIcon(iconaLuna);
                
                // Swiching del logo
                setLogoImage("immagini/logo.png");
                isDarkMode = false;
            } else {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                bottoneTema.setIcon(iconaSole);
                setLogoImage("immagini/logo_white.png");
                isDarkMode = true;
            }

            // Refresh del contenuto
            SwingUtilities.updateComponentTreeUI(this);

        // Catch di eventuali exeption riguardanti il tema
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    // Metodo per il settaggio del Logo del programma
    private void setLogoImage(String path){
        try {
            // Import del logo
            BufferedImage logo = ImageIO.read(new File(path));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

            // Set del logo nella label
            picLabel.setIcon(new ImageIcon(logoScal));
        }
        // Catch di eventuali errori con il logo
        catch(Exception e ) {
            System.out.println("Errore nel caricamento del logo!");
        }
    }
    
    // Metodo per la apertura di link nel broswer predefinito sul pc Utente
    // PS: non serve a nulla se non per easter egg
    private void getVideo(String URI){
        try {
            // Apertura del Broswer predefinto
            Desktop.getDesktop().browse(new URI(URI));

        // Raccolra di eventuali errori di input output
        } catch (IOException e1) {
            e1.printStackTrace();
        
        // Raccolta di eventuali errori di sintassi dell'URI
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
