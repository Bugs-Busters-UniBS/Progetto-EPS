public class Automobile extends Veicolo {
    private int numeroPorte;

    public Automobile(String marca, String modello, Targa targa, int numeroPorte) {
        super(marca, modello, targa);
        this.numeroPorte = numeroPorte;
    }

    // Getter dell'attributo numeroPorte
    public int getNumeroPorte() {
        return this.numeroPorte;
    }

    @Override
    public String getTipo() {
        return "Automobile";
    }
}
