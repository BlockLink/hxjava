package cash.hx.hxjava.pubkey;

import cash.hx.hxjava.address.Address;
import cash.hx.hxjava.crypto.Base58;
import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.crypto.exceptions.Base58DecodeException;
import cash.hx.hxjava.exceptions.PubKeyInvalidException;

public class PubKeyUtil {
    public static final String PUBKEY_STRING_PREFIX = Address.ADDRESS_PREFIX;

    public static byte[] getPubKeyBytes(String pubKeyStr) throws PubKeyInvalidException {
        if (pubKeyStr == null || pubKeyStr.length() <= PUBKEY_STRING_PREFIX.length()) {
            throw new PubKeyInvalidException("invalid pubKeyStr prefix");
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
}
