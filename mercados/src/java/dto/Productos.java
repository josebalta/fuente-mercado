package dto;

/**
 *
 * @author Jose
 */
public class Productos {

    private Integer idproducto; //Serial NOT NULL
    private String descripcion; //Character varying(50) NOT NULL
    private Double preciounitario; //Numeric(10,2) NOT NULL
    private Short idestado; //Smallint DEFAULT 1 NOT NULL

    public Productos() {
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public Short getIdestado() {
        return idestado;
    }

    public void setIdestado(Short idestado) {
        this.idestado = idestado;
    }
}
