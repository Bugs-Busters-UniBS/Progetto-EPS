import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TabellaInventarioModel extends DefaultTableModel {
    private static final Vector<String> NOMI_COLONNE = new Vector<>(Arrays.asList("Tipo Veicolo", "Marca", "Modello", "Numero Targa", "Paese", "Dettagli", "Elimina"));

    public TabellaInventarioModel(Inventario inv) {
        super(NOMI_COLONNE, inv.getLista().size());
        Vector<Vector<String>> rows = new Vector<>();

        for(Veicolo veic : inv.getLista()) {
            rows.add(veic.toVector());
        }

        this.setDataVector(rows, NOMI_COLONNE);
    }

    public void refresh(Inventario inv) {
        Vector<Vector<String>> rows = new Vector<>();

        for(Veicolo veic : inv.getLista()) {
            rows.add(veic.toVector());
        }

        this.setDataVector(rows, NOMI_COLONNE);
    } 

    public void addRow(Veicolo v) {
        super.addRow(v.toVector());
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        //Only the third column
        return column==5 || column==6;
    }
}