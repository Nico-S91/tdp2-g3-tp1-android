package tp1.g3.tdp2.hoycomo.Modelos;

import java.io.Serializable;
import java.util.List;

public class Plato  implements Serializable {
    private Integer mId;
    private String mNombre;
    private List<Guarnicion> mGuarniciones;
    private Float mPrecio;

    public Integer getId() {
        return mId;
    }
    public void setId(Integer id) {
        mId = id;
    }
    public List<Guarnicion> getGuarniciones() {
        return mGuarniciones;
    }
    public void setGuarnicions(List<Guarnicion> guarnicions) {
        mGuarniciones = guarnicions;
    }
    public String getNombre() {
        return mNombre;
    }
    public void setNombre(String nombre) {
        mNombre = nombre;
    }
    public Float getPrecio() {
        return mPrecio;
    }
    public void setPrecio(Float precio) {
        mPrecio = precio;
    }
}
