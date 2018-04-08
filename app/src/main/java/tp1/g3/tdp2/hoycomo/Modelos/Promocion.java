package tp1.g3.tdp2.hoycomo.Modelos;

import java.io.Serializable;
import java.util.List;

public class Promocion  implements Serializable {

    private Integer mId;
    private String mNombre;
    private List<Plato> mPlatos;
    private Float mPrecio;

    public Integer getId() {
        return mId;
    }
    public void setId(Integer id) {
        mId = id;
    }
    public List<Plato> getPlatos() {
        return mPlatos;
    }
    public void setPlatos(List<Plato> platos) {
        mPlatos = platos;
    }
    public String getNombre() {
        return mNombre;
    }
    public void setNombre(String nombre) {
        mNombre = nombre;
    }
    public float getPrecio() {
        return mPrecio;
    }
    public void setPrecio(float precio) {
        mPrecio = precio;
    }
}
