package vista;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PanelLogin() {
		setBounds(0, 0, 880, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setBounds(400, 5, 100, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		add(lblNewLabel);
	}
}
