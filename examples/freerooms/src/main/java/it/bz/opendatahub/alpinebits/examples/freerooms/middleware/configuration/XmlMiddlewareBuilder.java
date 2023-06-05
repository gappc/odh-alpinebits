// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: AGPL-3.0-or-later

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.examples.freerooms.middleware.configuration;

import it.bz.opendatahub.alpinebits.middleware.Key;
import it.bz.opendatahub.alpinebits.middleware.Middleware;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlRequestMappingMiddleware;
import it.bz.opendatahub.alpinebits.xml.middleware.XmlResponseMappingMiddleware;

import javax.xml.validation.Schema;

/**
 * Utility class to build XML middlewares.
 */
public final class XmlMiddlewareBuilder {

    private XmlMiddlewareBuilder() {
        // Empty
    }

    public static <T> Middleware buildXmlToObjectConvertingMiddleware(Key<T> key) {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        XmlToObjectConverter<T> converter = new JAXBXmlToObjectConverter.Builder<>(key.getType()).schema(schema).build();
        return new XmlRequestMappingMiddleware<>(converter, key);
    }

    public static <T> Middleware buildObjectToXmlConvertingMiddleware(Key<T> key) {
        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2017-10");
        ObjectToXmlConverter converter = new JAXBObjectToXmlConverter.Builder()
                .schema(schema)
                .prettyPrint(true)
                .build();
        return new XmlResponseMappingMiddleware<>(converter, key);
    }

}
