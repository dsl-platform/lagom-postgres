package com.mentatlabs.lagombeer.foo.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface FooService extends Service {
  ServiceCall<String, NotUsed, String> foo();

  @Override
  default Descriptor descriptor() {
    return named("fooservice").with(
        restCall(Method.GET, "/foo/:message", foo())
      ).withAutoAcl(true);
  }
}
