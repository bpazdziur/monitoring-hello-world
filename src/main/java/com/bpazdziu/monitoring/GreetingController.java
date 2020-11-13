package com.bpazdziu.monitoring;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class GreetingController {


    private final MeterRegistry metricsRegistry;


    @Autowired
    public GreetingController(final MeterRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;
    }

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
        metricsRegistry.counter("greetings_request", "whom", name).increment();
        return new Greeting(counter.incrementAndGet(), String.format(template, name));

    }
}
