package client;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
	
	private static final String URL_BASE = "http://localhost:8080";

	public static final ObjectMapper MAPPER = new ObjectMapper()
			.setSerializationInclusion(Include.NON_NULL)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	private static Retrofit newRetrofit() {
		return new Retrofit.Builder()
				.baseUrl(URL_BASE)
				.addConverterFactory(JacksonConverterFactory.create(MAPPER))
				.build();
	}

	public static ContatoClientRetrofit contatoClient() {
		return newRetrofit().create(ContatoClientRetrofit.class);
	}
	
	public static UsuarioClientRetrofit usuarioClient() {
		return newRetrofit().create(UsuarioClientRetrofit.class);
	}
}
