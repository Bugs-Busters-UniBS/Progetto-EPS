import org.w3c.dom.Element;

public class Targa {
    public enum Paese {
        ITALIA,
        GERMANIA
        // Future implementazioni degli altri paesi
    }

    public static final String NUMERO_TARGA_XML_STRING = "numero";
    public static final String PAESE_XML_STRING = "paese";

    private Paese paese;
    private String numero;

    // Costruttore da string
    public Targa(String numero, String paese) {
        this.numero = numero;
        // Converte string con il nome del paese nel valore enum
        this.paese = Paese.valueOf(paese.toUpperCase());
    }

    // Costruttore da elemento XML
    public Targa(Element targa) {
        this(targa.getAttribute(NUMERO_TARGA_XML_STRING), targa.getAttribute(PAESE_XML_STRING));
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

    public void targaToXml(Element targa) {
        targa.setAttribute(NUMERO_TARGA_XML_STRING, this.getNumero());
        targa.setAttribute(PAESE_XML_STRING, this.getPaese().toString());
    }

    
}
