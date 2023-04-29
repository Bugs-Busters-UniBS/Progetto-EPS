public class Veicolo {
    private String marca;
    private Targa targa;
    private String modello;

    public Veicolo(String marca, String modello, Targa targa) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
    }

    public String getTarga() {
        return this.targa.toString();
    }

    public String getMarca() {
        return this.marca;
    }


    public String getModello() {
        return this.modello;
    }

    public String getTipo() {
        return "Veicolo";
    }

    public void intoXML() {
        //TODO metodo che trasforma l'oggetto in un entry XML
    }
}