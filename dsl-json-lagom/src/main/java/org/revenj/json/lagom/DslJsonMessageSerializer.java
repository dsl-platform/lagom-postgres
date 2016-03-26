package org.revenj.json.lagom;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import org.revenj.json.DslJsonSerialization;

import com.lightbend.lagom.javadsl.api.deser.DeserializationException;
import com.lightbend.lagom.javadsl.api.deser.SerializationException;
import com.lightbend.lagom.javadsl.api.deser.StrictMessageSerializer;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;

import akka.util.ByteString;

public class DslJsonMessageSerializer<MessageEntity> implements StrictMessageSerializer<MessageEntity> {
    private final NegotiatedSerializer<MessageEntity, ByteString> serializer;
    private final NegotiatedDeserializer<MessageEntity, ByteString> deserializer;

    public DslJsonMessageSerializer(Type type, DslJsonSerialization serialization) {
        serializer = new DslJsonSerializer(serialization);
        deserializer = new DslJsonDeserializer(type, serialization);
    }

    @Override
    public PSequence<MessageProtocol> acceptResponseProtocols() {
        return TreePVector.singleton(new MessageProtocol(Optional.of("application/json"), Optional.empty(), Optional.empty()));
    }

    @Override
    public NegotiatedSerializer<MessageEntity, ByteString> serializerForRequest() {
        return serializer;
    }

    @Override
    public NegotiatedDeserializer<MessageEntity, ByteString> deserializer(MessageProtocol messageProtocol) throws SerializationException {
        return deserializer;
    }

    @Override
    public NegotiatedSerializer<MessageEntity, ByteString> serializerForResponse(List<MessageProtocol> acceptedMessageProtocols) {
        return serializer;
    }

    private class DslJsonSerializer implements NegotiatedSerializer<MessageEntity, ByteString> {
        private final DslJsonSerialization serialization;

        @Inject
        public DslJsonSerializer(DslJsonSerialization serialization) {
            this.serialization = serialization;
        }

        @Override
        public MessageProtocol protocol() {
            return new MessageProtocol(Optional.of("application/json"), Optional.of("utf-8"), Optional.empty());
        }

        @Override
        public ByteString serialize(final MessageEntity messageEntity) {
            try {
                final String serialized = serialization.serialize(messageEntity);
                return ByteString.fromString(serialized, "UTF-8");
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }
    }

    private class DslJsonDeserializer implements NegotiatedDeserializer<MessageEntity, ByteString> {
        private final Type type;
        private final DslJsonSerialization serialization;

        public DslJsonDeserializer(final Type type, final DslJsonSerialization serialization) {
            this.type = type;
            this.serialization = serialization;
        }

        @Override
        @SuppressWarnings("unchecked")
        public MessageEntity deserialize(final ByteString bytes) {
            try {
                return (MessageEntity) serialization.deserialize(type, bytes.decodeString("UTF-8"));
            } catch (Exception e) {
                throw new DeserializationException(e);
            }
        }
    }
}
