public class Camion extends Veicolo {
    private double portataCarico;

    public Camion(String marca, String modello, Targa targa, double portata) {
        super(marca, modello, targa);
        this.portataCarico = portata;
    }

    public double getPortata() {
        return this.portataCarico;
    }
}
