package dto;

/**
 *
 * @author Jose
 */
public class Vendedores {

    private Integer idvendedor; //Serial NOT NULL,
    private String nombre; //Character varying(150) NOT NULL,
    private Short idestado; //Smallint NOT NULL

    public Vendedores() {
    }

    public Integer getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getIdestado() {
        return idestado;
    }

    public void setIdestado(Short idestado) {
        this.idestado = idestado;
    }

}
