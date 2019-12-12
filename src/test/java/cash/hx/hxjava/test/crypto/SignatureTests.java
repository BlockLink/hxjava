package cash.hx.hxjava.test.crypto;

import cash.hx.hxjava.address.Address;
import cash.hx.hxjava.builder.TransactionBuilder;
import cash.hx.hxjava.config.Constants;
import cash.hx.hxjava.exceptions.CryptoException;
import cash.hx.hxjava.exceptions.TransactionException;
import cash.hx.hxjava.utils.Numeric;
import org.bitcoin.Secp256k1Context;
import org.bitcoinj.core.ECKey;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class SignatureTests {
    private static final Logger log = LoggerFactory.getLogger(SignatureTests.class);

    @Test
    public void testGeneratePrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String privateKeyHex = Numeric.toHexStringNoPrefix(keyPair.getPrivate().getEncoded());
        log.info("generated privateKeyHex: {}", privateKeyHex);
    }

    @Test
    public void testTransfer() throws TransactionException {
        String refInfo = "30375,575365464";
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String fromAddr = "";
        String toAddr = "";
        BigDecimal amount = new BigDecimal("0.001");
        BigDecimal fee = new BigDecimal("0.0011");
        String memo = "test";
        String txJson = TransactionBuilder.transfer(Address.ADDRESS_PREFIX, refInfo, wifStr, chainId, fromAddr, toAddr, amount, "1.3.0", 5, fee, memo, null);
        log.info("signed tx: {}", txJson);
    }
}
