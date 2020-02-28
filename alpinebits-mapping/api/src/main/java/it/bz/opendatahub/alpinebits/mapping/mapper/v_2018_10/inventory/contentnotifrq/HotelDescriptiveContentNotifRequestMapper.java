/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory.contentnotifrq;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContent;
import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveContentNotifRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory.HotelDescriptiveContentMapper;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveContentNotifRQ;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Convert between AlpineBits {@link OTAHotelDescriptiveContentNotifRQ}
 * (Inventory) and {@link HotelDescriptiveContent} objects.
 */
@Mapper(uses = {HotelDescriptiveContentMapper.class})
public interface HotelDescriptiveContentNotifRequestMapper {

    @Mapping(target = "hotelDescriptiveContent", source = "hotelDescriptiveContents.hotelDescriptiveContent")
    HotelDescriptiveContentNotifRequest toHotelDescriptiveContentNotifRequest(
            OTAHotelDescriptiveContentNotifRQ otaHotelDescriptiveContentNotifRQ,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

    @InheritInverseConfiguration
    @Mapping(target = "version", constant = "8.000")
    @Mapping(target = "timeStamp", ignore = true)
    OTAHotelDescriptiveContentNotifRQ toOTAHotelDescriptiveContentNotifRQ(
            HotelDescriptiveContentNotifRequest hotelDescriptiveContentNotifRequest,
            @Context it.bz.opendatahub.alpinebits.middleware.Context ctx
    );

}