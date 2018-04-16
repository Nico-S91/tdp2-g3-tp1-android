package tp1.g3.tdp2.hoycomo.Modelos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import tp1.g3.tdp2.hoycomo.Exceptions.BusinessException;

public class Comercio implements Serializable {
    private Integer mId;
    private String mNombre;
    private String mDireccion;
    private Integer mTipo;
    private String mImagen;
    private Menu mMenu;

    public Integer getId() {
        return mId;
    }
    public Integer getTipo() {
        return mTipo;
    }
    public String getNombre() {
        return mNombre;
    }
    public String getDireccion() {
        return mDireccion;
    }
    public String getImagen() {
        return mImagen;
    }
    public Menu getMenu() {
        return mMenu;
    }
    public void setId(Integer id) {
        mId = id;
    }
    public void setTipo(Integer tipo) {
        mTipo = tipo;
    }
    public void setNombre(String nombre) {
        mNombre = nombre;
    }
    public void setDireccion(String direccion) {
        mDireccion = direccion;
    }
    public void setImagen(String imagen) {
        mImagen = imagen;
    }
    public void setMenu(Menu menu) {
        mMenu = menu;
    }

    public static Comercio fromShortJson(JSONObject json) {
        Comercio comercio = null;
        try {
            comercio = new Comercio();
            comercio.setNombre(json.getString("name"));
            comercio.setDireccion(json.getString("address"));
            comercio.setId(json.getInt("id"));
//            comercio.setImagen(json.getString("Imagen"));
        } catch (JSONException e) {
            throw new BusinessException("Error al traer el comercio", e);
        }
        return comercio;
    }
}
