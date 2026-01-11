package gui.base.enums;

public enum Calzados {
        DEPORTIVA("Deportiva"),
        BOTA("Bota"),
        SANDALIA("Sandalia"),
        PLATAFORMA("Plataforma"),
        ESTAR_POR_CASA("Estar por casa");

        private final String valor;
        //final porque va a ser un valor inmutable

        Calzados(String valor) {this.valor = valor;}

        public String getValor() {return valor;}
    }

