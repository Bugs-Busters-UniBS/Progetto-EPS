import java.util.Vector;

// Import classe di utilità per XML
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * Classe per la definizione del tipo generico veicolo
 */
public class Veicolo {
    //Tipo di veicolo
    //Ma quanto è bello il polimorfismo Johnny?!
    public static final String TIPO_VEICOLO = "Veicolo"; 
   
    //Attributi generali di un veicolo
    private String marca;
    private Targa targa;
    private String modello;
    private String imgFilename;

    // ==================== COSTRUTTORE DA MARCA MODELLO E TARGA ===========================
    //Inutile, tutti i veicoli devono avere un filename per l'immagine di default
    /* public Veicolo(String marca, String modello, Targa targa) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
    } */
    // ======================================================================================

    // ====================== COSTRUTTORE DA ELEMENTO XML ===================================
    public Veicolo(Element veicolo) {
        this.modello = veicolo.getAttribute(XmlTags.MODELLO_XML_TAG);
        this.marca = veicolo.getAttribute(XmlTags.MARCA_XML_TAG);

        Element targaElement = (Element)veicolo.getElementsByTagName(XmlTags.TARGA_XML_TAG).item(0);
        try{
            this.targa = new Targa(targaElement);
        }
        catch(TargaException e){
            //non posso mai andare qui perché una targa generata da XML è corretta per forza
        }
        Element imgElement = (Element)veicolo.getElementsByTagName(XmlTags.IMMAGINE_XML_TAG).item(0);
        this.imgFilename = imgElement.getAttribute(XmlTags.FILENAME_XML_TAG);
    }
    // =======================================================================================


    // ======================= COSTRUTTORE CON FILENAME IMMAGINE =============================
    public Veicolo(String marca, String modello, Targa targa, String filename) {
        this.marca = marca;
        this.modello = modello;
        this.targa = targa;
        this.imgFilename = filename;
    }
    // =======================================================================================


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

    //Converte un veicolo in un elemento XML
    public Element veicoloToXmlElement (Document fileInventario, String tag) {
        // Crea gli attributi dell'entry XML partendo da quelli degli oggetti della lista
        Element veicolo = fileInventario.createElement(tag);
        veicolo.setAttribute(XmlTags.TIPO_XML_TAG, this.getTipo());
        veicolo.setAttribute(XmlTags.MARCA_XML_TAG, this.getMarca());
        veicolo.setAttribute(XmlTags.MODELLO_XML_TAG, this.getModello());

        return veicolo;
    }

    public Vector<Object> toVector() {
        Vector<Object> row = new Vector<Object>(); //Array di object che rappresenta una riga della tabella

        row.add(this.getTipo()); //Tipo del veicolo
        row.add(this.getMarca()); //Marca del veicolo
        row.add(this.getModello()); //Modello del veicolo
        row.add(this.targa.getNumero()); //Numero della targa
        row.add(this.targa.getPaese().toString()); //Paese di origine della targa

        return row;
    }
}