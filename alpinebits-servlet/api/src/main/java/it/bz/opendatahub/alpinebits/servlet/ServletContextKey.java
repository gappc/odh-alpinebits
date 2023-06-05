// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.servlet;

import it.bz.opendatahub.alpinebits.middleware.Key;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class contains key definitions for servlet values.
 */
public final class ServletContextKey {

    /**
     * Context key for HTTP request.
     */
    public static final Key<HttpServletRequest> SERVLET_REQUEST = Key.key(
            "servlet.request", HttpServletRequest.class
    );

    /**
     * Context key for HTTP response.
     */
    public static final Key<HttpServletResponse> SERVLET_RESPONSE = Key.key(
            "servlet.response", HttpServletResponse.class
    );

    private ServletContextKey() {
        // Empty
    }

}
