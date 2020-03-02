/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Customer;
import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.Gender;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAResRetrieveRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map AlpineBits customer objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper
public interface CustomerMapper {

    @Mapping(target = "namePrefix", source = "personName.namePrefix")
    @Mapping(target = "givenName", source = "personName.givenName")
    @Mapping(target = "surname", source = "personName.surname")
    @Mapping(target = "nameTitle", source = "personName.nameTitle")
    @Mapping(target = "email.email", source = "email.value")
    @Mapping(target = "address.countryNameCode", source = "address.countryName.code")
    Customer toCustomer(
            OTAResRetrieveRS.ReservationsList.HotelReservation.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer customer,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.ResGuests.ResGuest.Profiles.ProfileInfo.Profile.Customer toOTACustomer(
            Customer customer,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    default Gender stringToGender(
            String s,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        return Gender.fromString(s);
    }

    default String genderToString(
            Gender gender,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    ) {
        return gender == null ? null : gender.toString();
    }
}
