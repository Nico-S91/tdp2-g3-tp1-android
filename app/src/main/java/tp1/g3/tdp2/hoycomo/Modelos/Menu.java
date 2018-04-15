package tp1.g3.tdp2.hoycomo.Modelos;


import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private List<Plato> mPlatos;
    private List<Promocion> mPromociones;

    public List<Plato> getPlatos() {
        return mPlatos;
    }
    public List<Promocion> getPromociones() {
        return mPromociones;
    }
    public void setPromociones(List<Promocion> promociones) {
        mPromociones = promociones;
    }
    public void setPlatos(List<Plato> platos) {
        mPlatos = platos;
    }
}
