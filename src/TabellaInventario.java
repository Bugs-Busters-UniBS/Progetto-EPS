import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

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
        this.tableHeader.setReorderingAllowed(false);
        
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
            Veicolo v = inv.getVeicoloDaTarga(modello.getValueAt(row, 3).toString());
            GUIRiepilogoVeicolo guiDettagli = new GUIRiepilogoVeicolo(v.getMarca()+" "+v.getModello(), v, inv);
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

    /* public void removeRow(int i) {
        this.modello.removeRow(i);
    } */

    
}
