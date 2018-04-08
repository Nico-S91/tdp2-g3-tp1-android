package tp1.g3.tdp2.hoycomo.Modelos;

import java.io.Serializable;

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
}
