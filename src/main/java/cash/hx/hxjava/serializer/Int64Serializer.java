package cash.hx.hxjava.serializer;

import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;

import java.io.ByteArrayOutputStream;

public class Int64Serializer implements ISerializer<Long> {
    private final boolean littleEndian;

    public Int64Serializer(boolean littleEndian) {
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
            bos.write((byte) (intValue >> 32));
            bos.write((byte) (intValue >> 40));
            bos.write((byte) (intValue >> 48));
            bos.write((byte) (intValue >> 56));
        } else {
            bos.write((byte) (intValue >> 56));
            bos.write((byte) (intValue >> 48));
            bos.write((byte) (intValue >> 40));
            bos.write((byte) (intValue >> 32));
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
            value = ((long) bytes[0]) | (((long)bytes[1]) << 8) | (((long)bytes[2]) << 16) | (((long) bytes[3]) << 24)
            | (((long) bytes[4]) << 32) | (((long) bytes[5]) << 40) | (((long) bytes[6]) << 48) | (((long) bytes[7]) << 56);
        } else {
            value =((long) bytes[7]) | (((long)bytes[6]) << 8) | (((long)bytes[5]) << 16) | (((long) bytes[4]) << 24)
                    | (((long) bytes[3]) << 32) | (((long) bytes[2]) << 40) | (((long) bytes[1]) << 48) | (((long) bytes[0]) << 56);
        }
        return value;
    }

    public static Int64Serializer defaultInstance() {
        return new Int64Serializer(EndianConstants.littleEndian);
    }
}
