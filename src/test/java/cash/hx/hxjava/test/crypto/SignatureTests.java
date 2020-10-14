package cash.hx.hxjava.test.crypto;

import cash.hx.hxjava.address.Address;
import cash.hx.hxjava.address.AddressVersion;
import cash.hx.hxjava.address.PrivateKeyGenerator;
import cash.hx.hxjava.builder.TransactionBuilder;
import cash.hx.hxjava.client.NodeClient;
import cash.hx.hxjava.config.Constants;
import cash.hx.hxjava.exceptions.CryptoException;
import cash.hx.hxjava.exceptions.TransactionException;
import cash.hx.hxjava.operation.NodeException;
import cash.hx.hxjava.transaction.Transaction;
import cash.hx.hxjava.utils.Numeric;
import cash.hx.hxjava.utils.PrivateKeyUtil;
import org.bitcoinj.core.ECKey;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class SignatureTests {
    private static final Logger log = LoggerFactory.getLogger(SignatureTests.class);

    @Test
    public void testGeneratePrivateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        String privateKeyHex = Numeric.toHexStringNoPrefix(keyPair.getPrivate().getEncoded());
        log.info("generated privateKeyHex: {}", privateKeyHex);
    }

    private String getRefInfo() throws NodeException {
        String nodeRpcEndpoint = "ws://nodeapi2.hxlab.org:6090";
        NodeClient nodeClient = new NodeClient(nodeRpcEndpoint);
        nodeClient.open();
        nodeClient.sendLogin();
        return nodeClient.getRefInfo();
    }

    @Test
    public void testContractTransfer() throws TransactionException, NodeException {
        String refInfo = getRefInfo();
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String callerAddr = "";
        String callerPubKey = "";
        String contractId = "HXCHcRE3jsyHGrtoE2ZJZtpHsEiYTQ7VrHkb";
        BigDecimal transferAmount = new BigDecimal("0.001");
        String transferMemo = "hi";

        long gasLimit = 10000;
        long gasPrice = 1;

        BigDecimal fee = new BigDecimal("0.003");

        Transaction tx = TransactionBuilder.createContractTransferTransaction(refInfo, callerAddr, callerPubKey,
                contractId, transferAmount, "1.3.0", Constants.hxPrecision, transferMemo, fee, gasLimit, gasPrice, null);
        String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
        log.info("signed tx: {}", txJson);
    }

    @Test
    public void testContractInvoke() throws TransactionException, NodeException {
        String refInfo = getRefInfo();
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String callerAddr = "";
        String callerPubKey = "";
        String contractId = "HXCHcRE3jsyHGrtoE2ZJZtpHsEiYTQ7VrHkb";
        String contractApi = "balanceOf";
        String contractArg = "";

        long gasLimit = 10000;
        long gasPrice = 1;

        BigDecimal fee = new BigDecimal("0.003");

        Transaction tx = TransactionBuilder.createContractInvokeTransaction(refInfo, callerAddr, callerPubKey,
                contractId, contractApi, contractArg, fee, gasLimit, gasPrice, null);
        String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
        log.info("signed tx: {}", txJson);
    }

    @Test
    public void testTransfer() throws TransactionException, NodeException {
        String refInfo = getRefInfo();
        String chainId = Constants.mainnetChainId;
        String wifStr = "";
        String fromAddr = "";
        String toAddr = "";
        BigDecimal amount = new BigDecimal("0.001");
        BigDecimal fee = new BigDecimal("0.0011");
        String memo = "test";
        Transaction tx = TransactionBuilder.createTransferTransaction(refInfo, fromAddr, toAddr, amount, "1.3.0", 5, fee, memo, null);
        String txJson = TransactionBuilder.signTransaction(tx, wifStr, chainId, Address.ADDRESS_PREFIX);
        log.info("signed tx: {}", txJson);
    }

    @Test
    public void testGeneratePrivateKeyAndAddress() throws CryptoException {
        ECKey ecKey = PrivateKeyGenerator.generate();
        String privateKeyHex = ecKey.getPrivateKeyAsHex();
        String wif = PrivateKeyUtil.privateKeyToWif(ecKey);
        log.info("privateKeyHex: {}", privateKeyHex);
        log.info("privateKey wif: {}", wif);
        Address address = Address.fromPubKey(ecKey.getPubKey(), AddressVersion.NORMAL);
        log.info("address: {}", address);
    }
}
