package cash.hx.hxjava.serializer;

import cash.hx.hxjava.config.Constants;
import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class StringSerializer implements ISerializer<String> {
    @Override
    public byte[] serialize(String instance) throws SerializeException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        SignedVarIntSerializer signedVarIntSerializer = SignedVarIntSerializer.defaultInstance();
        byte[] strBytes = instance.getBytes(Charset.forName("UTF-8"));
        try {
            bos.write((byte)(strBytes.length)); // TODO: length is varint
            bos.write(strBytes);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        }
    }

    @Override
    public String deserialize(byte[] bytes) throws DeserializeException {
        return null;
    }

    public static StringSerializer defaultInstance() {
        return new StringSerializer();
    }
}
