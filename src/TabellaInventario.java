import javax.swing.JTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TabellaInventario extends JTable {
    private TabellaInventarioModel modello;
    private Inventario inv;

    public TabellaInventario(Inventario inv) {
        super();
        this.inv = inv;
        modello = new TabellaInventarioModel(inv);
        setCellSelectionEnabled(false);
        this.setModel(modello);
        this.tableHeader.setReorderingAllowed(false);
        setBottoni();
    }

    public void addRow(Veicolo vec) {
        inv.aggiungiVeicolo(vec);
        updateTable();
    }

    public void updateTable() {
        this.modello.refresh(inv);
        setBottoni();
    }
    public Inventario getInventario(){
        return inv;
    }

    // public void removeRow(int i) {
    //     this.modello.removeRow(i);
    // }

    private void setBottoni(){
        this.getColumn("Elimina").setCellRenderer(new ButtonRenderer());
        this.getColumn("Elimina").setCellEditor(new ButtonEditor(new JCheckBox()));

        this.getColumn("Dettagli").setCellRenderer(new ButtonRenderer());
        this.getColumn("Dettagli").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
                // if (isSelected) {
                //     setForeground(Color.RED);
                //     setBackground(Color.BLUE);
                // } else {
                //     setForeground(Color.RED);
                //     setBackground(Color.BLUE);
                // }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        // if (isSelected) {
        //     button.setForeground(Color.RED);
        //     button.setBackground(Color.BLUE);
        // } else {
        //     button.setForeground(Color.RED);
        //     button.setBackground(Color.BLUE);
        // }
        label = (value == null) ? "" : value.toString();
        button.setText(label);

        //premuto bottone mostra dettagli
        if(column == 5){
            new FinestraRiepilogoDatiVeicolo();
            String targa = table.getModel().getValueAt(row, 3).toString();
            System.out.println(targa);
        }
        //premuto bottone elimina veicolo
        if(column == 6){
            TabellaInventario tabella = (TabellaInventario) table;
            String targa = tabella.getModel().getValueAt(row, 3).toString();
            int scelta = JOptionPane.showConfirmDialog(null,"Are you sure?");  
            //int scelta = JOptionPane.showConfirmDialog(null,"Are you sure?","Conferma",0, JOptionPane.QUESTION_MESSAGE,null);`
            if(scelta==JOptionPane.YES_OPTION){
                tabella.getInventario().rimuoviVeicolo(targa);
                tabella.updateTable();
            }
        }
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}