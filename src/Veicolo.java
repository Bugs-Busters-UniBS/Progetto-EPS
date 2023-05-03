public class Veicolo {
    public static final String TIPO_VEICOLO = "Veicolo";
    private String marca;
    private Targa targa;
    private String modello;

    public Veicolo(String marca, String modello, Targa targa) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
    }

    // Getter oggetto targa
    public Targa getTarga() {
        return this.targa;
    }

    // Getter marca del veicolo
    public String getMarca() {
        return this.marca;
    }

    // Getter modello del veicolo
    public String getModello() {
        return this.modello;
    }

    // Getter del tipo del veicolo
    // Funzione utilizzata per facilitare la scrittura degli attributi XML
    public String getTipo() {
        return TIPO_VEICOLO;
    }
    
}