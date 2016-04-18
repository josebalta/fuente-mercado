/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parainfo.convert;

/**
 *
 * @author Jose
 */
public class DeDateJava {

    public DeDateJava() {
    }
    
    public static java.sql.Date aDateSql(java.util.Date fecha) {

        fecha = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        return sqlDate;       
        
    }
}
