package client;

import java.util.List;
import java.util.Optional;

import models.Contato;
import models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioClientRetrofit {
	
	@GET("usuarios")
	Call<List<Usuario>> listarTodos();
	
	@GET("usuarios/{id}")
	Call<Optional<Usuario>> acharUm(@Path("id") Long id);
	
	@POST("usuarios")
	Call<Void> salvar(@Body Usuario usuario);
	
	@POST("login")
	Call<Void> logIn(@Body Usuario usuario);
	
	@PUT("usuarios/{id}")
	Call<Void> alterar(@Path("id") Long id, @Body Contato contato);
	
	@DELETE("usuarios/{id}")
	Call<Void> excluir(@Path("id") Long id);
}
