package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import client.ContatoClientRetrofit;
import client.RetrofitInicializador;
import models.Contato;
import models.ErroDto;
import retrofit2.Call;
import retrofit2.Response;
import util.ClientUtil;
import validadores.EmailValidador;
import validadores.NomeValidador;
import validadores.TelefoneValidador;

public class TelaCadastroContato {

	private JFrame frame;
	private JLabel lblBackground;
	private JTextField tfNome;
	private JTextField tfTelefone;
	private JTextField tfRg;
	private JTable tblContatos;
	private JTextField tfEmail;
	private ButtonGroup rdbtnTipo;
	private DefaultTableModel defaultTableModel;

	private ContatoClientRetrofit client = RetrofitInicializador.contatoClient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaCadastroContato();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastroContato() {
		frame = new JFrame("Cadastro de Contatos");
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.setSize(450, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initialize();
		setBackground();
		loadData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 11, 78, 25);
		frame.getContentPane().add(lblNome);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefone.setBounds(10, 49, 78, 25);
		frame.getContentPane().add(lblTelefone);

		JLabel lblRg = new JLabel("RG");
		lblRg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRg.setBounds(10, 90, 49, 14);
		frame.getContentPane().add(lblRg);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(10, 122, 78, 25);
		frame.getContentPane().add(lblEmail);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo.setBounds(10, 158, 49, 25);
		frame.getContentPane().add(lblTipo);

		tfNome = new JTextField();
		tfNome.setBounds(73, 13, 352, 25);
		frame.getContentPane().add(tfNome);
		tfNome.setColumns(10);

		tfTelefone = new JTextField();
		tfTelefone.setColumns(10);
		tfTelefone.setBounds(73, 51, 352, 25);
		frame.getContentPane().add(tfTelefone);

		tfRg = new JTextField();
		tfRg.setColumns(10);
		tfRg.setBounds(73, 87, 352, 25);
		frame.getContentPane().add(tfRg);

		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(73, 122, 352, 25);
		frame.getContentPane().add(tfEmail);

		JRadioButton rdbtnPessoal = new JRadioButton("pessoal");
		rdbtnPessoal.setBackground(new Color(224, 243, 215));
		rdbtnPessoal.setBounds(73, 161, 78, 23);
		frame.getContentPane().add(rdbtnPessoal);

		JRadioButton rdbtnProfissional = new JRadioButton("profissional");
		rdbtnProfissional.setBackground(new Color(224, 243, 215));
		rdbtnProfissional.setBounds(186, 161, 117, 23);
		frame.getContentPane().add(rdbtnProfissional);

		JRadioButton rdbtnOutros = new JRadioButton("outros");
		rdbtnOutros.setBackground(new Color(224, 243, 215));
		rdbtnOutros.setBounds(335, 161, 90, 23);
		frame.getContentPane().add(rdbtnOutros);

		rdbtnTipo = new ButtonGroup();
		rdbtnTipo.add(rdbtnPessoal);
		rdbtnTipo.add(rdbtnProfissional);
		rdbtnTipo.add(rdbtnOutros);
		rdbtnPessoal.setActionCommand("pessoal");
		rdbtnProfissional.setActionCommand("profissional");
		rdbtnOutros.setActionCommand("outros");
		rdbtnPessoal.setSelected(true);

		JButton btnSalvar = new JButton();
		btnSalvar.setBounds(376, 191, 49, 25);
		btnSalvar.setIcon(new ImageIcon(retornaUmaImagem("imagens/check.png")));
		frame.getContentPane().add(btnSalvar);

		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String nome = NomeValidador.nomeValidador(tfNome.getText());
				String telefone = TelefoneValidador.telefoneValidator(tfTelefone.getText());
				String rg = tfRg.getText();
				String email = EmailValidador.emailValidador(tfEmail.getText());
				String tipo = (rdbtnTipo.getSelection().getActionCommand());

				Contato c = new Contato(nome, telefone, rg, email, tipo);
				
				int i = tblContatos.getSelectedRow();
					
				if (i == -1) {
					try {	
						Call<Void> call = client.saveAContact(c);
						Response<Void> response = call.execute();
						int httpCodigo = response.code();

						if(httpCodigo == 201) {	

							loadData(); 

							JOptionPane.showMessageDialog(null, "Contato salvo");
							limparCampos();
						}else {
							ErroDto erroDto = ClientUtil.erroDto(response);
							JOptionPane.showMessageDialog(frame, erroDto.getErro());
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Sistema indisponível!");
					}

				} else {

					Long contatoId = (long) tblContatos.getValueAt(i, 0);

					try {
						Call<Void> call = client.update(contatoId, c);
						Response<Void> response = call.execute();
						int httpCodigo = response.code();

						if(httpCodigo == 204) {
							loadData();
							limparCampos();

						}else {
							ErroDto erroDto = ClientUtil.erroDto(response);
							JOptionPane.showMessageDialog(frame, erroDto.getErro());
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Sistema indisponível!");
					}
				}
			}

		});

