public class Targa {
    public enum Paese {
        ITALIA,
        GERMANIA
        // Future implementazioni degli altri paesi
    }

    private Paese paese;
    private String numero;

    public Targa(String numero, String paese) {
        this.numero = numero;
        // Converte string con il nome del paese nel valore enum
        this.paese = Paese.valueOf(paese.toUpperCase());
    }

    // Getter numero della targa
    public String getNumero() {
        return this.numero;
    }

    // Getter valore enum Paese del paese della targa
    // Per avere il valore in string si può usare Paese.toString()
    public Paese getPaese() {
        return this.paese;
    }

}
