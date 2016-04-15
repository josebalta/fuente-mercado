package parainfo.aes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AdvEncSta {

    private final String KEY = "aPb4x9q0H4W8rPs7";

    public AdvEncSta() {
    }

    private Cipher getCipher(int cipherMode) {
        Cipher cipher = null;

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    KEY.getBytes("UTF-8"), "AES");

            cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKeySpec);

        } catch (UnsupportedEncodingException 
                | NoSuchAlgorithmException 
                | NoSuchPaddingException 
                | InvalidKeyException ex) {
        }

        return cipher;
    }

    public String encrypt(String text) {
        String result = null;

        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());

            result = Base64.encodeBase64String(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
        }

        return result;
    }

    public String decrypt(String encrypted) {
        String result = null;

        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] plainBytes
                    = cipher.doFinal(Base64.decodeBase64(encrypted));

            result = new String(plainBytes);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
        }

        return result;
    }
}

