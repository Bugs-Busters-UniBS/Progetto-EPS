import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Automobile extends Veicolo {
    public final static String TIPO_VEICOLO = "Automobile";
    public static final String NUMERO_PORTE_XML_STRING = "Numero_Porte";
    private int numeroPorte;

    public Automobile(String marca, String modello, Targa targa, int numeroPorte) {
        super(marca, modello, targa,"immagini/auto_place_holder.png");
        this.numeroPorte = numeroPorte;
    }

    public Automobile(Element auto) {
        super(auto);
        this.numeroPorte = Integer.parseInt(auto.getAttribute(NUMERO_PORTE_XML_STRING));
    }

    public Automobile(String marca, String modello, Targa targa, int numeroPorte, String filename) {
        super(marca, modello, targa, filename);
        this.numeroPorte = numeroPorte;
    }

    // Getter dell'attributo numeroPorte
    public int getNumeroPorte() {
        return this.numeroPorte;
    }

    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    @Override
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(NUMERO_PORTE_XML_STRING, Integer.toString(this.getNumeroPorte()));
        return veicolo;
    }
}
