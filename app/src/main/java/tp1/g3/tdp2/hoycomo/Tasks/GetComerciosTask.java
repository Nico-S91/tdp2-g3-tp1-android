package tp1.g3.tdp2.hoycomo.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import tp1.g3.tdp2.hoycomo.Modelos.Comercio;
import tp1.g3.tdp2.hoycomo.Respuestas.Procedure;
import tp1.g3.tdp2.hoycomo.Respuestas.TaskResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tp1.g3.tdp2.hoycomo.Respuestas.ComerciosListadoResponse;
import tp1.g3.tdp2.hoycomo.Servicios.GetComerciosListadoService;


public class GetComerciosTask extends AsyncTask<Integer, String, TaskResponse<ComerciosListadoResponse>> {
    private static final String TAG = GetComerciosTask.class.getName();
    public static final int FAIL_CODE = 999;
    private String srvUrl;
    private Runnable onPreExecute;
    private Procedure<TaskResponse<ComerciosListadoResponse>> postExecute;

    public GetComerciosTask(String srvUrl, Runnable onPreExecute, Procedure<TaskResponse<ComerciosListadoResponse>> postExecute) {
        this.srvUrl = srvUrl;
        this.onPreExecute = onPreExecute;
        this.postExecute = postExecute;
    }

    @Override
    protected TaskResponse<ComerciosListadoResponse> doInBackground(Integer... params) {
        int pagina = params[0];

        publishProgress("Obteniendo Comercios");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(srvUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetComerciosListadoService service = retrofit.create(GetComerciosListadoService.class);
        Call<ComerciosListadoResponse> call = service.getComercios(pagina);
        try {
            Response<ComerciosListadoResponse> response = call.execute();
            ComerciosListadoResponse comercios = response.body();
            int code = comercios == null ? FAIL_CODE : comercios.getCode();
            return new TaskResponse<>(true, code, comercios);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return new TaskResponse<>(false, FAIL_CODE, null);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPreExecute.run();
    }


    @Override
    protected void onPostExecute(TaskResponse<ComerciosListadoResponse> listTaskResponse) {
        super.onPostExecute(listTaskResponse);
        postExecute.run(listTaskResponse);
    }
}
