import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Moto extends Veicolo {
    public static final String CILINDRATA_XML_STRING = "cilindrata";
    public final static String TIPO_VEICOLO = "Moto";
    private int cilindrata;
   
    public Moto(String marca, String modello, Targa targa, int cilindrata) {
        super(marca, modello, targa);
        this.cilindrata = cilindrata;
    }

    public Moto(Element moto) {
        super(moto);
        this.cilindrata = Integer.parseInt(moto.getAttribute(CILINDRATA_XML_STRING));

    }

    public Moto(String marca, String modello, Targa targa, int cilindrata, String filename) {
        super(marca, modello, targa, filename);
        this.cilindrata = cilindrata;
    }

    // Getter dell'attributo cilindrata
    public int getCilindrata() {
        return this.cilindrata;
    }

    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    @Override
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(CILINDRATA_XML_STRING, Integer.toString(this.getCilindrata()));

        return veicolo;
    }
}
    

