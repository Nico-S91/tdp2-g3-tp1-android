package tp1.g3.tdp2.hoycomo.Modelos;

import java.io.Serializable;

public class Guarnicion  implements Serializable {
    private Integer mId;
    private String mNombre;
    private String mImagen;

    public Integer getId() {
        return mId;
    }
    public void setId(Integer id) {
        mId = id;
    }
    public String getNombre() {
        return mNombre;
    }
    public void setNombre(String nombre) {
        mNombre = nombre;
    }
    public String getImagen() {
        return mImagen;
    }
    public void setImagen(String imagen) {
        mImagen = imagen;
    }
}