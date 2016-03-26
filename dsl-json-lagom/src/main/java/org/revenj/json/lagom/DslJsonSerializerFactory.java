package org.revenj.json.lagom;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import org.revenj.json.DslJsonSerialization;

import com.lightbend.lagom.javadsl.api.deser.SerializationException;
import com.lightbend.lagom.javadsl.api.deser.SerializerFactory;
import com.lightbend.lagom.javadsl.api.deser.StrictMessageSerializer;
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol;

import akka.Done;
import akka.util.ByteString;

public class DslJsonSerializerFactory implements SerializerFactory {
    private final DslJsonSerialization serialization;

    @Inject
    public DslJsonSerializerFactory(final DslJsonSerialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public <MessageEntity> StrictMessageSerializer<MessageEntity> messageSerializerFor(final Type type) {
        return type == Done.class
                ? new DoneMessageSerializer<>()
                : new DslJsonMessageSerializer<>(type, serialization);
    }

    private class DoneMessageSerializer<MessageEntity> implements StrictMessageSerializer<MessageEntity> {
        private final NegotiatedSerializer<MessageEntity, ByteString> serializer = new DoneSerializer();
        private final NegotiatedDeserializer<MessageEntity, ByteString> deserializer = new DoneDeserializer();

        @Override
        public PSequence<MessageProtocol> acceptResponseProtocols() {
            return TreePVector.singleton(new MessageProtocol(Optional.of("application/json"), Optional.empty(), Optional.empty()));
        }

        @Override
        public NegotiatedSerializer<MessageEntity, ByteString> serializerForRequest() {
            return serializer;
        }

        @Override
        public NegotiatedDeserializer<MessageEntity, ByteString> deserializer(final MessageProtocol messageProtocol) throws SerializationException {
            return deserializer;
        }

        @Override
        public NegotiatedSerializer<MessageEntity, ByteString> serializerForResponse(final List<MessageProtocol> acceptedMessageProtocols) {
            return serializer;
        }

        private class DoneSerializer implements NegotiatedSerializer<MessageEntity, ByteString> {
            private final ByteString doneJson = ByteString.fromString("{\"done\":true}", "UTF-8");

            @Override
            public MessageProtocol protocol() {
                return new MessageProtocol(Optional.of("application/json"), Optional.of("utf-8"), Optional.empty());
            }

            @Override
            public ByteString serialize(MessageEntity obj) {
                return doneJson;
            }
        }

        private class DoneDeserializer implements NegotiatedDeserializer<MessageEntity, ByteString> {
            @SuppressWarnings("unchecked")
            @Override
            public MessageEntity deserialize(ByteString bytes) {
                return (MessageEntity) Done.getInstance();
            }
        }
    }
}
