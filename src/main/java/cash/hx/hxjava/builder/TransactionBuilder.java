package cash.hx.hxjava.builder;

import cash.hx.hxjava.asset.Asset;
import cash.hx.hxjava.asset.AssetUtil;
import cash.hx.hxjava.config.Constants;
import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.exceptions.CryptoException;
import cash.hx.hxjava.exceptions.SerializeException;
import cash.hx.hxjava.exceptions.TransactionException;
import cash.hx.hxjava.operation.OperationsUtil;
import cash.hx.hxjava.operation.TransferOperation;
import cash.hx.hxjava.serializer.TransactionSerializer;
import cash.hx.hxjava.transaction.Memo;
import cash.hx.hxjava.transaction.MemoUtil;
import cash.hx.hxjava.transaction.RefBlockInfo;
import cash.hx.hxjava.transaction.Transaction;
import cash.hx.hxjava.utils.Numeric;
import cash.hx.hxjava.utils.SignatureUtil;
import cash.hx.hxjava.utils.StringUtil;
import com.alibaba.fastjson.JSON;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class TransactionBuilder {
    private static final Logger log = LoggerFactory.getLogger(TransactionBuilder.class);

    public static String transfer(String addressPrefix, String refInfo, String wifStr, String chainId, String fromAddr, String toAddr,
                                  BigDecimal transferAmount, String assetId, int assetPrecision, BigDecimal fee, String memo, String guaranteeId)
            throws TransactionException {
        long transferAmountFull = transferAmount.multiply(new BigDecimal(10).pow(assetPrecision)).longValue();
        long feeFull = fee.multiply(new BigDecimal(10).pow(Constants.hxPrecision)).longValue();

        Asset amountAsset = AssetUtil.defaultAsset();
        amountAsset.setAssetId(assetId);
        amountAsset.setAmount(transferAmountFull);

        Asset feeAsset = AssetUtil.defaultAsset();
        feeAsset.setAmount(feeFull);

        TransferOperation transferOperation = OperationsUtil.defaultTransferOperation();
        transferOperation.setFee(feeAsset);
        transferOperation.setAmount(amountAsset);
        transferOperation.setFromAddr(fromAddr);
        transferOperation.setToAddr(toAddr);

        if(StringUtil.isEmpty(memo)) {
            transferOperation.setMemo(null);
        } else {
            Memo memoObj = MemoUtil.defaultMemo();
            memoObj.setTransientMessage(memo);
            memoObj.setEmpty(false);
            byte[] zero4 = new byte[4];
            CryptoUtil.setBytesZero(zero4);
            byte[] memoBytes = CryptoUtil.bytesMerge(zero4, memo.getBytes());
            memoObj.setMessage(Numeric.toHexStringNoPrefix(memoBytes));
            transferOperation.setMemo(memoObj);
        }

        if(!StringUtil.isEmpty(guaranteeId)) {
            transferOperation.setGuaranteeId(guaranteeId);
        }

        long expireSec = (System.currentTimeMillis() / 1000) + Constants.expireTimeout;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String expireStr = sdf.format(new Date(expireSec * 1000)); // expir_str := "2018-09-26T09:14:40"

        RefBlockInfo refBlockInfo = RefBlockInfo.decodeFromInfoString(refInfo);
        int refBlockNum = refBlockInfo.getRefBlockNum();
        long refBlockPrefix = refBlockInfo.getRefBlockPrefix();
        Transaction tx = new Transaction();
        tx.setRefBlockNum(refBlockNum);
        tx.setRefBlockPrefix(refBlockPrefix);
        tx.setExpiration(expireStr);
        tx.setTransientExpiration(expireSec);
        tx.setOperations(Collections.singletonList(Arrays.asList(transferOperation.getOperationType(), transferOperation)));
        tx.setExtensions(new ArrayList<>());
        tx.setSignatures(new ArrayList<>());
        tx.setTransientOperations(Collections.singletonList(transferOperation));

        // TODO: 拆分成序列化和签名两个方法

        TransactionSerializer txSerializer = new TransactionSerializer(addressPrefix);
        try {
            byte[] txBytes = txSerializer.serialize(tx);
            log.debug("tx hex: {}", Numeric.toHexStringNoPrefix(txBytes));
            byte[] chainIdBytes = Numeric.hexStringToByteArray(chainId);
            byte[] toSignBytes = CryptoUtil.bytesMerge(chainIdBytes, txBytes);
            byte[] sig = SignatureUtil.getSignature(wifStr, toSignBytes);
            tx.setSignatures(Collections.singletonList(Numeric.toHexStringNoPrefix(sig)));
            String txJson = JSON.toJSONString(tx);
            return txJson;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TransactionException(e);
        }
    }
}