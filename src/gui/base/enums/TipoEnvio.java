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
}
