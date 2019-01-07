/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.idm.alpinebits.mapping.mapper.v_2017_10.freerooms;

import it.bz.idm.alpinebits.mapping.entity.freerooms.FreeRoomsResponse;
import it.bz.idm.alpinebits.mapping.mapper.v_2017_10.common.WarningMapper;
import it.bz.idm.alpinebits.xml.schema.v_2017_10.OTAHotelAvailNotifRS;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits OTAHotelAvailNotifRS
 * and {@link FreeRoomsResponse} objects.
 */
@Mapper(
        uses = {
                AfterFreeRoomsResponseMapping.class,
                WarningMapper.class
        }
)
public interface FreeRoomsResponseMapper {

    @Mapping(target = "errors", source = "errors.errors")
    @Mapping(target = "warnings", source = "warnings.warnings")
    FreeRoomsResponse toFreeRoomsResponse(OTAHotelAvailNotifRS otaHotelAvailNotifRS);

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "1.001")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelAvailNotifRS toOTAHotelAvailNotifRS(FreeRoomsResponse freeRoomsResponse);

}
