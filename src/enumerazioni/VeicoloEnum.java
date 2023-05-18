package enumerazioni;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum VeicoloEnum {
    Automobile, 
    Camion,
    Moto;
    // Nuove implementazioni in futuro
    // Stringa contente la lista dei Veicoli
    public static final String[] VEICOLI = Arrays.stream(VeicoloEnum.values()).map(Enum::name).collect(Collectors.toList()) .toArray(new String[0]);
}

