package tp1.g3.tdp2.hoycomo.Helpers;

import android.content.Context;
import com.google.gson.Gson;
import tp1.g3.tdp2.hoycomo.Modelos.Usuario;

public class MyDataUserHelper {

    private final Context context;

    public MyDataUserHelper(Context context) {
        this.context = context;
    }

    public void saveUser(Usuario user) {
        MyData myData = new MyData(context);
        //pref.save(context.getString(R.string.shared_pref_current_professional), "");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        myData.save("user", json);
    }

    public Usuario getUser() {
        MyData myData = new MyData(context);
        Gson gson = new Gson();
        String json = myData.get("user", "");
        Usuario user = gson.fromJson(json, Usuario.class);
        return user;
    }
}
