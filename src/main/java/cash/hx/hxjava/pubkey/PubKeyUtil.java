package cash.hx.hxjava.pubkey;

import cash.hx.hxjava.address.Address;
import cash.hx.hxjava.crypto.Base58;
import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.crypto.exceptions.Base58DecodeException;
import cash.hx.hxjava.exceptions.PubKeyInvalidException;
import cash.hx.hxjava.utils.Numeric;

public class PubKeyUtil {
    public static final String PUBKEY_STRING_PREFIX = Address.ADDRESS_PREFIX;

    public static final int PUBKEY_BYTES_SIZE = 33;

    public static byte[] getPubKeyBytes(String pubKeyStr) throws PubKeyInvalidException {
        if (pubKeyStr == null || pubKeyStr.length() <= PUBKEY_STRING_PREFIX.length()) {
            throw new PubKeyInvalidException("invalid pubKeyStr prefix");
        }
        if(!pubKeyStr.startsWith(PUBKEY_STRING_PREFIX)) {
            // 非base58格式的公钥，尝试用hex格式解析
            try {
                return Numeric.hexStringToByteArray(pubKeyStr);
            } catch (Exception e) {
                throw new PubKeyInvalidException(e);
            }
        }
        String base58Addr = pubKeyStr.substring(PUBKEY_STRING_PREFIX.length());
        try {
            byte[] addrBytes = Base58.decode(base58Addr);
            byte[] addrBytesWithoutChecksum = CryptoUtil.bytesSlice(addrBytes, 0, addrBytes.length-4);
            return addrBytesWithoutChecksum;
        } catch (Base58DecodeException e) {
            throw new PubKeyInvalidException(e);
        }
    }

    public static String base58PubKeyToHex(String pubKeyBase58Str) throws PubKeyInvalidException {
        byte[] pubKeyBytes = getPubKeyBytes(pubKeyBase58Str);
        return Numeric.toHexStringNoPrefix(pubKeyBytes);
    }
}
