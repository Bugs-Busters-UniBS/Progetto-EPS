import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Camion extends Veicolo {
    public final static String TIPO_VEICOLO = "Camion";
    public static final String PORTATA_XML_STRING = "portata";

    private double portataCarico;

    public Camion(String marca, String modello, Targa targa, double portata) {
        super(marca, modello, targa,"immagini/camion_place_holder.png");
        this.portataCarico = portata;
    }

    public Camion(Element camion) {
        super(camion);
        this.portataCarico = Double.parseDouble(camion.getAttribute(PORTATA_XML_STRING));
    }

    public Camion(String marca, String modello, Targa targa, double portata, String filename) {
        super(marca, modello, targa, filename);
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
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(PORTATA_XML_STRING, Double.toString(this.getPortata()));

        return veicolo;
    }
}
