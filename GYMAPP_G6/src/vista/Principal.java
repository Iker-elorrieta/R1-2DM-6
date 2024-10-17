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

		crearPanelRegistro();

		crearPanelLogin();
		
		//visualizarPaneles(enumAcciones.PANEL_LOGIN);

	}

	private void crearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 600);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);
	}

	private void crearPanelRegistro() {
		panelRegistro = new PanelRegistro();
		panelRegistro.setBounds(0, 0, 880, 560);
		panelContenedor.add(panelRegistro);
		panelRegistro.setVisible(false);
	}

	private void crearPanelLogin() {
		panelLogin = new PanelLogin();
		panelLogin.setBounds(0, 0, 880, 560);
		panelContenedor.add(panelLogin);
		panelLogin.setVisible(true	);
	}

	public void visualizarPaneles(enumAcciones panel) {
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
