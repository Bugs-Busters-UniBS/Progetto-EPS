import org.w3c.dom.Element;

public class Moto extends Veicolo {
    public static final String CILINDRATA_XML_STRING = "cilindrata";
    public final static String TIPO_VEICOLO = "Moto";
    private int cilindrata;
   
    public Moto(String marca, String modello, Targa targa, int cilindrata) {
        super(marca, modello, targa);
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
    public void veicoloToXmlElement(Element veicolo) {
        super.veicoloToXmlElement(veicolo);
        veicolo.setAttribute(CILINDRATA_XML_STRING, Double.toString(this.getCilindrata()));
    }
}
    

