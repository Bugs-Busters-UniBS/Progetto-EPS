import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Camion extends Veicolo {
    public final static String TIPO_VEICOLO = "Camion";

    private double portataCarico;

    //===================COSTRUTTORE DA MARCA MODELLO TARGA==============================
    public Camion(String marca, String modello, Targa targa, double portata) {
        super(marca, modello, targa);
        this.portataCarico = portata;
    }
    //====================================================================================


    //=========================COSTRUTTORE DA ELEMENTO====================================
    public Camion(Element camion) {
        super(camion);
        this.portataCarico = Double.parseDouble(camion.getAttribute(XmlTags.PORTATA_XML_TAG));
    }
    //=====================================================================================


    //============================COSTRUTTORE CON IMMAGINE=====================================
    public Camion(String marca, String modello, Targa targa, double portata, String filename) {
        super(marca, modello, targa, filename);
        this.portataCarico = portata;
    }
    //=========================================================================================


    // Getter dell'attributo portata
    public double getPortata() {
        return this.portataCarico;
    }

    //Getter tipo Veicolo (Probabilmente non necessario -> Polimorfismo)
    @Override
    public String getTipo() {
        return TIPO_VEICOLO;
    }

    //Converte il Veicolo in un elemento XML
    @Override
    public Element veicoloToXmlElement(Document fileInventario, String tag) {
        Element veicolo = super.veicoloToXmlElement(fileInventario, tag);
        veicolo.setAttribute(XmlTags.PORTATA_XML_TAG, Double.toString(this.getPortata()));

        return veicolo;
    }
}
