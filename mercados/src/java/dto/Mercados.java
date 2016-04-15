package dto;

/**
 *
 * @author Jose
 */
public class Mercados {

    private Integer idmercado; //Serial NOT NULL,
    private Integer iddistrito; //Integer NOT NULL,
    private String abreviado; //Character varying(4) NOT NULL,
    private String nombre; //Character varying(100) NOT NULL,
    private String direccion; //Character varying(200),
    private Short idestado; //Smallint DEFAULT 1 NOT NULL

    public Mercados() {
    }

    public Integer getIdmercado() {
        return idmercado;
    }

    public void setIdmercado(Integer idmercado) {
        this.idmercado = idmercado;
    }

    public Integer getIddistrito() {
        return iddistrito;
    }

    public void setIddistrito(Integer iddistrito) {
        this.iddistrito = iddistrito;
    }

    public String getAbreviado() {
        return abreviado;
    }

    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Short getIdestado() {
        return idestado;
    }

    public void setIdestado(Short idestado) {
        this.idestado = idestado;
    }

}
