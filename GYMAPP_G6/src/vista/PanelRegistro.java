package vista;

import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import modelo.Usuario.IdiomaPreferido;
import modelo.Usuario.TemaPreferido;
import javax.swing.border.LineBorder;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tFRegistroNombre, tFRegistroApellido, tFRegistroEmail, tFRegistroUser;
	private JPasswordField pFRegistroPassword;
	private JComboBox<IdiomaPreferido> cBRegistroIdioma;
	private JComboBox<TemaPreferido> cBRegistroTema;
	private JButton btnRegistroSignUp, btnReturn;
	private JDateChooser fechaNacimientoCalendar;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	// LÍMITES DE FECHAS
	Calendar ahoraMismo = Calendar.getInstance();
	int ano = ahoraMismo.get(Calendar.YEAR);
	int mes = ahoraMismo.get(Calendar.MONTH) + 1;
	int dia = ahoraMismo.get(Calendar.DATE);
	String minString = (ano - 100) + "-" + mes + "-" + dia;
	String maxString = ano + "-" + mes + "-" + dia;

	public PanelRegistro() {
		setBounds(0, 0, 880, 560);
		setLayout(null);
		setBackground(new Color(173, 216, 230)); // Fondo azul claro

		JLabel lblNewLabel = new JLabel("REGISTRO");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setForeground(new Color(10, 75, 128)); // Azul oscuro
		lblNewLabel.setBounds(307, 62, 265, 84);
		add(lblNewLabel);

		JLabel lblRegistroName = new JLabel("Nombre:");
		lblRegistroName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRegistroName.setBounds(156, 172, 79, 25);
		lblRegistroName.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblRegistroName);

		JLabel lblRegistroSurname = new JLabel("Apellido:");
		lblRegistroSurname.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRegistroSurname.setBounds(156, 227, 79, 25);
		lblRegistroSurname.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblRegistroSurname);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDeNacimiento.setBounds(156, 353, 150, 25);
		lblFechaDeNacimiento.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblFechaDeNacimiento);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEmail.setBounds(156, 287, 79, 25);
		lblEmail.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblEmail);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsuario.setBounds(486, 172, 79, 25);
		lblUsuario.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblUsuario);

		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(486, 227, 79, 25);
		lblPassword.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblPassword);

		JLabel lblIdioma = new JLabel("Idioma:");
		lblIdioma.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblIdioma.setBounds(486, 287, 79, 25);
		lblIdioma.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblIdioma);

		JLabel lblTema = new JLabel("Tema:");
		lblTema.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTema.setBounds(486, 353, 79, 25);
		lblTema.setForeground(new Color(10, 75, 128)); // Azul oscuro
		add(lblTema);

		fechaNacimientoCalendar = new JDateChooser();
		fechaNacimientoCalendar.setBounds(294, 351, 175, 30);
		fechaNacimientoCalendar.setBackground(Color.WHITE);
		fechaNacimientoCalendar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); // Borde
																													// azul
																													// oscuro
		add(fechaNacimientoCalendar);

		JTextFieldDateEditor editor = (JTextFieldDateEditor) fechaNacimientoCalendar.getDateEditor();
		editor.setEditable(false);

		try {
			fechaNacimientoCalendar.setMaxSelectableDate(dateFormat.parse(maxString));
			fechaNacimientoCalendar.setMinSelectableDate(dateFormat.parse(minString));
			fechaNacimientoCalendar.setDate(dateFormat.parse(maxString));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		tFRegistroNombre = new JTextField();
		tFRegistroNombre.setBounds(242, 172, 175, 25);
		tFRegistroNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFRegistroNombre);

		tFRegistroApellido = new JTextField();
		tFRegistroApellido.setBounds(242, 227, 175, 25);
		tFRegistroApellido.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFRegistroApellido);

		tFRegistroEmail = new JTextField();
		tFRegistroEmail.setBounds(242, 287, 175, 25);
		tFRegistroEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFRegistroEmail);

		tFRegistroUser = new JTextField();
		tFRegistroUser.setBounds(589, 172, 175, 25);
		tFRegistroUser.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(tFRegistroUser);

		pFRegistroPassword = new JPasswordField();
		pFRegistroPassword.setBounds(590, 227, 175, 25);
		pFRegistroPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		add(pFRegistroPassword);

		cBRegistroIdioma = new JComboBox<>(IdiomaPreferido.values());
		cBRegistroIdioma.setBounds(590, 286, 175, 25);
		cBRegistroIdioma.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		cBRegistroIdioma.setBackground(Color.WHITE);
		add(cBRegistroIdioma);

		cBRegistroTema = new JComboBox<>(TemaPreferido.values());
		cBRegistroTema.setBounds(590, 351, 175, 25);
		cBRegistroTema.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(10, 75, 128), 2)); 
		cBRegistroTema.setBackground(Color.WHITE);
		add(cBRegistroTema);

		btnReturn = new JButton("Atrás");
		btnReturn.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReturn.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnReturn.setBounds(22, 504, 89, 30);
		btnReturn.setBackground(new Color(10, 75, 128)); // Azul oscuro
		btnReturn.setForeground(Color.WHITE);
		add(btnReturn);

		btnRegistroSignUp = new JButton("Crear cuenta");
		btnRegistroSignUp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRegistroSignUp.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		btnRegistroSignUp.setBounds(376, 439, 128, 39);
		btnRegistroSignUp.setBackground(new Color(10, 75, 128)); // Azul oscuro
		btnRegistroSignUp.setForeground(new Color(255, 255, 255));
		add(btnRegistroSignUp);
	}

	public JTextField gettFRegistroNombre() {
		return tFRegistroNombre;
	}

	public void settFRegistroNombre(JTextField tFRegistroNombre) {
		this.tFRegistroNombre = tFRegistroNombre;
	}

	public JTextField gettFRegistroApellido() {
		return tFRegistroApellido;
	}

	public void settFRegistroApellido(JTextField tFRegistroApellido) {
		this.tFRegistroApellido = tFRegistroApellido;
	}

	public JTextField gettFRegistroEmail() {
		return tFRegistroEmail;
	}

	public void settFRegistroEmail(JTextField tFRegistroEmail) {
		this.tFRegistroEmail = tFRegistroEmail;
	}

	public JTextField gettFRegistroUser() {
		return tFRegistroUser;
	}

	public void settFRegistroUser(JTextField tFRegistroUser) {
		this.tFRegistroUser = tFRegistroUser;
	}

	public JPasswordField getpFRegistroPassword() {
		return pFRegistroPassword;
	}

	public void setpFRegistroPassword(JPasswordField pFRegistroPassword) {
		this.pFRegistroPassword = pFRegistroPassword;
	}

	public JComboBox<IdiomaPreferido> getcBRegistroIdioma() {
		return cBRegistroIdioma;
	}

	public void setcBRegistroIdioma(JComboBox<IdiomaPreferido> cBRegistroIdioma) {
		this.cBRegistroIdioma = cBRegistroIdioma;
	}

	public JComboBox<TemaPreferido> getcBRegistroTema() {
		return cBRegistroTema;
	}

	public void setcBRegistroTema(JComboBox<TemaPreferido> cBRegistroTema) {
		this.cBRegistroTema = cBRegistroTema;
	}

	public JButton getBtnRegistroSignUp() {
		return btnRegistroSignUp;
	}

	public void setBtnRegistroSignUp(JButton btnRegistroSignUp) {
		this.btnRegistroSignUp = btnRegistroSignUp;
	}

	public JButton getBtnReturn() {
		return btnReturn;
	}

	public void setBtnReturn(JButton btnReturn) {
		this.btnReturn = btnReturn;
	}

	public JDateChooser getFechaNacimientoCalendar() {
		return fechaNacimientoCalendar;
	}

	public void setFechaNacimientoCalendar(JDateChooser fechaNacimientoCalendar) {
		this.fechaNacimientoCalendar = fechaNacimientoCalendar;
	}

}
