import javax.swing.JTable;

public class TabellaInventario extends JTable {
    private TabellaInventarioModel modello;

    public TabellaInventario(Inventario inv) {
        super();
        modello = new TabellaInventarioModel(inv);
        this.setModel(modello);
    }

    public void addRow(Veicolo vec) {
        this.modello.addRow(vec);
    }
    
}
