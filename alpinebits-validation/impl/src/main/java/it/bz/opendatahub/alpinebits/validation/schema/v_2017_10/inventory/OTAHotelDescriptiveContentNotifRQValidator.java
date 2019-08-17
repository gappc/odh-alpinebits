/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2017_10.inventory;

import it.bz.opendatahub.alpinebits.validation.ContextWithAction;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.SimpleValidationPath;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2017_10.OTAHotelDescriptiveContentNotifRQ;

/**
 * Use this validator to validate {@link OTAHotelDescriptiveContentNotifRQ}
 * objects (AlpineBits 2017-10).
 */
public class OTAHotelDescriptiveContentNotifRQValidator
        implements Validator<OTAHotelDescriptiveContentNotifRQ, ContextWithAction> {

    public static final String ELEMENT_NAME = Names.OTA_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    private final HotelDescriptiveContentsValidator hotelDescriptiveContentsValidator = new HotelDescriptiveContentsValidator();

    @Override
    public void validate(
            OTAHotelDescriptiveContentNotifRQ hotelDescriptiveContentNotifRQ,
            ContextWithAction ctx,
            ValidationPath unused
    ) {
        // Initialize validation path
        ValidationPath path = SimpleValidationPath.fromPath(ELEMENT_NAME);

        VALIDATOR.expectNotNull(
                hotelDescriptiveContentNotifRQ,
                ErrorMessage.EXPECT_HOTEL_DESCRIPTIVE_CONTENT_NOTIF_RQ_TO_BE_NOT_NULL,
                path
        );
        VALIDATOR.expectNotNull(ctx, ErrorMessage.EXPECT_CONTEXT_TO_BE_NOT_NULL);

        this.hotelDescriptiveContentsValidator.validate(
                hotelDescriptiveContentNotifRQ.getHotelDescriptiveContents(),
                ctx,
                path.withElement(HotelDescriptiveContentsValidator.ELEMENT_NAME)
        );
    }

}
