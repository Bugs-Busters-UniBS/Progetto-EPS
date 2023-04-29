public class Moto extends Veicolo {
    private int cilindrata;

    public Moto(String marca, String modello, Targa targa, int cilindrata) {
        super(marca, modello, targa);
        this.cilindrata = cilindrata;
    }

    // Getter dell'attributo cilindrata
    public int getCilindrata() {
        return this.cilindrata;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
    
}
