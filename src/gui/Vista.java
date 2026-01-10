package gui;

import com.github.lgooddatepicker.components.DatePicker;
import gui.base.enums.Calzados;
import gui.base.enums.TiposTiendas;
import gui.base.enums.TipoEnvio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Vista extends JFrame {

    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private final static String TITULO_FRAME = "Aplicación calzados";

    // diálogo desconectado
    JDialog dialogDesconectado;

    // calzados
    JPanel JPanelCalzado;
    JTextField txtModelo;
    JComboBox comboMarca;
    JComboBox comboTienda;
    JComboBox comboTipoCalzado;
    DatePicker fechaDeLanzamiento;
    JTextField txtCodigoSKU;
    JTextField txtPrecioCalzado;
    JButton btnCalzadosEliminar;
    JButton btnCalzadosModificar;
    JButton btnCalzadosAnadir;
    JTable calzadosTabla;

    // marcas
    JPanel JPanelMarca;
    JTextField txtNombreMarca;
    JTextField txtNombreLegalEmpresa;
    JTextField txtPaisFundacion;
    DatePicker fechaFundacion;
    JButton btnMarcasAnadir;
    JButton btnMarcasModificar;
    JButton btnMarcasEliminar;
    JTable marcasTabla;

    // tiendas
    JPanel JPanelTienda;
    JTextField txtNombreTienda;
    JTextField txtEmail;
    JTextField txtTelefono;
    JComboBox comboTipoTienda;
    JTextField txtWeb;
    JButton btnTiendasAnadir;
    JButton btnTiendasModificar;
    JButton btnTiendasEliminar;
    JTable tiendasTabla;

    // pedidos
    JPanel JPanelPedido;
    JTextField txtCodigoSeguimiento;
    JComboBox comboCalzado;
    JComboBox comboPedidoMarca;
    JComboBox comboPedidoTienda;
    JSpinner spinnerCantidad;
    JTextField txtNombreDestinatario;
    ButtonGroup buttonGroup1;
    JRadioButton jrbDomicilio;
    JRadioButton jrbTienda;
    JRadioButton jrbPuntoDeRecogida;
    JTextField txtDireccion;
    JButton btnPedidosEliminar;
    JButton btnPedidosModificar;
    JButton btnPedidosAnadir;
    JTable pedidosTabla;

    //busqueda
    private JLabel etiquetaEstado;

    // default table model
    DefaultTableModel dtmTiendas;
    DefaultTableModel dtmMarcas;
    DefaultTableModel dtmCalzados;
    DefaultTableModel dtmPedidos;

    // menubar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemDarkMode;
    JMenuItem itemSalir;

    // cuadro diálogo OptionDialog
    OptionDialog optionDialog;
    JDialog adminPasswordDialog;
    JButton btnValidate;
    JPasswordField adminPassword;

    public Vista() {
        super(TITULO_FRAME);
        initFrame();
    }

    public void initFrame() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // ICONO DEL FRAME
        this.setIconImage(
                new ImageIcon(getClass().getResource("/jordan.png")).getImage()
        );
        // Lo colocamos donde el Frame ya existe, pero todavía no es visible (después de configurar el frame básico)
        // Para que coja la imagen cuando creemos el JAR (la imagen tiene que ir en resources
        this.pack();
        this.setSize(new Dimension(this.getWidth() + 100, this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        optionDialog = new OptionDialog(this);
        setMenu();
        setAdminDialog();
        setEnumComboBox();
        //setTipoEnvioRadioButtons();
        setTableModels();
        crearDialogDesconectado();
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemDarkMode = new JMenuItem("Dark Mode");
        itemDarkMode.setActionCommand("Dark Mode");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");

        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemDarkMode);
        menu.add(itemSalir);

        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    public void setAdminDialog() {
        btnValidate = new JButton("Validar");
        btnValidate.setActionCommand("abrirOpciones");
        adminPassword = new JPasswordField();
        adminPassword.setPreferredSize(new Dimension(100, 26));
        Object[] options = new Object[]{adminPassword, btnValidate};
        JOptionPane jop = new JOptionPane("Introduce la contraseña", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION, null, options);
        adminPasswordDialog = new JDialog(this, "Opciones", true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

    public void crearDialogDesconectado() {
        dialogDesconectado = new JDialog(this, "Desconectado", false); // modal
        dialogDesconectado.setSize(300, 150);
        dialogDesconectado.setLocationRelativeTo(this);
        dialogDesconectado.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JLabel lblMensaje = new JLabel("Estás desconectado :(", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 14));

        dialogDesconectado.setLayout(new BorderLayout());
        dialogDesconectado.add(lblMensaje, BorderLayout.CENTER);
    }


    private void setEnumComboBox() {
        for (TiposTiendas constant : TiposTiendas.values()) {
            comboTipoTienda.addItem(constant.getValor());
        }
        comboTipoTienda.setSelectedIndex(-1);

        for (Calzados constant : Calzados.values()) {
            comboTipoCalzado.addItem(constant.getValor());
        }
        comboTipoCalzado.setSelectedIndex(-1);

        // Combos para pedidos
      //  comboPedidoMarca = new JComboBox<>();
      //  comboPedidoTienda = new JComboBox<>();

      //  for (int i = 0; i < comboMarca.getItemCount(); i++) {
           // comboPedidoMarca.addItem(comboMarca.getItemAt(i));
      //  }

        //for (int i = 0; i < comboTienda.getItemCount(); i++) {
       //     comboPedidoTienda.addItem(comboTienda.getItemAt(i));
     //   }

       // JPanelPedido.add(comboPedidoMarca);
       // JPanelPedido.add(comboPedidoTienda);
    }

    //private void setTipoEnvioRadioButtons() {
        // Crear el ButtonGroup
        //buttonGroup1 = new ButtonGroup();

        // Crear radio buttons dinámicamente desde el enum
       // for (TipoEnvio tipo : TipoEnvio.values()) {
          //  JRadioButton rb = new JRadioButton(tipo.getValor());
          //  rb.setActionCommand(tipo.name()); // guardar el enum para recuperar luego
           // buttonGroup1.add(rb);
           // JPanelPedido.add(rb); // añadir al panel de pedidos
       // }

        // Opcional: no seleccionar ninguno al inicio
      //  buttonGroup1.clearSelection();
 //   }

    private void setTableModels() {
        dtmCalzados = new DefaultTableModel();
        calzadosTabla.setModel(dtmCalzados);

        dtmMarcas = new DefaultTableModel();
        marcasTabla.setModel(dtmMarcas);

        dtmTiendas = new DefaultTableModel();
        tiendasTabla.setModel(dtmTiendas);

      //  dtmPedidos = new DefaultTableModel();
     //   pedidosTabla.setModel(dtmPedidos);
    }

    public void mostrarDialogDesconectado() {
        dialogDesconectado.setVisible(true);
    }

    public void cerrarDialogDesconectado() {
        dialogDesconectado.dispose();
    }

    public void bloquearVista() {
        setEnabledRecursivo(panel1, false);
    }

    public void desbloquearVista() {
        setEnabledRecursivo(panel1, true);
    }

    private void setEnabledRecursivo(Container container, boolean enabled) {
        for (Component c : container.getComponents()) {
            c.setEnabled(enabled);
            if (c instanceof Container) {
                setEnabledRecursivo((Container) c, enabled);
            }
        }
    }

    public void bloquearVistaExceptoMenu() {
        bloquearVista();
        itemDesconectar.setEnabled(true);
        itemSalir.setEnabled(true);
    }
}
