/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.idm.alpinebits.http.middleware;

import com.idm.alpinebits.http.BasicAuthenticationException;
import com.idm.alpinebits.http.HttpContextKey;
import com.idm.alpinebits.middleware.Context;
import com.idm.alpinebits.middleware.Middleware;
import com.idm.alpinebits.middleware.MiddlewareChain;
import com.idm.alpinebits.middleware.RequiredContextKeyMissingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * This middleware extracts the value of the <code>Authorization</code> header from the
 * HTTP request. The header value must contain a <code>Basic Authentication</code> (refer to
 * <a href="http://www.faqs.org/rfcs/rfc1945.html">RFC 1945, Section 11.1</a> for more
 * information). Please note, that this middleware doesn't check the provided credentials
 * for validity. It just extracts the credentials and adds them to the {@link Context}
 * for later usage.
 * <p>
 * Given a <code>Basic Authentication</code> was found, the username is added to the
 * context using the {@link HttpContextKey#HTTP_USERNAME} key, the password is added
 * using the {@link HttpContextKey#HTTP_PASSWORD} key.
 * <p>
 * The HTTP request must be present in the {@link Context}. Otherwise, a
 * {@link RequiredContextKeyMissingException} is thrown.
 * <p>
 * If no <code>Basic Authentication</code> header was found, or the <code>Basic Authentication</code>
 * could not be parsed, a {@link BasicAuthenticationException} is thrown.
 */
public class BasicAuthenticationMiddleware implements Middleware {

    public static final String BASIC_AUTHENTICATION_HEADER = "Authorization";

    private static final Logger LOG = LoggerFactory.getLogger(BasicAuthenticationMiddleware.class);

    @Override
    public void handleContext(Context ctx, MiddlewareChain chain) {
        HttpServletRequest request = ctx.getOrThrow(HttpContextKey.HTTP_REQUEST);

        String header = request.getHeader(BASIC_AUTHENTICATION_HEADER);
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            throw new BasicAuthenticationException("No basic authentication header found");
        }

        String[] tokens = this.extractAndDecodeHeader(header);

        String username = tokens[0];

        MDC.put("username", username);

        LOG.debug("Basic authentication header found for user '{}'", username);

        // Add username and password to context
        ctx.put(HttpContextKey.HTTP_USERNAME, username);
        ctx.put(HttpContextKey.HTTP_PASSWORD, () -> tokens[1]);

        chain.next();
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BasicAuthenticationException if the Basic header is not present or is not valid Base64
     */
    private String[] extractAndDecodeHeader(String header) {
        byte[] base64Token;
        try {
            base64Token = header.substring(6).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BasicAuthenticationException("Failed to decode basic authentication header string", e);
        }

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BasicAuthenticationException("Failed to decode basic authentication token");
        }

        String token;
        try {
            token = new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BasicAuthenticationException("Failed to build string from decoded basic authentication header string", e);
        }

        int delim = token.indexOf(':');

        if (delim == -1) {
            throw new BasicAuthenticationException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}