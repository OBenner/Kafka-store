package com.obenner.kafkastore.web;

import com.obenner.kafkastore.model.Ac;
import com.obenner.kafkastore.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/")
@Slf4j
public class Controller {

    @Autowired
    private StoreService storeService;

    @PostMapping("/getObj")
    @ApiOperation("get object from store by id")
    public Mono<Ac> get(@RequestParam Long request) {
        return Mono.just(storeService.getObjClient(request));
    }




    @PostMapping("/share")
    @ApiOperation("share object from another instance")
    public Mono<Ac> getObj(@RequestParam long id) {
        return storeService.getAccInsideKafka(id);
    }


}
