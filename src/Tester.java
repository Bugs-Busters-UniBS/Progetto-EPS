public class Tester {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        inventario.caricaInventario("Inventario.xml");

        for(Veicolo vec : inventario.getLista()) {
            System.out.printf("Veicolo: %s, marca: %s, modello: %s\n", vec.getTipo(), vec.getMarca(), vec.getModello());
            System.out.printf("Ha targa %s\n", vec.getTarga());
        }
    }
}
