package cash.hx.hxjava.serializer;

import cash.hx.hxjava.asset.Asset;
import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;
import cash.hx.hxjava.utils.IdUtil;

public class AssetSerializer implements ISerializer<Asset> {
    @Override
    public byte[] serialize(Asset instance) throws SerializeException {
        Int64Serializer int64Serializer = Int64Serializer.defaultInstance();
        byte[] amountBytes = int64Serializer.serialize(instance.getAmount());
        int assetIdValue = IdUtil.getId(instance.getAssetId());
        UnsignedVarIntSerializer unsignedVarIntSerializer = new UnsignedVarIntSerializer();
        byte[] assetIdBytes = unsignedVarIntSerializer.serialize(assetIdValue);
        return CryptoUtil.bytesMerge(amountBytes, assetIdBytes);
    }

    @Override
    public Asset deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
