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
        this.paese = Paese.valueOf(paese.toUpperCase());
    }

    public String toString() {
        return "Numero: "+numero+" Paese: "+paese.name();
    }

}
