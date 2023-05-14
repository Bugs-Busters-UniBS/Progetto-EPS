import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Moto extends Veicolo {
    public final static String TIPO_VEICOLO = "Moto";
    private final static String DEFAULT_IMAGE = "immagini/moto_place_holder.png";
    private int cilindrata;
   
    //========================COSTRUTTORE DA MARCA MODELLO E TARGA==========================
    public Moto(String marca, String modello, Targa targa, int cilindrata) {
        super(marca, modello, targa, DEFAULT_IMAGE);
        this.cilindrata = cilindrata;
    }
    //======================================================================================


    //================================COSTRUTTORE DA ELEMENTO XML============================
    public Moto(Element moto) {
        super(moto);
        this.cilindrata = Integer.parseInt(moto.getAttribute(XmlTags.CILINDRATA_XML_TAG));

    }
    //=======================================================================================

    
    //==========================COSTRUTTORE CON IMMAGINE SPECIFICA===========================
    public Moto(String marca, String modello, Targa targa, int cilindrata, String filename) {
        super(marca, modello, targa, filename);
        this.cilindrata = cilindrata;
    }
    //=======================================================================================

    // Getter dell'attributo cilindrata
    public int getCilindrata() {
        return this.cilindrata;
    }

    //Getter del tipo di veicolo (Probabilmente non necessario -> Polimorfismo)
    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    //Converte l'oggetto moto in un elemento XML
    @Override
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(XmlTags.CILINDRATA_XML_TAG, Integer.toString(this.getCilindrata()));

        return veicolo;
    }
}
    

