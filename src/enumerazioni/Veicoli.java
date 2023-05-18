package enumerazioni;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Veicoli {
    Automobile, 
    Camion,
    Moto;
    // Nuove implementazioni in futuro
    // Stringa contente la lista dei Veicoli
    public static final String[] VEICOLI = Arrays.stream(Veicoli.values()).map(Enum::name).collect(Collectors.toList()) .toArray(new String[0]);
}

