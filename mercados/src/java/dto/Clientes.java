package dto;

import java.sql.Date;

/**
 *
 * @author Jose
 */
public class Clientes {

    private Integer idcliente; //Serial NOT NULL
    private Integer idvendedor; //Integer NOT NULL
    private String nombre; //Character varying(150) NOT NULL
    private Integer idmercado; //Integer NOT NULL
    private String direccion; //Character varying(255)
    private Integer telefono; //Integer DEFAULT 9
    private Short idestado; //Smallint DEFAULT 1 NOT NULL
    private Date fechaingreso; //Date
    private Date fechacese; //Date

    public Clientes() {
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
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

    public Integer getIdmercado() {
        return idmercado;
    }

    public void setIdmercado(Integer idmercado) {
        this.idmercado = idmercado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Short getIdestado() {
        return idestado;
    }

    public void setIdestado(Short idestado) {
        this.idestado = idestado;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getFechacese() {
        return fechacese;
    }

    public void setFechacese(Date fechacese) {
        this.fechacese = fechacese;
    }

}
