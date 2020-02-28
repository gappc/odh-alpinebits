/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.common.constants.OTACodeRoomAmenityType;
import it.bz.opendatahub.alpinebits.validation.ErrorMessage;
import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationHelper;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom.Amenities;

/**
 * Validate OTAHotelDescriptiveContentNotifRQ-&gt;HotelDescriptiveContents
 * -&gt;HotelDescriptiveContent-&gt;FacilityInfo-&gt;GuestRooms-&gt;GuestRoom-&gt;Amenities
 * elements.
 */
public class AmenitiesValidator implements Validator<Amenities, Void> {

    public static final String ELEMENT_NAME = Names.AMENITIES;

    private static final ValidationHelper VALIDATOR = ValidationHelper.withClientDataError();

    @Override
    public void validate(Amenities amenities, Void ctx, ValidationPath path) {
        // Amenities are optional
        if (amenities == null || amenities.getAmenities() == null) {
            return;
        }

        for (int i = 0; i < amenities.getAmenities().size(); i++) {
            Amenities.Amenity amenity = amenities.getAmenities().get(i);

            ValidationPath indexedPath = path.withElement(Names.AMENITY).withIndex(i);

            VALIDATOR.expectNotNull(
                    amenity.getRoomAmenityCode(),
                    ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_NOT_NULL,
                    indexedPath.withAttribute(Names.ROOM_AMENITY_CODE)
            );

            this.validateRoomAmenityTypeCode(amenity.getRoomAmenityCode().intValue(), indexedPath);
        }
    }

    private void validateRoomAmenityTypeCode(int code, ValidationPath path) {
        if (!OTACodeRoomAmenityType.isCodeDefined(code)) {
            String message = String.format(ErrorMessage.EXPECT_ROOM_AMENITY_CODE_TO_BE_DEFINED, code);
            VALIDATOR.throwValidationException(message, path.withAttribute(Names.ROOM_AMENITY_CODE));
        }
    }

}
