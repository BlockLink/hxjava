package cash.hx.hxjava.crypto;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;

public class CryptoUtil {
    public static byte[] ripemd160(byte[] input) {
        RIPEMD160Digest d = new RIPEMD160Digest();
        d.update(input, 0, input.length);
        byte[] o = new byte[d.getDigestSize()];
        d.doFinal(o, 0);
        return o;
    }

    public static byte[] sha512(byte[] input) {
        SHA512Digest d = new SHA512Digest();
        d.update(input, 0, input.length);
        byte[] o = new byte[d.getDigestSize()];
        d.doFinal(o, 0);
        return o;
    }

    public static byte[] bytesSlice(byte[] input, int index, int end) {
        if(index>=input.length) {
            return new byte[0];
        }
        if(end > input.length) {
            end = input.length;
        }
        byte[] result = new byte[end-index];
        for(int i=0;i<result.length;i++) {
            result[i] = input[index + i];
        }
        return result;
    }

    public static boolean deepEqualsOfBytes(byte[] a, byte[] b) {
        if(a.length!=b.length) {
            return false;
        }
        for(int i=0;i<a.length;i++) {
            if(a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }
}
