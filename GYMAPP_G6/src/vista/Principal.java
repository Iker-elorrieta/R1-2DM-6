package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;

    public static enum enumAcciones {
        PANEL_REGISTRO, PANEL_LOGIN
    }

    private JPanel panelContenedor;
    private PanelRegistro panelRegistro;
    private PanelLogin panelLogin;

    public Principal() {

        crearPanelContenedor();
        crearPanelLogin();
        crearPanelRegistro();

        // Mostrar el panel de login al inicio.
        visualizarPaneles(enumAcciones.PANEL_LOGIN);
    }

    private void crearPanelContenedor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 900, 600);
        panelContenedor = new JPanel();
        panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelContenedor);
        panelContenedor.setLayout(null);
    }

    private void crearPanelLogin() {
        panelLogin = new PanelLogin();
        panelContenedor.add(panelLogin);
        panelLogin.setVisible(false); // Ocultar inicialmente
    }

    private void crearPanelRegistro() {
        panelRegistro = new PanelRegistro();
        panelContenedor.add(panelRegistro);
        panelRegistro.setVisible(false); // Ocultar inicialmente
    }

    public void visualizarPaneles(enumAcciones panel) {
        // Ocultar ambos paneles primero.
        panelLogin.setVisible(false);
        panelRegistro.setVisible(false);

        switch (panel) {
            case PANEL_LOGIN:
                panelLogin.setVisible(true);
                break;
            case PANEL_REGISTRO:
                panelRegistro.setVisible(true);
                break;
        }
    }

    // Getters para los paneles
    public PanelRegistro getPanelRegistro() {
        return panelRegistro;
    }

    public PanelLogin getPanelLogin() {
        return panelLogin;
    }
}