package gui.base.enums;

public enum TiposTiendas {
    ONLINE("Online"),
    FISICA("Fisica"),
    OUTLET("Outlet"),
    MULTIMARCA("Multimarca");

    private String valor;

    TiposTiendas(String valor) {

        this.valor = valor;
    }

    public String getValor() {

        return valor;
    }
}
