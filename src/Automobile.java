import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Automobile extends Veicolo {
    public final static String TIPO_VEICOLO = "Automobile";
    public static final String NUMERO_PORTE_XML_STRING = "Numero_Porte";
    private int numeroPorte;

    public Automobile(String marca, String modello, Targa targa, int numeroPorte) {
        super(marca, modello, targa);
        this.numeroPorte = numeroPorte;
    }

    public Automobile(Element auto) {
        super(auto);
        this.numeroPorte = Integer.parseInt(auto.getAttribute(NUMERO_PORTE_XML_STRING));
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
    public Element veicoloToXmlElement(Document fileInventario, String tipo) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tipo);
        veicolo.setAttribute(NUMERO_PORTE_XML_STRING, Integer.toString(this.getNumeroPorte()));

        return veicolo;
    }
}
