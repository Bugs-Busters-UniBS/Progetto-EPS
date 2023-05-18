package enumerazioni;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Paesi {
        ITALIA,
        GERMANIA,
        FRANCIA;
        // Future implementazioni degli altri paesi
        // Stringa Contenete la lista dei Paesi
        public static final String[] PAESI = Arrays.stream(Paesi.values()).map(Enum::name).collect(Collectors.toList()) .toArray(new String[0]);

}
