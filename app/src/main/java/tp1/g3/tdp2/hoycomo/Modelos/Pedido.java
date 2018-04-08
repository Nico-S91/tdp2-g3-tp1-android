package tp1.g3.tdp2.hoycomo.Modelos;

import java.io.Serializable;
import java.util.List;

public class Pedido  implements Serializable {
    private Integer mId;
    private Usuario mUsuario;
    private List<Plato> mPlatos;
    private List<Promocion> mPromociones;
    public Integer getId() {
        return mId;
    }
    public void setId(Integer id) {
        mId = id;
    }
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
