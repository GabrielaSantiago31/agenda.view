package client;

import java.util.List;
import java.util.Optional;

import models.Contato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContatoClientRetrofit {
	
	@GET("contatos")
	Call<List<Contato>> getContatos();
	
	@GET("contatos/{id}")
	Call<Optional<Contato>> getContatoById(@Path("id") Long id);
	
	@POST("contatos")
	Call<Void> saveAContact(@Body Contato contato);
	
	@PUT("contatos/{id}")
	Call<Void> update(@Path("id") Long id, @Body Contato contato);
	
	@DELETE("contatos/{id}")
	Call<Void> delete(@Path("id") Long id);
}
