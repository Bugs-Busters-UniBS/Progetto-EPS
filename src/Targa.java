import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Targa {
    public enum Paese {
        ITALIA,
        GERMANIA,
        FRANCIA
        // Future implementazioni degli altri paesi
    }

    private Paese paese;
    private String numero;

    // Costruttore da string
    public Targa(String numero, String paese) throws TargaException {
        numero = numero.toUpperCase();
        numero = numero.trim();
        this.paese = Paese.valueOf(paese.toUpperCase());
        checkCorrettezzaNumero(numero, this.paese);
        this.numero = numero;
        // Converte string con il nome del paese nel valore enum
        this.paese = Paese.valueOf(paese.toUpperCase());
    }

    // Costruttore da elemento XML
    public Targa(Element targa) throws TargaException{
        this(targa.getAttribute(XmlTags.NUMERO_TARGA_XML_TAG), targa.getAttribute(XmlTags.PAESE_XML_TAG));
    }

    // Getter numero della targa
    public String getNumero() {
        return this.numero;
    }

    // Getter valore enum Paese del paese della targa
    // Per avere il valore in string si può usare Paese.toString()
    public Paese getPaese() {
        return this.paese;
    }

    //Converte l'oggetto targa in un elemento XML
    public Element targaToXml(Document fileInventario, String tag) {
        Element targa = fileInventario.createElement(tag);
        targa.setAttribute(XmlTags.NUMERO_TARGA_XML_TAG, this.getNumero());
        targa.setAttribute(XmlTags.PAESE_XML_TAG, this.getPaese().toString());

        return targa;
    }

    //ritorna la sigla del paese (utile per visualizzazione targa nella gui)
    public String getSigla() {
        switch(paese) {
            case ITALIA:
                return "I";
            case GERMANIA:
                return "D";
            case FRANCIA:
                return "F";
        }
        return "";
    }

    //verifica correttezza sintassi targa e lancia eccezione nel caso ci siano degli errori
    private void checkCorrettezzaNumero(String numero, Paese paese) throws TargaException{
        switch(paese){
            case ITALIA:
                if (numero.length() != 7)
                    throw new TargaException("Lunghezza targa errata");
                if (numero.contains("Q") || numero.contains("I") ||numero.contains("O") ||numero.contains("U"))
                    throw new TargaException("Caratteri targa non ammessi");
                char[] n = numero.toCharArray();
                for (int i = 0; i < 7; i++) {
                    if (i<2 || i>4){
                        if(n[i]>'Z' || n[i]<'A')
                            throw new TargaException("Sintassi targa errata");
                    }
                    else {
                        if(n[i]>'9' || n[i]<'0')
                            throw new TargaException("Sintassi targa errata");
                    }
                }
                break;
            case GERMANIA:
                break;
            case FRANCIA:
                break;
        }
    } 
}
