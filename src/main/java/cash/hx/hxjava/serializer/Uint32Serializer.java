package cash.hx.hxjava.serializer;

import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Uint32Serializer implements ISerializer<Integer> {
    private final boolean littleEndian;

    public Uint32Serializer(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    @Override
    public byte[] serialize(Integer instance) throws SerializeException {
        int intValue = instance;
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
    public Integer deserialize(byte[] bytes) throws DeserializeException {
        int value;
        if(littleEndian) {
            value = ((int) bytes[0]) | (((int)bytes[1]) << 8) | (((int)bytes[2]) << 16) | (((int) bytes[3]) << 24);
        } else {
            value = ((int) bytes[3]) | (((int) bytes[2]) << 8) | (((int)bytes[1]) << 16) | (((int) bytes[0]) << 24);
        }
        return value;
    }

    public static Uint32Serializer defaultInstance() {
        return new Uint32Serializer(EndianConstants.littleEndian);
    }
}