		JButton btnExcluir = new JButton();
		btnExcluir.setBounds(10, 327, 49, 25);
		btnExcluir.setIcon(new ImageIcon(retornaUmaImagem("imagens/excluir.png")));
		frame.getContentPane().add(btnExcluir);

		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int i = tblContatos.getSelectedRow();
					Long contatoId = (long) tblContatos.getValueAt(i, 0);
					
					if(i >= 0) {
						Call<Void> call = client.delete(contatoId);
						Response<Void> response = call.execute();
						int httpCodigo = response.code();
						
						if (httpCodigo == 204) {
							defaultTableModel.removeRow(i);
							limparCampos();
						}else {
							ErroDto erroDto = ClientUtil.erroDto(response);
							JOptionPane.showMessageDialog(frame, erroDto.getErro());
						}
					}
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Não foi possível remover o contato selecionado");
				}
			}

		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 225, 415, 98);
		frame.getContentPane().add(scrollPane, BorderLayout.NORTH);

		tblContatos = new JTable() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;	
			}
		};
		tblContatos.setFocusable(false); // seleciona como uma linha inteira ao invés de várias células
		tblContatos.setColumnSelectionAllowed(false); //setFocusable precisa deste para funcionar
		
		scrollPane.setViewportView(tblContatos);
		
		tblContatos.setBorder(UIManager.getBorder("ScrollPane.border"));
		
		tblContatos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					int selectedRow = tblContatos.getSelectedRow();
					if(selectedRow != -1) {
						int linhaSelecionada = tblContatos.getSelectedRow();
						String tblNome = defaultTableModel.getValueAt(linhaSelecionada, 1).toString();
						String tblTelefone = defaultTableModel.getValueAt(linhaSelecionada, 2).toString();
						String tblRg = defaultTableModel.getValueAt(linhaSelecionada, 3).toString();
						String tblEmail = defaultTableModel.getValueAt(linhaSelecionada, 4).toString();
		
						tfNome.setText(tblNome);
						tfTelefone.setText(tblTelefone);
						tfRg.setText(tblRg);
						tfEmail.setText(tblEmail);
					
					}
				}
			}
			
		});
		
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {	
				tblContatos.clearSelection();
				limparCampos();
			}
		});
	
	}

	private Image retornaUmaImagem(String png) {
		ImageIcon imageIcon = new ImageIcon(png);
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT));
		Image img = imageIcon.getImage();

		return img;
	}

	private void setBackground() {
		lblBackground = new JLabel("background");
		lblBackground.setBackground(new Color(224, 243, 215));
		lblBackground.setForeground(new Color(255, 255, 204));
		lblBackground.setIcon(new ImageIcon("imagens/background.png"));
		lblBackground.setBounds(0, 0, 436, 363);
		frame.getContentPane().add(lblBackground);
	}

	private void limparCampos() {
		tfNome.setText("");
		tfTelefone.setText("");
		tfRg.setText("");
		tfEmail.setText("");
	}


	private void loadData() {
		try {
			defaultTableModel = new DefaultTableModel();
			defaultTableModel.addColumn("Id");
			defaultTableModel.addColumn("Nome");
			defaultTableModel.addColumn("Telefone");
			defaultTableModel.addColumn("RG");
			defaultTableModel.addColumn("Email");
			defaultTableModel.addColumn("Tipo");
			
			Call<List<Contato>> call = client.getContatos();

			Response<List<Contato>> response = call.execute();

			int code = response.code();

			if (code == 200) {
				for (Contato contato : response.body()) {
					defaultTableModel.addRow(new Object[] { contato.getId(), contato.getNome(), contato.getTelefone(),
							contato.getRg(), contato.getEmail(), contato.getTipo() });
				}
			}

			tblContatos.setModel(defaultTableModel);
			
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, e.getMessage());
		}
	}
	
	public Component getFrame() {
		return null;
	}
}
