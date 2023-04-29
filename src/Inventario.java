import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Inventario {
    private ArrayList<Veicolo> listaVeicoli;

    public Inventario() {
        listaVeicoli = new ArrayList<Veicolo>();
    }

    public void salvaInventario(String filename) {
        //TODO chiama i metodi intoXML dei veicoli e li salva in un file
    }

    public void caricaInventario(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Ottiene il documento partendo dal filename
            Document inventario = builder.parse(new File(filename));
            // Normalizza la struttura XML
            inventario.getDocumentElement().normalize();
            // Ottiene una lista di tutti gli elementi con tag "Veicolo"
            NodeList veicoliList = inventario.getElementsByTagName("Veicolo");

            // Itera su tutti i veicoli nell'inventario
            for(int i = 0; i < veicoliList.getLength(); i++) {
                Node nodeVeicolo = veicoliList.item(i);
                Element elementVeicolo = (Element)nodeVeicolo;

                String modello = elementVeicolo.getAttribute("modello");
                String marca = elementVeicolo.getAttribute("marca");
                Targa targa;
                Veicolo veicoloEntry;

                Element targaElement = (Element)elementVeicolo.getElementsByTagName("Targa").item(0);
                String numeroTarga = targaElement.getAttribute("numero");
                String paeseTarga = targaElement.getAttribute("paese");
                targa = new Targa(numeroTarga, Targa.stringToPaese(paeseTarga));

                String tipo = elementVeicolo.getAttribute("tipo");

                if(tipo.compareTo("Automobile") == 0) {
                    int numeroPorte = Integer.parseInt(elementVeicolo.getAttribute("Numero_Porte"));
                    
                    veicoloEntry = new Automobile(marca, modello, targa, numeroPorte);
                    aggiungiVeicolo(veicoloEntry);
                }

                if (tipo.compareTo("Camion") == 0) {
                    double portata = Double.parseDouble(elementVeicolo.getAttribute("portata"));

                    veicoloEntry = new Camion(marca, modello, targa, portata);
                    aggiungiVeicolo(veicoloEntry);
                }

                if (tipo.compareTo("Moto") == 0) {
                    int cilindrata = Integer.parseInt(elementVeicolo.getAttribute("cilindrata"));

                    veicoloEntry = new Moto(marca, modello, targa, cilindrata);
                    aggiungiVeicolo(veicoloEntry);
                }

            }

        } 

        catch (SAXException e) {
            // TODO Auto-generated
            e.printStackTrace();
        } 
        
        catch (IOException e) {
            // TODO Auto-generated
            e.printStackTrace();
        } 
        
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void aggiungiVeicolo(Veicolo vec) {
        this.listaVeicoli.add(vec);
    }

    public void rimuoviVeicolo(String targa) {
        //TODO trova il veicolo dalla targa e lo rimuove
    }

    public ArrayList<Veicolo> getLista() {
        return this.listaVeicoli;
    }
}
