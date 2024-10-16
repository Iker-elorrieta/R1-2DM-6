package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	public static enum enumPaneles {
		PANEL_REGISTRO, PANEL_LOGIN
	}

	private JPanel panelContenedor;
	private PanelRegistro panelRegistro;
	private PanelLogin panelLogin;

	public Principal() {

		crearPanelContenedor();

		crearPanelRegistro();

		crearPanelLogin();

	}

	private void crearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);
	}

	private void crearPanelRegistro() {
		panelRegistro = new PanelRegistro();
		panelRegistro.setBounds(100, 100, 900, 600);
		panelContenedor.add(panelRegistro);
		panelRegistro.setVisible(false);
	}

	private void crearPanelLogin() {
		panelLogin = new PanelLogin();
		panelLogin.setBounds(100, 100, 900, 600);
		panelContenedor.add(panelLogin);
		panelLogin.setVisible(false);
	}

	public void mVisualizarPaneles(enumPaneles panel) {
		panelRegistro.setVisible(false);
		panelLogin.setVisible(false);

		switch (panel) {
		case PANEL_REGISTRO:
			panelRegistro.setVisible(true);
			break;
		case PANEL_LOGIN:
			panelLogin.setVisible(true);
			break;

		default:
			break;
		}
	}
}
