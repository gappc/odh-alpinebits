// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.common.utils.middleware.utils;

import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;

/**
 * Builder for AlpineBits {@link Middleware}.
 */
public final class MiddlewareBuilder {

    public static final String EXCEPTION_MESSAGE = "FROM MIDDLEAWARE";

    private static final Key<String> DEFAULT_KEY = Key.key("KEY", String.class);
    private static final String DEFAULT_VALUE = "VALUE";

    private MiddlewareBuilder() {
        // Empty
    }

    public static Middleware buildMiddleware() {
        return MiddlewareBuilder.buildMiddleware(DEFAULT_KEY, DEFAULT_VALUE);
    }

    public static <T> Middleware buildMiddleware(Key<T> key, T value) {
        return MiddlewareBuilder.buildMiddleware(key, value, false);
    }

    /**
     * Build and return a {@link Middleware}, where the value is added to the
     * {@link Context} state.
     *
     * @param key      the key used to store the value in the {@link Context} state
     * @param value    the value stored in the {@link Context} state
     * @param callNext if the middleware should invoke the {@link MiddlewareChain#next()}
     *                 method
     * @return the {@link Middleware} with the value added to the {@link Context}
     * state
     */
    public static <T> Middleware buildMiddleware(Key<T> key, T value, boolean callNext) {
        return (ctx, chain) -> {
            ctx.put(key, value);
            if (callNext) {
                chain.next();
            }
        };
    }

    /**
     * Build and return a {@link Middleware} that throws a {@link RuntimeException}.
     *
     * @return the {@link Middleware} that throws a {@link RuntimeException}
     */
    @SuppressWarnings("squid:S00112")
    public static Middleware buildThrowingMiddleware() {
        return (ctx, chain) -> {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        };
    }

}
