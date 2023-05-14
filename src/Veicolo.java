import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Veicolo {
    //Stringhe utilizzate per dare il nome alle tag XML
    public static final String TIPO_XML_STRING = "tipo";
    public static final String MARCA_XML_STRING = "marca";
    public static final String TIPO_VEICOLO = "Veicolo";
    public static final String MODELLO_XML_STRING = "modello";
    public static final String TARGA_XML_STRING = "Targa";
    public static final String IMMAGINE_XML_TAG = "immagine";
    public static final String FILENAME_XML_TAG = "filename";

    private String marca;
    private Targa targa;
    private String modello;
    private String imgFilename;

    public Veicolo(String marca, String modello, Targa targa) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
        this.imgFilename = "immagini/"+marca+modello+("_"+targa.getNumero())+".png";
    }

    public Veicolo(Element veicolo) {
        this.modello = veicolo.getAttribute(MODELLO_XML_STRING);
        this.marca = veicolo.getAttribute(MARCA_XML_STRING);

        Element targaElement = (Element)veicolo.getElementsByTagName(TARGA_XML_STRING).item(0);
        this.targa = new Targa(targaElement);

        Element imgElement = (Element)veicolo.getElementsByTagName(IMMAGINE_XML_TAG).item(0);
        this.imgFilename = imgElement.getAttribute(FILENAME_XML_TAG);
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

    // Getter del filename associato alla foto del veicolo
    public String getImgFilename() {
        return this.imgFilename;
    }

    
    public Element veicoloToXmlElement (Document fileInventario, String tag) {
        // Crea gli attributi dell'entry XML partendo da quelli degli oggetti della lista
        Element veicolo = fileInventario.createElement(tag);
        veicolo.setAttribute(TIPO_XML_STRING, this.getTipo());
        veicolo.setAttribute(MARCA_XML_STRING, this.getMarca());
        veicolo.setAttribute(MODELLO_XML_STRING, this.getModello());

        return veicolo;
    }

    public Vector<String> toVector() {
        Vector<String> vec = new Vector<String>();
        vec.add(this.getTipo());
        vec.add(this.getMarca());
        vec.add(this.getModello());
        vec.add(this.targa.getNumero());
        vec.add(this.targa.getPaese().toString());
        vec.add("Dettagli");
        vec.add("Elimina");
        return vec;
    }
}