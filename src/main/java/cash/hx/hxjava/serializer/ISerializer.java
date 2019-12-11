package cash.hx.hxjava.serializer;

import cash.hx.hxjava.exceptions.DeserializeException;
import cash.hx.hxjava.exceptions.SerializeException;

public interface ISerializer<T> {
    byte[] serialize(T instance) throws SerializeException;

    T deserialize(byte[] bytes) throws DeserializeException;
}
