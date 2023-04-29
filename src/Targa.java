public class Targa {
    public enum Paese {
        ITALIA
        // Future implementazioni degli altri paesi
    }

    private Paese paese;
    private String numero;

    public Targa(String numero, Paese paese) {
        this.numero = numero;
        this.paese = paese;
        //TODO implementare il costruttore
    }

    public String toString() {
        //TODO IMPLEMENTARE TOSTRING()
        return "TODO";
    }

    public static Targa.Paese stringToPaese(String str) {
        return Targa.Paese.ITALIA;
    }
}
