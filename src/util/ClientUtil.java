package util;

import java.io.IOException;
import java.util.ArrayList;

import models.ErroDto;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ClientUtil {
	
	private ClientUtil() {}
	
	public static ErroDto erroDto(Response<?> response) {
		ResponseBody errorBody = response.errorBody();
		
		if(errorBody != null) {
			try {
				String json = errorBody.string();
				
				return Json.objeto(json, ErroDto.class);
			} catch (IOException e) {

			}
		}
		
		return new ErroDto("Dados incorretos", new ArrayList<>()); 
	}
}
