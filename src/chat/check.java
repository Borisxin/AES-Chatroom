package chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class check extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					check frame = new check("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public check(String s) {
		super("your key number");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYourKeyIs = new JLabel("your key is set to:");
		lblYourKeyIs.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 36));
		lblYourKeyIs.setBounds(55, 13, 318, 116);
		contentPane.add(lblYourKeyIs);
		
		JLabel keynum = new JLabel(s);
		keynum.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 29));
		keynum.setBounds(154, 126, 202, 33);
		contentPane.add(keynum);
		
		JButton btnNewButton = new JButton("ok");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnNewButton.setBounds(154, 187, 99, 27);
		contentPane.add(btnNewButton);
	}

}
