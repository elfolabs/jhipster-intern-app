package br.com.elfotec.domain.enumeration;

/**
 * The EstadoCivil enumeration.
 */
public enum EstadoCivil {
    SOLTEIRO("SOL"),
    CASADO("CAS"),
    VIUVO("VIU"),
    DIVORCIADO("DIV"),
    SEPARADO("SEP"),
    UNIAO_ESTAVEL("UNI");

    private final String value;

    EstadoCivil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
