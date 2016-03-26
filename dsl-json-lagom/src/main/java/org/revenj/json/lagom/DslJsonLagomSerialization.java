package org.revenj.json.lagom;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.revenj.json.DslJsonSerialization;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.deser.MessageSerializer;

import akka.NotUsed;

public class DslJsonLagomSerialization {
    public static <T extends com.lightbend.lagom.javadsl.api.Service> PMap<Type, MessageSerializer<?,?>> optimizeSerializersFor(final Class<T> service) {
        PMap<Type, MessageSerializer<?,?>> result = HashTreePMap.empty();
        final DslJsonSerialization serialization = new DslJsonSerialization(null, Optional.empty());

        final Method[] serviceMethods = service.getMethods();
        for (final Method serviceMethod : serviceMethods) {
            if (serviceMethod.getReturnType() != ServiceCall.class) continue;
            final ParameterizedType genericReturnType = (ParameterizedType) serviceMethod.getGenericReturnType();
            final Type[] arguments = genericReturnType.getActualTypeArguments();

            for (int i = 1; i <= 2; i ++) {
                final Type type = arguments[i];
                if (type == NotUsed.class) continue;

                if (result.containsKey(type)) continue;
                result = result.plus(type, new DslJsonMessageSerializer<>(type, serialization));
            }
        }
        return result;
    }
}
