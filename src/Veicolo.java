import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Veicolo {
    public static final String TIPO_XML_STRING = "tipo";
    public static final String MARCA_XML_STRING = "marca";
    public static final String TIPO_VEICOLO = "Veicolo";
    public static final String MODELLO_XML_STRING = "modello";
    public static final String TARGA_XML_STRING = "Targa";

    private String marca;
    private Targa targa;
    private String modello;

    public Veicolo(String marca, String modello, Targa targa) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
    }

    public Veicolo(Element veicolo) {
        this.modello = veicolo.getAttribute(MODELLO_XML_STRING);
        this.marca = veicolo.getAttribute(MARCA_XML_STRING);
        Element targaElement = (Element)veicolo.getElementsByTagName(TARGA_XML_STRING).item(0);
        this.targa = new Targa(targaElement);
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
    
    public Element veicoloToXmlElement (Document fileInventario, String tipo) {
        // Crea gli attributi dell'entry XML partendo da quelli degli oggetti della lista
        Element veicolo = fileInventario.createElement(tipo);
        veicolo.setAttribute(TIPO_XML_STRING, this.getTipo());
        veicolo.setAttribute(MARCA_XML_STRING, this.getMarca());
        veicolo.setAttribute(MODELLO_XML_STRING, this.getModello());

        return veicolo;
    }
}