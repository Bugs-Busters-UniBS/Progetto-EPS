import javax.swing.JTable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class TabellaInventario extends JTable {
    private TabellaInventarioModel modello;

    public TabellaInventario(Inventario inv) {
        super();
        modello = new TabellaInventarioModel(inv);
        setCellSelectionEnabled(false);
        this.setModel(modello);
        this.tableHeader.setReorderingAllowed(false);
        setBottoni();
    }

    public void addRow(Veicolo vec) {
        this.modello.addRow(vec);
    }

    public void updateTable(Inventario inv) {
        this.modello.refresh(inv);
        setBottoni();
    }

    public void removeRow(int i) {
        this.modello.removeRow(i);
    }

    private void setBottoni(){
        this.getColumn("Elimina").setCellRenderer(new ButtonRenderer());
        this.getColumn("Elimina").setCellEditor(new ButtonEditor(new JCheckBox()));

        this.getColumn("Dettagli").setCellRenderer(new ButtonRenderer());
        this.getColumn("Dettagli").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    /*
    * Azioni del bottone
    */
    public void mouseClicked(MouseEvent e) {
        int column = this.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row    = e.getY()/this.getRowHeight(); //get the row of the button

        /*Checking the row or column is valid or not*/
        if (row < this.getRowCount() && row >= 0 && column < this.getColumnCount() && column >= 0) {
            Object value = this.getValueAt(row, column);
            if (value instanceof JButton) {
                /*perform a click event*/
                ((JButton)value).doClick();
            }
        }
        System.out.println(column);
    }   
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        // button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            JOptionPane.showMessageDialog(button, label + ": Ouch!");
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }


}