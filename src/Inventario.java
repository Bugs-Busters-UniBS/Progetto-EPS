import java.util.LinkedList;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Inventario {
    private LinkedList<Veicolo> listaVeicoli;

    public Inventario() {
        listaVeicoli = new LinkedList<Veicolo>();
    }

    // Salva la lista dei veicolo in un file XML
    public void salvaInventario(String filename) {
        // Utilizzata per ottenere un oggetto Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            // Crea l'elemento root ossia l'origine dell'inventario
            Element root = doc.createElement("Inventario");

            // Itera su tutti i veicoli dell'inventario
            for(Veicolo vec : listaVeicoli) {
                // Crea una entry XML <Veicolo>
                Element veicolo = doc.createElement("Veicolo");
                // Crea gli attributi dell'entry XML partendo da quelli degli oggetti della lista
                veicolo.setAttribute("tipo", vec.getTipo());
                veicolo.setAttribute("marca", vec.getMarca());
                veicolo.setAttribute("modello", vec.getModello());

                // Setta gli attributi unici per tipo di Veicolo controllando di quale classe è istanza
                if(vec instanceof Automobile) {
                    Automobile auto = (Automobile)vec;
                    veicolo.setAttribute("Numero_Porte", Integer.toString(auto.getNumeroPorte()));
                }

                if(vec instanceof Camion) {
                    Camion camion = (Camion)vec;
                    veicolo.setAttribute("portata", Double.toString(camion.getPortata()));
                }

                if(vec instanceof Moto) {
                    Moto moto = (Moto)vec;
                    veicolo.setAttribute("cilindrata", Integer.toString(moto.getCilindrata()));
                }

                // Crea per ultima la targa in quanto si tratta di un elemento figlio del veicolo e non un attributo
                Element targa = doc.createElement("Targa");
                targa.setAttribute("numero", vec.getTarga().getNumero());
                targa.setAttribute("paese", vec.getTarga().getPaese().toString());
                // Aggiunge la targa come elemento figlio del veicolo
                veicolo.appendChild(targa);

                // Aggiunge il veicolo come elemento figlio di root (l'origine del documento)
                root.appendChild(veicolo);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerFactoryConfigurationError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
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
            NodeList veicoliList = inventario.getElementsByTagName("Veicolo");

            // Itera su tutti gli element <veicolo> nel file inventario
            for(int i = 0; i < veicoliList.getLength(); i++) {
                Node nodeVeicolo = veicoliList.item(i);
                // Casta l'oggetto nodeVeicolo di classe Node in un oggetto di classe Element
                Element elementVeicolo = (Element)nodeVeicolo;

                // Dichiara le variabili che verranno utilizzate per inizializzare i veicoli della lista listaVeicoli dell'inventario
                Targa targa;
                Veicolo veicoloEntry;
                String modello = elementVeicolo.getAttribute("modello");
                String marca = elementVeicolo.getAttribute("marca");

                // Ottiene il primo elemento con il TagName Targa (ossia l'unica targa del veicolo)
                Element targaElement = (Element)elementVeicolo.getElementsByTagName("Targa").item(0);
                // Ottiene gli attributi della targa
                String numeroTarga = targaElement.getAttribute("numero");
                String paeseTarga = targaElement.getAttribute("paese");
                // Inizializza la targa del veicolo
                targa = new Targa(numeroTarga, paeseTarga);

                // Crea la variabile 'tipo' che verrà utilizata per capire di che tipo è il veicolo
                // in base al tipo si leggeranno gli attributi unici della classe e si inizializzerà la classe corretta
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

        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    public void aggiungiVeicolo(Veicolo vec) {
        this.listaVeicoli.add(vec);
    }

    public void rimuoviVeicolo(String targa) {
        //trova il veicolo con la stessa targa e lo elimina
        for(Veicolo vec : this.listaVeicoli){
            if(vec.getTarga().getNumero().equalsIgnoreCase(targa)){
                this.listaVeicoli.remove(vec);
            }
        } 
    }

    // Getter della lista listaVeicoli
    // Utile per il testing e per la GUI
    public LinkedList<Veicolo> getLista() {
        return this.listaVeicoli;
    }
}
