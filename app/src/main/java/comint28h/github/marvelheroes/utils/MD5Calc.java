package comint28h.github.marvelheroes.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Calc {
    private String hash;

    public MD5Calc(String forMD5Calc){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Все очень плохо и хэш не вычисляется.");
        }
        md.reset();
        md.update(forMD5Calc.getBytes());
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hash = bigInt.toString(16);
        while(hash.length() < 32){
            hash = "0" + hash;
        }
        this.hash = hash;
    }

    public String getHash(){
        return this.hash;
    }
}
