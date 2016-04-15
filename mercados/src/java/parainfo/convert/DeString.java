package parainfo.convert;

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
                Integer x = aInteger(ix);

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
}

