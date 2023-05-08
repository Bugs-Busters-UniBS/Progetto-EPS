targa = '"AA{:0>3}AA"'
f = open("databasepy.xml", "w")
f.write('<?xml version="1.0" encoding="UTF-8" standalone="no"?>\n')
f.write("<Inventario>\n")
for i in range(1000):
    f.write('\t<Veicolo Numero_Porte="5" marca="Fiat" modello="500" tipo="Automobile">\n')
    f.write('\t\t<Targa numero=')
    f.write(targa.format(i))
    f.write(' paese="ITALIA"/>\n')
    f.write('\t</Veicolo>\n')
    targa
f.write("</Inventario>\n")

