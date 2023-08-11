package validadores;

import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class NomeValidador {
	
	public static String nomeValidador(String input) {
	   try {
		   if(!Pattern.matches("^[\\p{L} .'-]+[/^\\S+$/]+$", input)) {
			   JOptionPane.showMessageDialog(null,"Nome inválido! Digite apenas letras!");
			   return null;
			} 
		   
	   }catch(Exception e) {
		   
		   e.printStackTrace();
	   }
	   return input;
	}
	
}
