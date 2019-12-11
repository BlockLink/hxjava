package cash.hx.hxjava.serializer;

import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;
import cash.hx.hxjava.transaction.Memo;

public class MemoSerializer implements ISerializer<Memo> {
    @Override
    public byte[] serialize(Memo instance) throws SerializeException {
        if(instance == null) {
            return CryptoUtil.singleBytes((byte) 0);
        }
        byte[] one = CryptoUtil.singleBytes((byte) 1); // optional value have 1 as first byte when exists
        byte[] pubKeyBytes = new byte[74];
        CryptoUtil.setBytesZero(pubKeyBytes);
        byte[] messageStrBytes = instance.getMessage().getBytes();
        byte[] lengthBytes = CryptoUtil.singleBytes((byte) (messageStrBytes.length+4));
        byte[] checksumBytes = new byte[4];
        CryptoUtil.setBytesZero(checksumBytes);
        return CryptoUtil.bytesMerge(one, pubKeyBytes, lengthBytes, checksumBytes, messageStrBytes);
    }

    @Override
    public Memo deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
