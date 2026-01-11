package gui.base.enums;

public enum TipoEnvio {
    DOMICILIO("Domicilio"),
    TIENDA("Tienda"),
    PUNTO_DE_RECOGIDA("Punto de recogida");

    private String valor;

    TipoEnvio(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoEnvio fromValor(String valor) {
        for (TipoEnvio t : values()) {
            if (t.valor.equalsIgnoreCase(valor)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo de envío desconocido: " + valor);
    }
}

