package main;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import gui.Controlador;
import gui.Modelo;
import gui.Vista;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {
        //Aqui importamos libreria de flatlaf del archivo ejecutable y la convertimos en interfaz principal
        // IMPORTANTE hacer try catch antes de crear la ventana, sino no se visualiza la libreria
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo,vista);
    }
}
