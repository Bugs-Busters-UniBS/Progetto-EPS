// Import classe di utilità per XML
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/***
 * Classe per la definizione del tipo specifico Automobile
 */
public class Automobile extends Veicolo {
    public final static String TIPO_VEICOLO = "Automobile";
    private final static String DEFAULT_IMAGE = "immagini/auto_place_holder.png";
    private int numeroPorte;

    // ==================COSTRUTTORE DA MARCA MODELLO TARGA===================================
    public Automobile(String marca, String modello, Targa targa, int numeroPorte) {
        super(marca, modello, targa, DEFAULT_IMAGE);
        this.numeroPorte = numeroPorte;
    }
    // ========================================================================================

    // ===========================COSTRUTTORE DA ELEMENTO XML==================================
    public Automobile(Element auto) {
        super(auto);
        this.numeroPorte = Integer.parseInt(auto.getAttribute(XmlTags.NUMERO_PORTE_XML_TAG));
    }
    // ==========================================================================================

    // =================================COSTRUTTORE CON IMMAGINE==================================
    public Automobile(String marca, String modello, Targa targa, int numeroPorte, String filename) {
        super(marca, modello, targa, filename);
        this.numeroPorte = numeroPorte;
    }
    // ===========================================================================================

    // Getter dell'attributo numeroPorte
    public int getNumeroPorte() {
        return this.numeroPorte;
    }

    // Getter del tipo veicolo (Probabilmente non necessario -> Polimorfismo)
    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    // Converte il veicolo in un elemento XML
    @Override
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(XmlTags.NUMERO_PORTE_XML_TAG, Integer.toString(this.getNumeroPorte()));
        return veicolo;
    }
}
