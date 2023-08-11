package validadores;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class EmailValidador {
	
	public static String emailValidador(String input) {
		
		try {
			if(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", input)) {
				JOptionPane.showMessageDialog(null,"Email inválido. Tente novamente.");
				return null;
			}
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		return input;
	}
}
