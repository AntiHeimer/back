package capstone.Antiheimer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AesService {

    public static String algorithms = "AES/CBC/PKCS5Padding";
    private final static String AESKey = "abcdefghabcdefghabcdefghabcdefgh";
    private final static String AESIv = "0123456789abcdef";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String encryptAES(String ID) {
        try {

            Cipher cipher = Cipher.getInstance(algorithms);
            SecretKeySpec keySpec = new SecretKeySpec(AESKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(ID.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("암호화 중 오류", e);
        }
    }

    public String decryptAES(String ID) {
        try {
            Cipher cipher = Cipher.getInstance(algorithms);
            SecretKeySpec keySpec = new SecretKeySpec(AESKey.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(ID);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("복호화 중 오류", e);
        }
    }
    /*public String encryptAES(String ID) {

        try {
            String result;

            Cipher cipher = Cipher.getInstance(algorithms);

            SecretKeySpec keySpec = new SecretKeySpec(AESKey.getBytes(), "AES");

            IvParameterSpec ivParamSpec = new IvParameterSpec(AESIv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(ID.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(encrypted);
            System.out.println("result = " + result);

            return result;
        } catch (Exception e) {

            System.out.println("암호화 중 오류 발생");
            e.printStackTrace();
        }
        return "";
    }

    public String decryptAES(String ID) {

        try {
            Cipher cipher = Cipher.getInstance(algorithms);

            SecretKeySpec keySpec = new SecretKeySpec(AESKey.getBytes(), "AES");

            IvParameterSpec ivParamSpec = new IvParameterSpec(AESIv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodeBytes = Base64.getDecoder().decode(ID);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            System.out.println("decrypted = " + new String(decrypted, StandardCharsets.UTF_8));

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e){

            System.out.println("복호화 중 오류 발생");
            e.printStackTrace();
        }

        return "";
    }*/
}