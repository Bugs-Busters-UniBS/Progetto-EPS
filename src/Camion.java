import org.w3c.dom.Element;

public class Camion extends Veicolo {
    public final static String TIPO_VEICOLO = "Camion";
    public static final String PORTATA_XML_STRING = "portata";

    private double portataCarico;

    public Camion(String marca, String modello, Targa targa, double portata) {
        super(marca, modello, targa);
        this.portataCarico = portata;
    }

    // Getter dell'attributo portata
    public double getPortata() {
        return this.portataCarico;
    }

    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    @Override
    public void veicoloToXmlElement(Element veicolo) {
        super.veicoloToXmlElement(veicolo);
        veicolo.setAttribute(PORTATA_XML_STRING, Double.toString(this.getPortata()));
    }
}
