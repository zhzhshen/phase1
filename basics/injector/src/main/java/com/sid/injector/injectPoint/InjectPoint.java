package com.sid.injector.injectPoint;

import com.sid.injector.Container;

public interface InjectPoint {
    <T> T resolve(Container container);
}
