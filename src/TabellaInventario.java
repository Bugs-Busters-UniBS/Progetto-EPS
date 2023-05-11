import javax.swing.JTable;

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
    }

    public void addRow(Veicolo vec) {
        inv.aggiungiVeicolo(vec);
        updateTable();
    }

    public void updateTable() {
        this.modello.refresh(inv);

    }
    public Inventario getInventario(){
        return inv;
    }

    /* public void removeRow(int i) {
        this.modello.removeRow(i);
    } */

    
}
