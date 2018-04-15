package tp1.g3.tdp2.hoycomo.Respuestas;


import java.util.Arrays;

import tp1.g3.tdp2.hoycomo.Modelos.Comercio;

public class ComerciosListadoResponse {
    private int code;
    private Comercio[] comercios;

    public ComerciosListadoResponse(int code, Comercio[] comercios) {
        this.code = code;
        this.comercios = comercios;
    }

    public int getCode() {
        return code;
    }

    public Comercio[] getComercio() {
        return comercios;
    }

    @Override
    public String toString() {
        return "ComerciosListadoResponse{" +
                "code=" + code +
                ", comercio=" + Arrays.toString(comercios) +
                '}';
    }
}
