package parainfo.aes;

public class Prueba {

    public static void main(String[] arguments) {
        String text = "12345";

        AdvEncSta aes = new AdvEncSta();

        String encriptado = aes.encrypt(text);
        String descriptado = aes.decrypt(encriptado);

        System.out.println(encriptado);
        System.out.println(descriptado);
    }
}
