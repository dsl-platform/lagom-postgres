package worldwonders.storage.api;

import java.util.List;

import com.lightbend.lagom.javadsl.api.deser.SerializationException;
import com.lightbend.lagom.javadsl.api.deser.StrictMessageSerializer;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;
import com.lightbend.lagom.javadsl.api.transport.NotAcceptable;
import com.lightbend.lagom.javadsl.api.transport.UnsupportedMediaType;

import akka.util.ByteString;

public class ByteArrayResponseSerializer implements StrictMessageSerializer<byte[]> {
    @Override
    public NegotiatedSerializer<byte[], ByteString> serializerForRequest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NegotiatedDeserializer<byte[], ByteString> deserializer(
            final MessageProtocol protocol) throws UnsupportedMediaType {
        throw new UnsupportedOperationException();
    }

    @Override
    public NegotiatedSerializer<byte[], ByteString> serializerForResponse(
            final List<MessageProtocol> acceptedMessageProtocols) throws NotAcceptable {
        return new NegotiatedSerializer<byte[], ByteString>() {
            @Override
            public ByteString serialize(final byte[] messageEntity) throws SerializationException {
                return ByteString.fromArray(messageEntity);
            }
        };
    }
}
