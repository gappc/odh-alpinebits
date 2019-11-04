/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.guestrequests.middleware;

import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsCapability;
import it.bz.opendatahub.alpinebits.common.constants.AlpineBitsVersion;
import it.bz.opendatahub.alpinebits.common.utils.middleware.ComposingMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.examples.guestrequests.middleware.configuration.GuestRequestsConfirmationMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.examples.guestrequests.middleware.configuration.GuestRequestsReadMiddlewareBuilder;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetCapabilitiesMiddleware;
import it.bz.opendatahub.alpinebits.housekeeping.middleware.HousekeepingGetVersionMiddleware;
import it.bz.opendatahub.alpinebits.middleware.Context;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.middleware.MiddlewareChain;
import it.bz.opendatahub.alpinebits.routing.DefaultRouter;
import it.bz.opendatahub.alpinebits.routing.Router;
import it.bz.opendatahub.alpinebits.routing.constants.Action;
import it.bz.opendatahub.alpinebits.routing.middleware.RoutingMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.AlpineBitsClientProtocolMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.BasicAuthenticationMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.GzipUnsupportedMiddleware;
import it.bz.opendatahub.alpinebits.servlet.middleware.MultipartFormDataParserMiddleware;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

/**
 * This {@link Middleware} configures a set of middlewares, such that
 * the resulting server is able to respond to AlpineBits Guest Requests.
 * <p>
 * The resulting server supports the {@link AlpineBitsVersion#V_2017_10}
 * version only.
 * <p>
 * A basic authentication check is enforced, although any username and
 * password combination is valid. In other words: a request MUST contain
 * basic authentication information, but that information is not checked
 * any further.
 */
public class ConfiguringMiddleware implements Middleware {

    private final Middleware middleware;

    public ConfiguringMiddleware() throws JAXBException {
        this.middleware = ComposingMiddlewareBuilder.compose(Arrays.asList(
                new AlpineBitsClientProtocolMiddleware(),
                new BasicAuthenticationMiddleware(),
                new GzipUnsupportedMiddleware(),
                new MultipartFormDataParserMiddleware(),
                this.buildRoutingMiddleware()
        ));
    }

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        this.middleware.handleContext(ctx, chain);
    }

    private Middleware buildRoutingMiddleware() throws JAXBException {
        Router router = new DefaultRouter.Builder()
                .version(AlpineBitsVersion.V_2017_10)
                .supportsAction(Action.GET_VERSION)
                .withCapabilities(AlpineBitsCapability.GET_VERSION)
                .using(new HousekeepingGetVersionMiddleware())
                .and()
                .supportsAction(Action.GET_CAPABILITIES)
                .withCapabilities(AlpineBitsCapability.GET_CAPABILITIES)
                .using(new HousekeepingGetCapabilitiesMiddleware())
                .and()
                .supportsAction(Action.GUEST_REQUESTS_READ_GUEST_REQUESTS)
                .withCapabilities(AlpineBitsCapability.GUEST_REQUESTS_READ)
                .using(GuestRequestsReadMiddlewareBuilder.buildHotelReservationReadMiddleware())
                .and()
                .supportsAction(Action.GUEST_REQUESTS_CONFIRM_GUEST_REQUESTS_READ)
                .withCapabilities()
                .using(GuestRequestsConfirmationMiddlewareBuilder.buildHotelReservationConfirmationMiddleware())
                .versionComplete()
                .buildRouter();
        return new RoutingMiddleware(router);
    }

}