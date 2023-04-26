import java.util.ArrayList;

public class Inventario {
    private ArrayList<Veicolo> listaVeicoli;

    public Inventario() {
        listaVeicoli = new ArrayList<Veicolo>();
    }

    public void salvaInventario(String filename) {
        //TODO chiama i metodi intoXML dei veicolie li salva in un file
    }

    public void caricaInventario(String filename) {
        // TODO 
    }

    public void aggiungiVeicolo(Veicolo vec) {
        this.listaVeicoli.add(vec);
    }

    public void rimuoviVeicolo(String targa) {
        //TODO trova il veicolo dalla targa e lo rimuove
    }

}
