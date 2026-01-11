package gui.base.enums;

public enum TiposTiendas {
    ONLINE("Online"),
    FISICA("Fisica"),
    OUTLET("Outlet"),
    MULTIMARCA("Multimarca");

    private final String valor;
    //final porque va a ser un valor inmutable

    TiposTiendas(String valor) {

        this.valor = valor;
    }

    public String getValor() {

        return valor;
    }
}
