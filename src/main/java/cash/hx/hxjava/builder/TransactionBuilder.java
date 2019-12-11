package cash.hx.hxjava.builder;

import cash.hx.hxjava.exceptions.TransactionException;

public class TransactionBuilder {
    public static byte[] transfer(String ref, String wif, String chainId, String fromAddr, String toAddr,
                                  String coinType, String transferAmount, String fee, String memo, String guaranteeId)
            throws TransactionException {
        return new byte[]{}; // TODO
    }
}
