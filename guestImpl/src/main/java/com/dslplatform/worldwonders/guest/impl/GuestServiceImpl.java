package com.dslplatform.worldwonders.guest.impl;

import com.dslplatform.worldwonders.guest.api.GuestService;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static java.util.concurrent.CompletableFuture.completedFuture;

public class GuestServiceImpl implements GuestService {
    @Override
    public ServiceCall<NotUsed, NotUsed, String> ping() {
        return (id, request) -> {
            System.out.println("ID: " + id);
            return completedFuture("Pong!");
        };
    }
}
