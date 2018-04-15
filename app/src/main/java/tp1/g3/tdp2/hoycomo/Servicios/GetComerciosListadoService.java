package tp1.g3.tdp2.hoycomo.Servicios;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tp1.g3.tdp2.hoycomo.Respuestas.ComerciosListadoResponse;

public interface GetComerciosListadoService {
    @GET("api/v1/comercio/{pagina}")
    Call<ComerciosListadoResponse> getComercios(@Path("comercio") int pagina);
}
