package vista;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class PanelRegistro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public PanelRegistro() {
		setBounds(0, 0, 880, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTRO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(370, 62, 159, 86);
		add(lblNewLabel);
		
	}
}
