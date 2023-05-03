import org.w3c.dom.Element;


public class Automobile extends Veicolo {
    public final static String TIPO_VEICOLO = "Automobile";
    public static final String NUMERO_PORTE_XML_STRING = "Numero_Porte";
    private int numeroPorte;

    public Automobile(String marca, String modello, Targa targa, int numeroPorte) {
        super(marca, modello, targa);
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
    public void veicoloToXmlElement(Element veicolo) {
        super.veicoloToXmlElement(veicolo);
        veicolo.setAttribute(NUMERO_PORTE_XML_STRING, Integer.toString(this.getNumeroPorte()));
    }
}
