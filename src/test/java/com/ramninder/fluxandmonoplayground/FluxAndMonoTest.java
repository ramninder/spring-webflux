package com.ramninder.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){

       Flux<String> stringFlux  = Flux.just("Spring","Spring boot", "Reactive Spring")
//              .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
               .log();
        stringFlux.subscribe(System.out::println, System.err::println,()->System.out.println("Complete"));
    }

    @Test
    public void fluxTestElements_WithoutError(){
        Flux<String> stringFlux  = Flux.just("Spring","Spring boot", "Reactive Spring").log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

    }

    @Test
    public void fluxTestElements_WithError(){
        Flux<String> stringFlux  = Flux.just("Spring","Spring boot", "Reactive Spring")
                      .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    public void fluxTestElementsCount(){
        Flux<String> stringFlux  = Flux.just("Spring","Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    public void fluxTestElements_WithError1() {
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring boot", "Reactive Spring")
                .expectError(RuntimeException.class)
                .verify();
     }

     @Test
    public void monoTest(){
        Mono<String> stringMono =  Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
     }

    @Test
    public void monoTestError(){
        Mono<String> stringMono =  Mono.error(new RuntimeException("Exception occurred"));

        StepVerifier.create(stringMono.log())
                .expectError(RuntimeException.class)
                .verify();
    }



    }
