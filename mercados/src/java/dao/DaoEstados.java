/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author Administrador
 */
public interface DaoEstados {

    /**
     *
     * @return
     */
    public List<Object[]> estadosCbo();

    public String getMessage();

}
