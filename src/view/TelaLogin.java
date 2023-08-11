package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.RetrofitInicializador;
import client.UsuarioClientRetrofit;
import models.ErroDto;
import models.Usuario;
import retrofit2.Call;
import retrofit2.Response;
import util.ClientUtil;

public class TelaLogin {

	private JTextField tfLogin;
	private JTextField tfSenha;
	private JButton btnEntrar;

	private JFrame jFrame;
	
	private UsuarioClientRetrofit client = RetrofitInicializador.usuarioClient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jFrame = new JFrame("Login");
		jFrame.setSize(400, 400);
		jFrame.setLocationRelativeTo(null);
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);

		setComponents();
		setBackground();

	}

	public void setBackground() {
		JLabel lblBackground = new JLabel("background");
		lblBackground.setForeground(new Color(255, 255, 204));
		lblBackground.setIcon(new ImageIcon("imagens/background.png"));
		lblBackground.setBounds(0, 0, 386, 363);
		jFrame.getContentPane().add(lblBackground);

	}

	public void setComponents() {

		JPanel panel = new JPanel();
		panel.setForeground(new Color(250, 251, 219));
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(53, 40, 280, 287);
		jFrame.getContentPane().add(panel);
		panel.setLayout(null);

		ImageIcon img = new ImageIcon("imagens/perfil.png");
		img.setImage(img.getImage().getScaledInstance(105, 105, Image.SCALE_AREA_AVERAGING));

		JLabel lblImgLogin = new JLabel();
		lblImgLogin.setBounds(80, -10, 150, 150);
		lblImgLogin.setIcon(img);
		panel.add(lblImgLogin);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLogin.setBounds(10, 110, 55, 28);
		panel.add(lblLogin);
		lblLogin.setForeground(new Color(244, 102, 113));

		tfLogin = new JTextField();
		tfLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_DOWN) {
					tfSenha.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					btnEntrar.requestFocus();
				}
			}
		});
		tfLogin.setForeground(new Color(0, 0, 0));
		tfLogin.setBackground(new Color(255, 255, 255));
		tfLogin.setBounds(10, 138, 260, 20);
		panel.add(tfLogin);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setForeground(new Color(244, 102, 113));
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(10, 179, 55, 28);
		panel.add(lblSenha);

		tfSenha = new JPasswordField();
		tfSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_DOWN) {
					btnEntrar.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					tfLogin.requestFocus();
				}
			}
		});
		tfSenha.setBounds(10, 208, 260, 20);
		panel.add(tfSenha);

		btnEntrar = new JButton("Entrar");
		btnEntrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					entrar();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					tfSenha.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					tfLogin.requestFocus();
				}
			}
		});
		btnEntrar.setBounds(86, 253, 89, 23);
		btnEntrar.setForeground(Color.WHITE);
		btnEntrar.setBackground(new Color(244, 102, 113));

		btnEntrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				entrar();
			}

		});

		panel.add(btnEntrar);
	}
	
	
	public void entrar() {
		if (tfLogin.getText() == null) {
			JOptionPane.showMessageDialog(jFrame, "Preencha o campo Login");
		} else if (tfSenha.getText() == null) {
			JOptionPane.showMessageDialog(jFrame, "Preencha o campo Senha");
		} else {
			try {
				
				Call<Void> call = client.logIn(new Usuario(tfLogin.getText(), tfSenha.getText()));
				
				Response<Void> response = call.execute();
				
				int httpCodigo = response.code();
				
				if(httpCodigo == 200) {

					new TelaCadastroContato();
					jFrame.dispose();

				} else {
					ErroDto erroDto = ClientUtil.erroDto(response);
					
					JOptionPane.showMessageDialog(jFrame, erroDto.getErro());
				}
			}catch (Exception ex) {
			JOptionPane.showMessageDialog(jFrame, "Sistema indisponível!");
			ex.printStackTrace();
			}
		}
	}
}
