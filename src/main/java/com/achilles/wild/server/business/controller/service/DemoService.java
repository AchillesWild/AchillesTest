package com.achilles.wild.server.business.controller.service;

import java.util.concurrent.Future;

public interface DemoService {


    void doIt (String name);

    Future<String> asyncReturnFuture(String name);
}
