package com.sid.injector.injectors;

import com.sid.injector.Container;

public interface Injector {
    <T> T resolve(Container container);
}
