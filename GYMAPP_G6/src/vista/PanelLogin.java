package vista;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tFUsuario;
	private JPasswordField tFContrasena;
	private JButton btnLogin, btnSignUp;

	public PanelLogin() {
		
		setBounds(0, 0, 900, 600);
		setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(347, 134, 150, 30);
		add(lblUsuario);
		
		tFUsuario = new JTextField();
		tFUsuario.setBounds(507, 136, 206, 30);
		add(tFUsuario);
		tFUsuario.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContrasena.setBounds(347, 187, 150, 30);
		add(lblContrasena);
		
		tFContrasena = new JPasswordField();
		tFContrasena.setBounds(507, 189, 206, 30);
		add(tFContrasena);
		
		btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setBounds(416, 257, 218, 30);
		add(btnLogin);

		JLabel lblLogin = new JLabel("INICIO DE SESIÓN");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(380, 10, 313, 66);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 30));
		add(lblLogin);
		
		btnSignUp = new JButton("Crear cuenta");
		btnSignUp.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSignUp.setBounds(416, 315, 218, 30);
		add(btnSignUp);
	}


	public JTextField gettFUsuario() {
		return tFUsuario;
	}

	public void settFUsuario(JTextField tFUsuario) {
		this.tFUsuario = tFUsuario;
	}

	public JPasswordField gettFContrasena() {
		return tFContrasena;
	}

	public void settFContrasena(JPasswordField tFContrasena) {
		this.tFContrasena = tFContrasena;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JButton getBtnSignUp() {
		return btnSignUp;
	}

	public void setBtnSignUp(JButton btnSignUp) {
		this.btnSignUp = btnSignUp;
	}
}