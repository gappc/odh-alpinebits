/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.guestrequests.resretrievers;

import it.bz.opendatahub.alpinebits.mapping.entity.guestrequests.resretrievers.HotelReservationId;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAResRetrieveRS;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Map AlpineBits hotel reservation ID objects for OTA_ResRetrieveRS
 * requests to business objects and vice versa.
 */
@Mapper
public interface HotelReservationIdMapper {

    @Mapping(target = "resIdType", source = "resIDType")
    @Mapping(target = "resIdValue", source = "resIDValue")
    @Mapping(target = "resIdSource", source = "resIDSource")
    @Mapping(target = "resIdSourceContext", source = "resIDSourceContext")
    HotelReservationId toHotelReservationId(
            OTAResRetrieveRS
                    .ReservationsList
                    .HotelReservation
                    .ResGlobalInfo
                    .HotelReservationIDs
                    .HotelReservationID hotelReservationID,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    OTAResRetrieveRS.ReservationsList.HotelReservation.ResGlobalInfo.HotelReservationIDs.HotelReservationID toOTAHotelReservationId(
            HotelReservationId hotelReservationId,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );
}
