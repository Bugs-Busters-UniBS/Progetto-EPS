import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TabellaInventarioModel extends DefaultTableModel {
    private static final Vector<String> NOMI_COLONNE = new Vector<>(Arrays.asList("Tipo Veicolo", "Marca", "Modello", "Numero Targa", "Paese", "Elimina"));

    public TabellaInventarioModel(Inventario inv) {
        super(NOMI_COLONNE, inv.getLista().size());
        refresh(inv);
    }

    public void refresh(Inventario inv) {
        Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

        for(Veicolo veic : inv.getLista()) {
            Vector<Object> row = veic.toVector();
            row.add(false);
            rows.add(row);
        }

        this.setDataVector(rows, NOMI_COLONNE);
    }
    
    public boolean isCellEditable(int row, int column) {
        //Solo colonna 5 (checkbox)
        return column == 5;
    }

    //Aggiunge una linea alla tabella (quando viene aggiunto un veicolo all'inventario)
    public void addRow(Veicolo v) {
        Vector<Object> row = v.toVector();
        row.add(false); //Aggiunge una colonna false (la checkbox per la rimozione)

        super.addRow(row); //Aggiunge la riga completa alla tabella
    }

    //"Forza" la tabella a considerare la quinta colonna come dei boolean
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 5)
            return Boolean.class;
        else
            return String.class;
    }

    public void filterRows(String searchText) {
    }
}