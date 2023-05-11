import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TabellaInventarioModel extends DefaultTableModel {
    private static final Vector<String> NOMI_COLONNE = new Vector<>(Arrays.asList("Tipo Veicolo", "Marca", "Modello", "Numero Targa", "Paese", "Elimina"));

    public TabellaInventarioModel(Inventario inv) {
        super(NOMI_COLONNE, inv.getLista().size());
        Vector<Vector<String>> rows = new Vector<>();

        for(Veicolo veic : inv.getLista()) {
            rows.add(veic.toVector());
        }

        this.setDataVector(rows, NOMI_COLONNE);
    }

    public void refresh(Inventario inv) {
        Vector<Vector<String>> rows = new Vector<Vector<String>>();

        for(Veicolo veic : inv.getLista()) {
            rows.add(veic.toVector());
        }

        this.setDataVector(rows, NOMI_COLONNE);
    }
    
    public boolean isCellEditable(int row, int column) {
        //Solo colonna 5 (checkbox)
        return column==5;
    }

    public void addRow(Veicolo v) {
        super.addRow(v.toVector());
    }


}