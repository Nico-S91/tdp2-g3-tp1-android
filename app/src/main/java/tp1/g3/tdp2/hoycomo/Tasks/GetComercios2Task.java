package tp1.g3.tdp2.hoycomo.Tasks;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tp1.g3.tdp2.hoycomo.Activdades.ComerciosListado;
import tp1.g3.tdp2.hoycomo.Dominio.ComercioList;
import tp1.g3.tdp2.hoycomo.Exceptions.BusinessException;
import tp1.g3.tdp2.hoycomo.Exceptions.ServerErrorException;
import tp1.g3.tdp2.hoycomo.Helpers.ToastMessage;


public class GetComercios2Task extends AbstractTask<String,Void,ComercioList,ComerciosListado> {

    public GetComercios2Task(ComerciosListado activity) {
        super(activity);
    }

    @Override
    protected ComercioList doInBackground(String... params) {
        Context context = weakReference.get().getApplicationContext();
        String uri = params[0];

        ComercioList comercioList = null;
        try {
            comercioList = (ComercioList) apiRestConsumer.get(uri);
        } catch (BusinessException e) {
            //ToastMessage.toastMessage(context, "Error al traer los Albums");
        } catch (final ServerErrorException e) {
            weakReference.get().runOnUiThread(() -> ToastMessage.toastMessage(weakReference.get().getApplicationContext(), e.getMessage()));
        } catch (Exception e) {
            //ToastMessage.toastMessage(context, e.getMessage());
        }
        return comercioList;
    }

    @Override
    public Object readResponse(String json) throws JSONException {
        Log.d("ComerciosList json", json);
//        if (json.length() > 10) {
            JSONObject jsonComerciosList = new JSONObject(json);
/*            if (json.contains("duration")){
                return TrackList.fromJson(jsonAlbumList);
            }else {*/
                return ComercioList.fromJson(jsonComerciosList);
            //}
 /*       }else{
            return new TrackList();
        }*/
    }

    @Override
    protected void onPostExecute(ComercioList albumList) {
        super.onPostExecute(albumList);
        weakReference.get().onGetComerciosSuccess(albumList);
    }
}
