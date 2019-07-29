package io.westerngun.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class DumbService {
    public Bar checkConnection() {
        Foo foo = new Foo();
        Bar bar = new Bar();
        log.info("Can we reach here?");

        return bar;
    }
}
