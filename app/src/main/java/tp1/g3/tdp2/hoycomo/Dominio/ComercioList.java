package tp1.g3.tdp2.hoycomo.Dominio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tp1.g3.tdp2.hoycomo.Exceptions.BusinessException;
import tp1.g3.tdp2.hoycomo.Modelos.Comercio;

public class ComercioList {

    private List<Comercio> comercios;

    public ComercioList() {
        this.comercios = new ArrayList<>();
    }

    public List<Comercio> getComercios() {
        return comercios;
    }

    public static ComercioList fromJson(JSONObject json) {
        ComercioList comercioList = null;
        try {
            comercioList = new ComercioList();
            JSONArray comerciosArray = json.getJSONArray("comercios");
            for (int i = 0; i < comerciosArray.length(); i++) {
                comercioList.getComercios().add(Comercio.fromShortJson(comerciosArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            throw new BusinessException("Error al intentar traer a los comercios.", e);
        }
        return comercioList;
    }
}
