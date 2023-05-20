import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class TabellaInventario extends JTable{
    private TabellaInventarioModel modello;
    private Inventario inv;

    public TabellaInventario(Inventario inv) {
        super();
        this.inv = inv;

        getToolTipText();

        modello = new TabellaInventarioModel(inv);

        setCellSelectionEnabled(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.setModel(modello);
        //Rimpicciolisce colonna checkbox
        this.getColumnModel().getColumn(5).setPreferredWidth(15);
        this.tableHeader.setReorderingAllowed(false);
        // render alternato piu' leggibile
        this.setDefaultRenderer(Object.class, new ColoreAlternatoRigheTabella());
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    JTable target = (JTable)me.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    visualizzaVeicolo(row, column);
                }
            }
         });
        
    }

    private void visualizzaVeicolo(int row, int col) {
        //se non sono nella colonna della checkbox
        if(col != 5){
            //Trovo l'indice di ciò che sto visualizzando in tabella
            int modelIndex = this.convertRowIndexToModel(row);
            Veicolo v = inv.getVeicoloDaTarga(modello.getValueAt(modelIndex, 3).toString());
            GUIRiepilogoVeicolo guiDettagli = new GUIRiepilogoVeicolo(v.getMarca()+" "+v.getModello(), v, inv, this.getColumnModel().getColomn(1));
            //aggiorna la tabella dopo aver eliminato il veicolo
            guiDettagli.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    updateTable();
                }
            });
            guiDettagli.setVisible(true);          
        }

    }
    public void addRow(Veicolo vec) {
        try {
            inv.aggiungiVeicolo(vec);
        } catch (TargaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        updateTable();
    }

    public void updateTable() {
        this.modello.refresh(inv);
        //Rimpicciolisce colonna checkbox
        this.getColumnModel().getColumn(5).setPreferredWidth(15);

    }
    public TabellaInventarioModel getTabellaInventarioModel(){
        return this.modello;
    }
    
    public Inventario getInventario(){
        return inv;
    }
    
    public String getToolTipText(MouseEvent e) {
        int colIndex = columnAtPoint(e.getPoint());
        if (colIndex != 5) // tutte le colonne 
            return "Doppio click per visualizzare ulteriori dettagli";
        else // solo colonna checkbox
            return "Seleziona uno o più veicoli per rimuoverli successivamente";
    }

    //Ottiene gli indici delle righe selezionate dalla checkbox sottoforma di ArrayList di interi
    public ArrayList<Targa> getCheckedTarghe() {
        ArrayList<Targa> targhe = new ArrayList<Targa>();

        int rowNum = this.getRowCount();

        for(int i = 0; i < rowNum; i++) {
            if((Boolean)getValueAt(i, 5) == true) {
                String numero = (String)getValueAt(i, 3);
                String paese = (String)getValueAt(i, 4);

                try {
                    Targa targaRimozione = new Targa(numero, paese);
                    targhe.add(targaRimozione);
                } catch (TargaException e) {
                    System.out.println("Errore nella rimozione della targa!");
                }
            }
        }

        return targhe;
    }

    public int howManyChecked() {
        int num = 0;
        int rowNum = getRowCount();

        for(int i=0; i<rowNum; i++) {
            if((Boolean)getValueAt(i, 5) == true) {
                num++;
            }
        }
        
        return num;
    }

    /* public void removeRow(int i) {
        this.modello.removeRow(i);
    } */

    static class ColoreAlternatoRigheTabella extends DefaultTableCellRenderer {

        private Color righeColorePari = null;
        private Color righeColoreDispari = new java.awt.Color(55, 55, 55);
        private Color testoRigheColoreDipari = Color.WHITE;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {

            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the row index is even or odd
            if (row % 2 == 0) {
                component.setBackground(righeColorePari);
                component.setForeground(righeColorePari);
            } else {
                component.setBackground(righeColoreDispari);
                component.setForeground(testoRigheColoreDipari);
            }

            return component;
        }
    }
    
}
