/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.otaextensions;

import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.AffiliationInfoType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.ContactInfosType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelDescriptiveContentType;
import it.bz.opendatahub.alpinebits.otaextension.schema.ota2015a.HotelInfoType;
import it.bz.opendatahub.alpinebits.otaextensions.exception.OtaExtensionException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * This class provides {@link JAXBContext}s for {@link AffiliationInfoType},
 * {@link ContactInfosType}, {@link HotelInfoType} and
 * {@link HotelDescriptiveContentType.Policies}.
 * <p>
 * The main purpose of this class is to encapsulate the slow JAXBContext
 * initialisation, caused by the large number of autogenerated OTA Java files.
 * Using lazy initialization, the first access to a JAXBContext is still slow,
 * but further accesses are then cached.
 * <p>
 * Access to this class may throw a RuntimeException in case that JAXBContext
 * could not be created.
 */
public final class JAXBContextProvider {

    private static final JAXBContext JAXB_CONTEXT;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(
                    AffiliationInfoType.class,
                    ContactInfosType.class,
                    HotelInfoType.class,
                    HotelDescriptiveContentType.Policies.class
            );
        } catch (JAXBException e) {
            throw new OtaExtensionException("Error while initializing JAXBContext for AffiliationInfoType.class", e);
        }
    }

    private JAXBContextProvider() {
        // Empty
    }

    /**
     * Return a {@link JAXBContext} instance that knows how to handle
     * {@link AffiliationInfoType}, {@link ContactInfosType}, {@link HotelInfoType} and
     * {@link HotelDescriptiveContentType.Policies} types.
     * <p>
     * The first invocation of this method may be slow, further invocations should
     * then be faster.
     *
     * @return A JAXBContext that can be used for marshalling / unmarshalling.
     */
    public static JAXBContext getJAXBContext() {
        return JAXB_CONTEXT;
    }

}