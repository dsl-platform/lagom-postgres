package com.mentatlabs.lagombeer.foo.impl;

import com.mentatlabs.lagombeer.foo.api.FooService;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class FooServiceImpl implements FooService {
    @Override
    public ServiceCall<String, NotUsed, String> foo() {
        return (message, request) -> {
            final String response = new StringBuilder(message).reverse().toString();
            return completedFuture(response);
        };
    }
}
