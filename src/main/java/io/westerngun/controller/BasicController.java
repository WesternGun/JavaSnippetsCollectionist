package io.westerngun.controller;

import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BasicController {
    private final DumbService dumbService;

    @PostMapping(path = "/bar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Bar sendPreAuthRequest(@ApiParam(required = true) @RequestBody @Valid SimpleRequest request) {
        log.info("Beginning query");
        Bar bar = dumbService.checkConnection();
        Assert.notNull(bar, "Returned null!");
        return bar;
    }
}
