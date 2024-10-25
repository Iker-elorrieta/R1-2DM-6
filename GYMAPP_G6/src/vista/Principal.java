package vista;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	public static enum enumAcciones {
		PANEL_REGISTRO, REGISTRAR_USUARIO, PANEL_LOGIN, INICIAR_SESION, PANEL_WORKOUTS, PANEL_EJERCICIOS
	}

	private JPanel panelContenedor;
	private PanelRegistro panelRegistro;
	private PanelLogin panelLogin;
	private PanelWorkouts panelWorkouts;
	private PanelEjercicios panelEjercicios;

	public Principal() {

		crearPanelContenedor();
		crearPanelLogin();
		crearPanelRegistro();
		crearPanelWorkouts();
		crearPanelEjercicios();

		// Mostrar el panel de login al inicio.
		visualizarPaneles(enumAcciones.PANEL_LOGIN);
	}

	private void crearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 600);
		panelContenedor = new JPanel();
		panelContenedor.setBackground(new Color(141, 204, 235));
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

	private void crearPanelWorkouts() {
		panelWorkouts = new PanelWorkouts();
		panelContenedor.add(panelWorkouts);
		panelWorkouts.setVisible(false); // Ocultar inicialmente
	}

	private void crearPanelEjercicios() {
		panelEjercicios = new PanelEjercicios();
		panelContenedor.add(panelEjercicios);
		panelEjercicios.setVisible(false);
	}

	public void visualizarPaneles(enumAcciones panel) {
		// Ocultar ambos paneles primero.
		panelLogin.setVisible(false);
		panelRegistro.setVisible(false);
		panelWorkouts.setVisible(false);
		panelEjercicios.setVisible(false);

		switch (panel) {
		case PANEL_LOGIN:
			panelLogin.setVisible(true);
			colocarImg(getPanelLogin().getLblImg(), "media/logo.jpg", panelLogin);
			break;
		case PANEL_REGISTRO:
			panelRegistro.setVisible(true);
			break;
		case PANEL_WORKOUTS:
			panelWorkouts.setVisible(true);
		case PANEL_EJERCICIOS:
			panelEjercicios.setVisible(true);
			// colocarImg(getPanelEjercicios().getLblImgEjer(), "media/cej1.jpg",
			// panelEjercicios);
		default:
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

	public PanelWorkouts getPanelWorkouts() {
		return panelWorkouts;
	}

	public PanelEjercicios getPanelEjercicios() {
		return panelEjercicios;
	}

	public void colocarImg(JLabel lbl, String imgPath, JPanel panel) {
		ImageIcon icon = new ImageIcon(imgPath);
		if (icon.getIconWidth() > 0) {
			Image img = icon.getImage().getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT);
			lbl.setIcon(new ImageIcon(img));
			lbl.setText("");
		} else {
			lbl.setText("Imagen no encontrada");
		}
		panel.add(lbl);
	}

}