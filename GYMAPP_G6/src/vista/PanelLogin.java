package vista;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tFUsuario;
	private JPasswordField TFContrasena;

	public PanelLogin() {
<<<<<<< HEAD
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
		
		TFContrasena = new JPasswordField();
		TFContrasena.setBounds(507, 189, 206, 30);
		add(TFContrasena);
		
		JButton btnLogin = new JButton("Iniciar sesión");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setBounds(411, 306, 218, 30);
		add(btnLogin);
=======
		setBounds(0, 0, 880, 560);
>>>>>>> branch 'Sprint1' of https://github.com/Iker-elorrieta/R1-2DM-6.git
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setBounds(400, 5, 100, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		add(lblNewLabel);
	}
}
