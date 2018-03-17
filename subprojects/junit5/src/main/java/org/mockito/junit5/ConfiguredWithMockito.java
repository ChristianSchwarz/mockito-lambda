/*
 * Copyright (c) 2018 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.junit5;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.quality.Strictness;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that can configure Mockito as invoked by the {@link MockitoExtension}.
 */
@ExtendWith(MockitoExtension.class)
@Retention(RUNTIME)
public @interface ConfiguredWithMockito {

    /**
     * Configure the strictness used in this test.
     * @return The strictness to configure, by default {@link Strictness#WARN}
     */
    Strictness strictness() default Strictness.WARN;
}
