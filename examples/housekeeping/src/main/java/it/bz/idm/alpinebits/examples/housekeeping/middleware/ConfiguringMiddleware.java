/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.examples.housekeeping.middleware;

import it.bz.idm.alpinebits.common.constants.HousekeepingActionEnum;
import it.bz.idm.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.idm.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.idm.alpinebits.middleware.Context;
import it.bz.idm.alpinebits.middleware.Middleware;
import it.bz.idm.alpinebits.middleware.MiddlewareChain;
import it.bz.idm.alpinebits.routing.DefaultRouter;
import it.bz.idm.alpinebits.routing.VersionRoutingBuilder;
import it.bz.idm.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.HousekeepingWriterMiddleware;
import it.bz.idm.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import java.util.Arrays;

/**
 * This {@link Middleware} configures a set of middlewares, such that
 * the resulting server is able to respond to Housekeeping requests.
 * <p>
 * The resulting server supports the version defined by
 * {@link ConfiguringMiddleware#DEFAULT_VERSION} only (typically 2017-10).
 * <p>
 * A basic authentication check is enforced, although any username and
 * password combination is valid. In other words: a request MUST contain
 * basic authentication information, but that information is not checked
 * any further.
 */
public class ConfiguringMiddleware implements Middleware {

    public static final String DEFAULT_VERSION = "2017-10";

    private final Middleware middleware;

    public ConfiguringMiddleware() {
        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new BasicAuthenticationMiddleware(),
                new GzipUnsupportedMiddleware(),
                new MultipartFormDataParserMiddleware(),
                new HousekeepingWriterMiddleware(),
                this.buildRoutingMiddleware()
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

    private Middleware buildRoutingMiddleware() {
        VersionRoutingBuilder builder = new DefaultRouter.Builder()
                .forVersion(DEFAULT_VERSION)
                .addMiddleware(HousekeepingActionEnum.GET_VERSION.getAction(), new HousekeepingGetVersionMiddleware())
                .addMiddleware(HousekeepingActionEnum.GET_CAPABLILITIES.getAction(), new HousekeepingGetCapabilitiesMiddleware())
                .done();
        return new RoutingMiddleware(builder.buildRouter());
    }
}