package parainfo.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * @author parainformaticos.com
 */
public class DeString {

    public DeString() {
    }

    public static Integer aInteger(String s) {
        Integer result = null;

        try {
            result = Integer.valueOf(s);
        } catch (NumberFormatException ex) {
        }

        return result;
    }

    public static Double aDouble(String s) {
        Double result = null;

        if (s != null) {
            try {
                result = Double.valueOf(s);
            } catch (NumberFormatException ex) {
            }
        }

        return result;
    }

    /**
     *
     * @param s
     * @return
     */
    public static Short aShort(String s) {
        Short result = null;

        try {
            result = Short.valueOf(s);
        } catch (NumberFormatException ex) {
        }

        return result;
    }

    /**
     * @param _ids cadena == "3,4,7,8,9"
     * @return lista de enteros
     */
    public static List<Integer> ids(String _ids) {
        List<Integer> list = null;

        if ((_ids != null) && (_ids.trim().length() > 0)) {
            String[] id = _ids.split(",");

            list = new LinkedList<>();
            for (String ix : id) {
                Integer x = aInteger(ix.trim());

                if (x != null) {
                    list.add(x);
                } else {
                    list = null;
                    break;
                }
            }
        }

        return list;
    }

    /**
     * @param fecha como cadena
     * @return java.sql.Date
     */
    public static Date aDate(String fecha) {
        Date result = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //dd/MM/yyyy
        sdf.setLenient(false);

        try {
            java.util.Date utilDate = sdf.parse(fecha);
            result = new java.sql.Date(utilDate.getTime());

        } catch (ParseException ex) {
        }

        return result;
    }

    /**
     * @param hora como cadena
     * @return java.sql.Time
     */
    public static Time aTime(String hora) {
        Time result = null;

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        sdf.setLenient(false);

        try {
            java.util.Date uhora = sdf.parse(hora);
            result = new java.sql.Time(uhora.getTime());

        } catch (ParseException ex) {
        }

        return result;
    }

    /**
     * @param fechahora como cadena
     * @return java.sql.Timestamp
     */
    public static Timestamp aTimestamp(String fechahora) {
        Timestamp result = null;

        SimpleDateFormat sdf
                = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        sdf.setLenient(false);

        try {
            java.util.Date ufechahora = sdf.parse(fechahora);
            result = new java.sql.Timestamp(ufechahora.getTime());

        } catch (ParseException ex) {
        }

        return result;
    }
}
