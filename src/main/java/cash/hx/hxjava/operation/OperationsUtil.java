package cash.hx.hxjava.operation;

import cash.hx.hxjava.asset.AssetUtil;

import java.util.ArrayList;

public class OperationsUtil {
    public static TransferOperation defaultTransferOperation() {
        TransferOperation op = new TransferOperation();
        op.setFee(AssetUtil.defaultAsset());
        op.setGuaranteeId(null);
        op.setFrom("1.2.0");
        op.setTo("1.2.0");
        op.setFromAddr("");
        op.setToAddr("");
        op.setAmount(AssetUtil.defaultAsset());
        op.setMemo(null);
        op.setExtensions(new ArrayList<>());
        return op;
    }
}
