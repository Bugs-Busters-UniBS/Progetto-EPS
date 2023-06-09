import java.util.LinkedList;
import java.util.TreeSet;

// Import classe di utilità per XML
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// Import per la gestione dei File
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/***
 * Classe per la Creazione di un inventario contente una lista di Veicoli
 */
public class Inventario {
    private LinkedList<Veicolo> listaVeicoli;
    private TreeSet<Targa> listaTarghe; //Usato per garantire unicità targhe nell'inventario

    //Unico costruttore inventario
    public Inventario() {
        listaVeicoli = new LinkedList<Veicolo>();
        listaTarghe = new TreeSet<Targa>(); 

    }

    // Salva la lista dei veicolo in un file XML
    public void salvaInventario(String filename) {
        // Utilizzata per ottenere un oggetto Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Crea l'elemento root ossia l'origine dell'inventario
            Element root = doc.createElement(XmlTags.INVENTARIO_XML_TAG);

            // Itera su tutti i veicoli dell'inventario
            for(Veicolo vec : listaVeicoli) {
                // Crea gli attributi dell'entry XML partendo da quelli degli oggetti della lista
                Element veicoloElement = vec.veicoloToXmlElement(doc, Veicolo.TIPO_VEICOLO);
                // Crea per ultima la targa in quanto si tratta di un elemento figlio del veicolo e non un attributo
                Element targa = vec.getTarga().targaToXml(doc, XmlTags.TARGA_XML_TAG);
                // Aggiunge la targa come elemento figlio del veicolo
                veicoloElement.appendChild(targa);
                // Aggiunge il filename dell'immagine da mostrare nella schermata dettagli
                Element immagine = doc.createElement(XmlTags.IMMAGINE_XML_TAG);
                immagine.setAttribute(XmlTags.FILENAME_XML_TAG, vec.getImgFilename());
                // Aggiunge l'immagine come elemento child del veicolo
                veicoloElement.appendChild(immagine);
                // Aggiunge il veicolo come elemento figlio di root (l'origine del documento)
                root.appendChild(veicoloElement);
            }

            // Aggiunge root al documento
            doc.appendChild(root);

            // Setta la opzioni della classe Transformer
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // Trasforma l'oggetto di tipo Document in un StreamResult e poi in un output stream
            // che viene scritto su un file
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(filename)));

        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    // Carica da un file XML la lista dei veicoli
    public void caricaInventario(String filename) {
        try {
            // Classi utilizzate per ottenere un documento DOM (interfaccia rappresentate il documento XML)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Ottiene il documento partendo dal filename e dal parser della classe builder
            Document inventario = builder.parse(new File(filename));
            
            // Normalizza la struttura XML (facoltativa (probabilmente))
            // inventario.getDocumentElement().normalize();
            // Ottiene una lista di tutti gli elementi con tag "Veicolo"
            NodeList veicoliList = inventario.getElementsByTagName(Veicolo.TIPO_VEICOLO);

            // Itera su tutti gli element <veicolo> nel file inventario
            for(int i = 0; i < veicoliList.getLength(); i++) {
                Node nodeVeicolo = veicoliList.item(i);
                // Casta l'oggetto nodeVeicolo di classe Node in un oggetto di classe Element
                Element elementVeicolo = (Element)nodeVeicolo;

                String tipo = elementVeicolo.getAttribute(XmlTags.TIPO_XML_TAG);
                try {
                    if(tipo.equals(Automobile.TIPO_VEICOLO)) {
                        aggiungiVeicolo(new Automobile(elementVeicolo));
                    }

                    if (tipo.equals(Camion.TIPO_VEICOLO)) {
                        aggiungiVeicolo(new Camion(elementVeicolo));
                    }

                    if (tipo.equals(Moto.TIPO_VEICOLO)) {
                        aggiungiVeicolo(new Moto(elementVeicolo));
                    }
                }
                catch(TargaException te){
                    //impossibile arrivare qui, li sto caricando da XML
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

        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    // Aggiunge un veicolo all'inventario
    public void aggiungiVeicolo(Veicolo vec) throws TargaException {
        
        if(this.listaTarghe.add(vec.getTarga()))
            this.listaVeicoli.add(vec);
        else
            throw new TargaException("Targa già presente");
    }
    
    // Rimuove un veicolo dall'inventario identificandolo dalla targa
    public void rimuoviVeicolo(Targa targa) {
        // Trova il veicolo con la stessa targa e lo elimina
        if(listaTarghe.remove(targa)){
            for(Veicolo vec : this.listaVeicoli){
                if(vec.getTarga().compareTo(targa) == 0){
                    this.listaVeicoli.remove(vec);
                    return;
                }
            }
        }
    }

    // Getter della lista listaVeicoli
    // Utile per il testing e per la GUI
    public LinkedList<Veicolo> getLista() {
        return this.listaVeicoli;
    }

    //getter di un veicolo passando la targa
    public Veicolo getVeicoloDaTarga(Targa targa) {
        if(listaTarghe.contains(targa)){ //cerco nella lista di veicoli solo se la targa è nell'inventario
            for(Veicolo veic : listaVeicoli){
                if(veic.getTarga().compareTo(targa)==0)
                    return veic;
            }
        }
        return null;
    }
}
