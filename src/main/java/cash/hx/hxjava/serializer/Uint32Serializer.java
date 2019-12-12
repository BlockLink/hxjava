package cash.hx.hxjava.serializer;

import cash.hx.hxjava.config.Constants;
import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Uint32Serializer implements ISerializer<Long> {
    private final boolean littleEndian;

    public Uint32Serializer(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public byte[] serialize(Long instance) throws SerializeException {
        long intValue = instance;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if(littleEndian) {
            bos.write((byte)intValue);
            bos.write((byte) (intValue >> 8));
            bos.write((byte) (intValue >> 16));
            bos.write((byte) (intValue >> 24));
        } else {
            bos.write((byte) (intValue >> 24));
            bos.write((byte) (intValue >> 16));
            bos.write((byte) (intValue >> 8));
            bos.write((byte) intValue);
        }
        return bos.toByteArray();
    }

    @Override
    public Long deserialize(byte[] bytes) throws DeserializeException {
        long value;
        if(littleEndian) {
            value = ((long) bytes[0]) | (((long)bytes[1]) << 8) | (((long)bytes[2]) << 16) | (((long) bytes[3]) << 24);
        } else {
            value = ((long) bytes[3]) | (((long) bytes[2]) << 8) | (((long)bytes[1]) << 16) | (((long) bytes[0]) << 24);
        }
        return value;
    }

    public static Uint32Serializer defaultInstance() {
        return new Uint32Serializer(Constants.littleEndian);
    }
}
