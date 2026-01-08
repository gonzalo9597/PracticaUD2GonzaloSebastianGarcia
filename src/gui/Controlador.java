package gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import util.Util;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;


public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    private boolean refrescar;
    private boolean conectado;
    private boolean darkMode;


    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        conectado = true; // al inicio estamos conectados
        darkMode = false;
        modelo.conectar();
        setOptions();

        addActionListeners(this);
        addItemListeners(this);
        addWindowListeners(this);
        refrescarTodo();
        iniciar();
    }

    private void refrescarTodo() {
        refrescarMarcas();
        refrescarTienda();
        refrescarCalzados();
        refrescar = false;
    }

    private void addActionListeners(ActionListener listener) {
        vista.btnCalzadosAnadir.addActionListener(listener);
        vista.btnCalzadosAnadir.setActionCommand("anadirCalzado");
        vista.btnMarcasAnadir.addActionListener(listener);
        vista.btnMarcasAnadir.setActionCommand("anadirMarca");
        vista.btnTiendasAnadir.addActionListener(listener);
        vista.btnTiendasAnadir.setActionCommand("anadirTienda");
        vista.btnCalzadosEliminar.addActionListener(listener);
        vista.btnCalzadosEliminar.setActionCommand("eliminarCalzado");
        vista.btnMarcasEliminar.addActionListener(listener);
        vista.btnMarcasEliminar.setActionCommand("eliminarMarca");
        vista.btnTiendasEliminar.addActionListener(listener);
        vista.btnTiendasEliminar.setActionCommand("eliminarTienda");
        vista.btnCalzadosModificar.addActionListener(listener);
        vista.btnCalzadosModificar.setActionCommand("modificarCalzado");
        vista.btnMarcasModificar.addActionListener(listener);
        vista.btnMarcasModificar.setActionCommand("modificarMarca");
        vista.btnTiendasModificar.addActionListener(listener);
        vista.btnTiendasModificar.setActionCommand("modificarTienda");
        vista.optionDialog.btnOpcionesGuardar.addActionListener(listener);
        vista.optionDialog.btnOpcionesGuardar.setActionCommand("guardarOpciones");
        vista.itemOpciones.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        // --- Botón validar admin ---
        vista.btnValidate.addActionListener(listener);
        vista.itemDarkMode.addActionListener(listener);
    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    void iniciar(){
        vista.tiendasTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel =  vista.tiendasTabla.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                        int row = vista.tiendasTabla.getSelectedRow();
                        vista.txtNombreTienda.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 1)));
                        vista.txtEmail.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 2)));
                        vista.txtTelefono.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 3)));
                        vista.comboTipoTienda.setSelectedItem(String.valueOf(vista.tiendasTabla.getValueAt(row, 4)));
                        vista.txtWeb.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 5)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                            borrarCamposTiendas();
                        } else if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                            borrarCamposMarcas();
                        } else if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                            borrarCamposCalzados();
                        }
                    }
                }
            }
        });

        vista.marcasTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 =  vista.marcasTabla.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                        int row = vista.marcasTabla.getSelectedRow();
                        vista.txtNombreMarca.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 1)));
                        vista.txtNombreLegalEmpresa.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 2)));
                        vista.fechaFundacion.setDate((Date.valueOf(String.valueOf(vista.marcasTabla.getValueAt(row, 3)))).toLocalDate());
                        vista.txtPaisFundacion.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 4)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                            borrarCamposTiendas();
                        } else if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                            borrarCamposMarcas();
                        } else if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                            borrarCamposCalzados();
                        }
                    }
                }
            }
        });

        vista.calzadosTabla.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 =  vista.calzadosTabla.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                        int row = vista.calzadosTabla.getSelectedRow();
                        vista.txtModelo.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 1)));
                        vista.comboMarca.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 5)));
                        vista.comboTienda.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 3)));
                        vista.comboTipoCalzado.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 4)));
                        vista.fechaDeLanzamiento.setDate((Date.valueOf(String.valueOf(vista.calzadosTabla.getValueAt(row, 7)))).toLocalDate());
                        vista.txtCodigoSKU.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 2)));
                        vista.txtPrecioCalzado.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 6)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                            borrarCamposTiendas();
                        } else if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                            borrarCamposMarcas();
                        } else if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                            borrarCamposCalzados();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
            if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                int row = vista.tiendasTabla.getSelectedRow();
                vista.txtNombreTienda.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 1)));
                vista.txtEmail.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 2)));
                vista.txtTelefono.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 3)));
                vista.comboTipoTienda.setSelectedItem(String.valueOf(vista.tiendasTabla.getValueAt(row, 4)));
                vista.txtWeb.setText(String.valueOf(vista.tiendasTabla.getValueAt(row, 5)));
            } else if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                int row = vista.marcasTabla.getSelectedRow();
                vista.txtNombreMarca.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 1)));
                vista.txtNombreLegalEmpresa.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 2)));
                vista.fechaFundacion.setDate((Date.valueOf(String.valueOf(vista.marcasTabla.getValueAt(row, 3)))).toLocalDate());
                vista.txtPaisFundacion.setText(String.valueOf(vista.marcasTabla.getValueAt(row, 4)));
            } else if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                int row = vista.calzadosTabla.getSelectedRow();
                vista.txtModelo.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 1)));
                vista.comboMarca.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 5)));
                vista.comboTienda.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 3)));
                vista.comboTipoCalzado.setSelectedItem(String.valueOf(vista.calzadosTabla.getValueAt(row, 4)));
                vista.fechaDeLanzamiento.setDate((Date.valueOf(String.valueOf(vista.calzadosTabla.getValueAt(row, 7)))).toLocalDate());
                vista.txtCodigoSKU.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 2)));
                vista.txtPrecioCalzado.setText(String.valueOf(vista.calzadosTabla.getValueAt(row, 6)));
            } else if (e.getValueIsAdjusting()
                    && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                if (e.getSource().equals(vista.tiendasTabla.getSelectionModel())) {
                    borrarCamposTiendas();
                } else if (e.getSource().equals(vista.marcasTabla.getSelectionModel())) {
                    borrarCamposMarcas();
                } else if (e.getSource().equals(vista.calzadosTabla.getSelectionModel())) {
                    borrarCamposCalzados();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Opciones":
                // Si el diálogo no existe o fue cerrado, lo recreamos
                if (vista.adminPasswordDialog == null || !vista.adminPasswordDialog.isDisplayable()) {
                    vista.setAdminDialog(); // recrea el diálogo
                }
                vista.adminPasswordDialog.setVisible(true); // ahora sí lo mostramos
                break;
            case "Desconectar":
            case "Conectar": // para el botón del popup
                if (conectado) {
                    desconectar();
                } else {
                    conectar();
                }
                break;
            case "Dark Mode":
                try {
                    darkMode = !darkMode;

                    if (darkMode) {
                        UIManager.setLookAndFeel(new FlatMacDarkLaf());
                        vista.itemDarkMode.setText("Light Mode");
                    } else {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                        vista.itemDarkMode.setText("Dark Mode");
                    }

                    SwingUtilities.updateComponentTreeUI(vista);
                    vista.pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Salir":
                System.exit(0);
                break;
            case "abrirOpciones":
                if(String.valueOf(vista.adminPassword.getPassword()).equals(modelo.getAdminPassword())) {
                    vista.adminPassword.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("La contraseña introducida no es correcta.");
                }
                break;
            case "guardarOpciones":
                modelo.setPropValues(vista.optionDialog.txtIP.getText(), vista.optionDialog.txtUsuario.getText(),
                        String.valueOf(vista.optionDialog.pfPass.getPassword()), String.valueOf(vista.optionDialog.pfAdmin.getPassword()));
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista());
                break;
            case "anadirCalzado": {
                try {
                    if (comprobarCalzadoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.calzadosTabla.clearSelection();
                    } else if (modelo.calzadoCodigoSKUYaExiste(vista.txtCodigoSKU.getText())) {
                        Util.showErrorAlert("Ese código SKU ya existe.\nIntroduce un calzado diferente");
                        vista.calzadosTabla.clearSelection();
                    } else {
                        modelo.insertarCalzado(
                                vista.txtModelo.getText(),
                                vista.txtCodigoSKU.getText(),
                                String.valueOf(vista.comboTienda.getSelectedItem()),
                                String.valueOf(vista.comboTipoCalzado.getSelectedItem()),
                                String.valueOf(vista.comboMarca.getSelectedItem()),
                                Float.parseFloat(vista.txtPrecioCalzado.getText()),
                                vista.fechaDeLanzamiento.getDate());
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.calzadosTabla.clearSelection();
                }
                borrarCamposCalzados();
                refrescarCalzados();
            }
            break;
            case "modificarCalzado": {
                try {
                    if (comprobarCalzadoVacio()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.calzadosTabla.clearSelection();
                    } else {
                        modelo.modificarCalzado(
                                vista.txtModelo.getText(),
                                vista.txtCodigoSKU.getText(),
                                String.valueOf(vista.comboTienda.getSelectedItem()),
                                String.valueOf(vista.comboTipoCalzado.getSelectedItem()),
                                String.valueOf(vista.comboMarca.getSelectedItem()),
                                Float.parseFloat(vista.txtPrecioCalzado.getText()),
                                vista.fechaDeLanzamiento.getDate(),
                                (Integer) vista.calzadosTabla.getValueAt(vista.calzadosTabla.getSelectedRow(), 0));
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.calzadosTabla.clearSelection();
                }
                borrarCamposCalzados();
                refrescarCalzados();
            }
            break;
            case "eliminarCalzado":
                modelo.eliminarCalzado((Integer) vista.calzadosTabla.getValueAt(vista.calzadosTabla.getSelectedRow(), 0));
                borrarCamposCalzados();
                refrescarCalzados();
                break;
            case "anadirMarca": {
                try {
                    if (comprobarMarcaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.marcasTabla.clearSelection();
                    } else if (modelo.marcaNombreYaExiste(vista.txtNombreMarca.getText(),
                            vista.txtNombreLegalEmpresa.getText())) {
                        Util.showErrorAlert("Ese nombre ya existe.\nIntroduce una marca diferente");
                        vista.marcasTabla.clearSelection();
                    } else {
                        modelo.insertarMarca(vista.txtNombreMarca.getText(),
                                vista.txtNombreLegalEmpresa.getText(),
                                vista.fechaFundacion.getDate(),
                                vista.txtPaisFundacion.getText());
                        refrescarMarcas();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.marcasTabla.clearSelection();
                }
                borrarCamposMarcas();
            }
            break;
            case "modificarMarca": {
                try {
                    if (comprobarMarcaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.marcasTabla.clearSelection();
                    } else {
                        modelo.modificarMarca(vista.txtNombreMarca.getText(), vista.txtNombreLegalEmpresa.getText(),
                                vista.fechaFundacion.getDate(), vista.txtPaisFundacion.getText(),
                                (Integer) vista.marcasTabla.getValueAt(vista.marcasTabla.getSelectedRow(), 0));
                        refrescarMarcas();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.marcasTabla.clearSelection();
                }
                borrarCamposMarcas();
            }
            break;
            case "eliminarMarca":
                modelo.eliminarMarca((Integer) vista.marcasTabla.getValueAt(vista.marcasTabla.getSelectedRow(), 0));
                borrarCamposMarcas();
                refrescarMarcas();
                break;
            case "anadirTienda": {
                try {
                    if (comprobarTiendaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tiendasTabla.clearSelection();
                    } else if (modelo.tiendaNombreYaExiste(vista.txtNombreTienda.getText())) {
                        Util.showErrorAlert("Ese nombre ya existe.\nIntroduce una tienda diferente.");
                        vista.tiendasTabla.clearSelection();
                    } else {
                        modelo.insertarTienda(vista.txtNombreTienda.getText(), vista.txtEmail.getText(),
                                vista.txtTelefono.getText(),
                                (String) vista.comboTipoTienda.getSelectedItem(),
                                vista.txtWeb.getText());
                        refrescarTienda();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tiendasTabla.clearSelection();
                }
                borrarCamposTiendas();
            }
            break;
            case "modificarTienda": {
                try {
                    if (comprobarTiendaVacia()) {
                        Util.showErrorAlert("Rellena todos los campos");
                        vista.tiendasTabla.clearSelection();
                    } else {
                        modelo.modificarTienda(vista.txtNombreTienda.getText(), vista.txtEmail.getText(), vista.txtTelefono.getText(),
                                String.valueOf(vista.comboTipoTienda.getSelectedItem()), vista.txtWeb.getText(),
                                (Integer) vista.tiendasTabla.getValueAt(vista.tiendasTabla.getSelectedRow(), 0));
                        refrescarTienda();
                    }
                } catch (NumberFormatException nfe) {
                    Util.showErrorAlert("Introduce números en los campos que lo requieren");
                    vista.tiendasTabla.clearSelection();
                }
                borrarCamposTiendas();
            }
            break;
            case "eliminarTienda":
                modelo.eliminarTienda((Integer) vista.tiendasTabla.getValueAt(vista.tiendasTabla.getSelectedRow(), 0));
                borrarCamposTiendas();
                refrescarTienda();
                break;
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    private void refrescarTienda() {
        try {
            vista.tiendasTabla.setModel(construirTableModelTiendas(modelo.consultarTienda()));
            vista.comboTienda.removeAllItems();
            for(int i = 0; i < vista.dtmTiendas.getRowCount(); i++) {
                vista.comboTienda.addItem(vista.dtmTiendas.getValueAt(i, 0)+" - "+
                        vista.dtmTiendas.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private  DefaultTableModel construirTableModelTiendas(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmTiendas.setDataVector(data, columnNames);

        return vista.dtmTiendas;

    }

    private void refrescarMarcas() {
        try {
            vista.marcasTabla.setModel(construirTableModelMarcas(modelo.consultarMarca()));
            vista.comboMarca.removeAllItems();
            for(int i = 0; i < vista.dtmMarcas.getRowCount(); i++) {
                vista.comboMarca.addItem(vista.dtmMarcas.getValueAt(i, 0)+" - "+
                        vista.dtmMarcas.getValueAt(i, 2)+", "+vista.dtmMarcas.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelMarcas(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmMarcas.setDataVector(data, columnNames);

        return vista.dtmMarcas;

    }

    private void refrescarCalzados() {
        try {
            vista.calzadosTabla.setModel(construirTableModelCalzados(modelo.consultarCalzados()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelCalzados(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmCalzados.setDataVector(data, columnNames);

        return vista.dtmCalzados;

    }

    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }

    private void setOptions() {
        vista.optionDialog.txtIP.setText(modelo.getIp());
        vista.optionDialog.txtUsuario.setText(modelo.getUser());
        vista.optionDialog.pfPass.setText(modelo.getPassword());
        vista.optionDialog.pfAdmin.setText(modelo.getAdminPassword());
    }

    private void borrarCamposCalzados() {
        vista.comboTienda.setSelectedIndex(-1);
        vista.comboMarca.setSelectedIndex(-1);
        vista.txtModelo.setText("");
        vista.txtCodigoSKU.setText("");
        vista.comboTipoCalzado.setSelectedIndex(-1);
        vista.txtPrecioCalzado.setText("");
        vista.fechaDeLanzamiento.setText("");
    }

    private void borrarCamposMarcas() {
        vista.txtNombreMarca.setText("");
        vista.txtNombreLegalEmpresa.setText("");
        vista.txtPaisFundacion.setText("");
        vista.fechaFundacion.setText("");
    }

    private void borrarCamposTiendas() {
        vista.txtNombreTienda.setText("");
        vista.txtEmail.setText("");
        vista.txtTelefono.setText("");
        vista.comboTipoTienda.setSelectedIndex(-1);
        vista.txtWeb.setText("");
    }

    private boolean comprobarCalzadoVacio() {
        return vista.txtModelo.getText().isEmpty() ||
                vista.txtPrecioCalzado.getText().isEmpty() ||
                vista.txtCodigoSKU.getText().isEmpty() ||
                vista.comboTipoCalzado.getSelectedIndex() == -1 ||
                vista.comboMarca.getSelectedIndex() == -1 ||
                vista.comboTienda.getSelectedIndex() == -1 ||
                vista.fechaDeLanzamiento.getText().isEmpty();
    }

    private boolean comprobarMarcaVacia() {
        return vista.txtNombreLegalEmpresa.getText().isEmpty() ||
                vista.txtNombreMarca.getText().isEmpty() ||
                vista.txtPaisFundacion.getText().isEmpty() ||
                vista.fechaFundacion.getText().isEmpty();
    }

    private boolean comprobarTiendaVacia() {
        return vista.txtNombreTienda.getText().isEmpty() ||
                vista.txtEmail.getText().isEmpty() ||
                vista.txtTelefono.getText().isEmpty() ||
                vista.comboTipoTienda.getSelectedIndex() == -1 ||
                vista.txtWeb.getText().isEmpty();
    }
    private void desconectar() {
        modelo.desconectar();
        vista.bloquearVistaExceptoMenu();
        vista.mostrarDialogDesconectado();
        vista.itemDesconectar.setText("Conectar");
        conectado = false;
    }

    private void conectar() {
        modelo.conectar();
        vista.desbloquearVista();
        vista.cerrarDialogDesconectado();
        refrescarTodo();
        vista.itemDesconectar.setText("Desconectar");
        conectado = true;
    }

    /*LISTENERS IPLEMENTOS NO UTILIZADOS*/

    private void addItemListeners(Controlador controlador) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}