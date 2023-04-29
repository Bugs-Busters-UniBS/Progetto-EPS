public class Camion extends Veicolo {
    private double portataCarico;

    public Camion(String marca, String modello, Targa targa, double portata) {
        super(marca, modello, targa);
        this.portataCarico = portata;
    }

    // Getter dell'attributo portata
    public double getPortata() {
        return this.portataCarico;
    }

    @Override
    public String getTipo() {
        return "Camion";
    }
}
