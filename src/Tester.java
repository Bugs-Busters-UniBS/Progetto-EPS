public class Tester {
    public static void main(String[] args) {
        /* Inventario inventario = new Inventario();
        inventario.caricaInventario("Inventario.xml");

        for(Veicolo vec : inventario.getLista()) {
            System.out.printf("Veicolo: %s, marca: %s, modello: %s\n", vec.getTipo(), vec.getMarca(), vec.getModello());
            System.out.printf("Ha targa: %s del paese: %s\n", vec.getTarga().getNumero(), vec.getTarga().getPaese().toString());
        } */

        Inventario inv = new Inventario();

        Targa targa1 = new Targa("ADGSBS", "Italia");
        Targa targa2 = new Targa("FFFSAB", "Germania");
        Targa targa3 = new Targa("AAAAAV", "Italia");
        Targa targa4 = new Targa("QWERTY", "Germania");

        Veicolo auto1 = new Automobile("Fiat", "500", targa1, 5);
        Veicolo auto2 = new Automobile("Audi", "boh", targa2, 150);
        Veicolo camion = new Camion("Coso", "modello1", targa3, 40.23);
        Veicolo moto = new Moto("Honda", "nonoloso", targa4, 1235);

        inv.aggiungiVeicolo(auto1);
        inv.aggiungiVeicolo(auto2);
        inv.aggiungiVeicolo(camion);
        inv.aggiungiVeicolo(moto);

        // inv.rimuoviVeicolo("QWERTY");

        inv.salvaInventario("inventario_generato.xml");
    }
}
