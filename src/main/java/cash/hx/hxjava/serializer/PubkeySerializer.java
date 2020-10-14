package cash.hx.hxjava.serializer;

import cash.hx.hxjava.crypto.CryptoUtil;
import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;
import cash.hx.hxjava.pubkey.PubKeyBytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PubkeySerializer implements ISerializer<PubKeyBytes> {
    @Override
    public byte[] serialize(PubKeyBytes instance) throws SerializeException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(instance.getData());
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public PubKeyBytes deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }
}
