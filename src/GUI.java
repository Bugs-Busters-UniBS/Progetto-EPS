import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

// import per le icone
import jiconfont.swing.IconFontSwing;
import icone.FontAwesome;
import icone.GoogleMaterialDesignIcons;
import icone.Iconic;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

// LAF
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class GUI extends JFrame{
    public int targaDEBUG = 0;

    //bottone di scelta del tema
    private JButton bottoneTema;
    private boolean isDarkMode = false;

    //tabella
    private TabellaInventario tabella;
    private JTextField cercaField;

    //Inventario interno di veicoli
    private Inventario inVeicoli;

    //Pannelli per il logo e titolo, bottoni, tabella
    private JPanel logoPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottoniPanel = new JPanel();
    private JPanel swichTema = new JPanel();
    private JPanel cercaPanel = new JPanel();
    private JScrollPane tabellaPanel;

    public GUI(String titolo) {
        //Costruttore superclasse JFrame
        super(titolo);

        //per le icone
        IconFontSwing.register(Iconic.getIconFont());

        //Impostazione ampiezza finestra e layout manager
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(25, 15));

        //Impostazione layout manager dei pannelli
        topPanel.setLayout(new BorderLayout());
        cercaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 10));
        bottoniPanel.setLayout(new GridLayout(3, 1, 15, 15));

        //Inserimento Logo UniBS
        try {
            BufferedImage logo = ImageIO.read(new File("immagini/logo.png"));
            Image logoScal = logo.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(logoScal));

            // Composizione logoPanel
            logoPanel.add(picLabel);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }

        //LABEL CON NOME DEL GRUPPO
        JLabel titoloProg = new JLabel("<html>Gestionale Veicoli<br/>By BugsBusters UniBS</html>");
        titoloProg.setFont(new Font("Arial", Font.ITALIC, 30));
        logoPanel.add(titoloProg);

        //======================================CREAZIONE TABELLA=================================================
        //Instanziamento Inventario interno
        this.inVeicoli = new Inventario();
        inVeicoli.caricaInventario("database.xml");
        //tabella
        tabella = new TabellaInventario(inVeicoli);
        //impostazione dimensioni dei pulsanti elimina e dettagli
        TableColumn colonna = tabella.getColumnModel().getColumn(5);
        colonna.setPreferredWidth(20);
        tabellaPanel = new JScrollPane(tabella);
        //=======================================================================================================


        //===================================BOTTONE AGGIUNTA NUOVO VEICOLO===================================
        JButton botAggiungi = new JButton("Aggiungi nuovo veicolo");
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


        //====================BOTTONE SALVATAGGIO INVENTARIO=============================
        JButton botSalva = new JButton("Salva modifiche all'inventario");
        botSalva.setBackground(new java.awt.Color(55, 90, 129));
        botSalva.setForeground(Color.WHITE);
        botSalva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inVeicoli.salvaInventario("database.xml");
                JOptionPane.showMessageDialog(null,"Inventario salvato correttamente");
        }});
        //=============================================================================

        //====================BOTTONE SWICH TEMA INVENTARIO===========================
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
        cercaField = new JTextField(20);
        JLabel cercaLabel= new JLabel("Cerca:");
        JButton pulisciTesto = new JButton("Pulisci");
        cercaField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
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
        //==============================================================================

        //====================BOTTONE RIMOZIONE VEICOLI=================================
        JButton botRimuovi = new JButton("Rimuovi veicoli selezionati");
        botRimuovi.setBackground((new java.awt.Color(222, 51, 72)));
        botRimuovi.setForeground(Color.WHITE);
        botRimuovi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String[] opzioni = {"Si, Sono sicuro", "No"};
                int scelta = JOptionPane.showOptionDialog(botRimuovi, "Sei sicuro di voler rimuovere i veicoli selezionati?", "Scelta", 
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, null);
                
                if(scelta == JOptionPane.YES_OPTION) {
                    //Ottiene una lista delle righe dalla tabella
                    TableModel model = tabella.getModel();
                    int numRows = model.getRowCount();
                    //Per ogni riga controlla se è selezionata e ne ottiene la targa
                    for(int i=0; i<numRows; i++) {
                        //Rimuove il veicolo dalla targa ottenuta se selezionato
                        if((Boolean)model.getValueAt(i, 5) == true) {
                            String numero = (String)model.getValueAt(i, 3);
                            String paese = (String)model.getValueAt(i, 4);
                            try {
                                Targa targaRimozione = new Targa(numero, paese);
                                inVeicoli.rimuoviVeicolo(targaRimozione);
                            }
                            catch(TargaException ex) {
                                System.out.println("Errore nella rimozione della targa tramite checkbox!");
                            }
                        }       
                    }
                    //Infine aggiorna la tabella
                    tabella.updateTable();
                }
            }
        });
        //===============================================================================

        // Agggiunta campo di ricerca
        // JLabel filtroText = new JLabel("Cerca:");
        // JTextField barraRicerca = new JTextField("", 15);

        //top panel
        topPanel.add(logoPanel, BorderLayout.PAGE_START);
        topPanel.add(cercaPanel, BorderLayout.LINE_START);
        topPanel.add(bottoneTema, BorderLayout.LINE_END);

        // Composizione dei pannelli ...    
        bottoniPanel.add(botAggiungi);
        bottoniPanel.add(botSalva);
        bottoniPanel.add(botRimuovi);

        // ... e della finestra principale
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottoniPanel, BorderLayout.WEST);
        this.add(tabellaPanel, BorderLayout.CENTER);
    }

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
                isDarkMode = false;
            } else {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
                bottoneTema.setIcon(iconaSole);
                isDarkMode = true;
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
