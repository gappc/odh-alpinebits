/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.inventory;

import it.bz.opendatahub.alpinebits.mapping.entity.inventory.HotelDescriptiveInfoRequest;
import it.bz.opendatahub.alpinebits.mapping.mapper.v_2018_10.InventoryMapperInstances;
import it.bz.opendatahub.alpinebits.xml.JAXBObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.JAXBXmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.ObjectToXmlConverter;
import it.bz.opendatahub.alpinebits.xml.XmlToObjectConverter;
import it.bz.opendatahub.alpinebits.xml.XmlValidationSchemaProvider;
import it.bz.opendatahub.alpinebits.xml.schema.v_2018_10.OTAHotelDescriptiveInfoRQ;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * This class tests the mapper of AlpineBits objects
 * to business objects.
 */
public class HotelDescriptiveInfoRequestMappingTest {

    @DataProvider(name = "xmlValid")
    public static Object[][] xmlFiles() {
        return new Object[][]{
                {"Inventory-OTA_HotelDescriptiveInfoRQ-basicpullRQ.xml"},
                {"Inventory-OTA_HotelDescriptiveInfoRQ-hotelinfo.xml"},
        };
    }

    @Test(dataProvider = "xmlValid")
    public void fullConversion(String xmlFile) throws Exception {
        String filename = "examples/v_2018_10/" + xmlFile;
        InputStream inputXmlStream = this.getClass().getClassLoader().getResourceAsStream(filename);

        Schema schema = XmlValidationSchemaProvider.buildRngSchemaForAlpineBitsVersion("2018-10");
        XmlToObjectConverter<OTAHotelDescriptiveInfoRQ> converter = this.validatingXmlToObjectConverter(
                OTAHotelDescriptiveInfoRQ.class, schema);

        OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ = converter.toObject(inputXmlStream);

        HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER
                        .toHotelDescriptiveInfoRequest(otaHotelDescriptiveInfoRQ, null);
        assertNotNull(hotelDescriptiveInfoRequest);

        OTAHotelDescriptiveInfoRQ otaHotelDescriptiveInfoRQ2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER
                        .toOTAHotelDescriptiveInfoRQ(hotelDescriptiveInfoRequest, null);
        assertNotNull(otaHotelDescriptiveInfoRQ2);

        HotelDescriptiveInfoRequest hotelDescriptiveInfoRequest2 =
                InventoryMapperInstances.HOTEL_DESCRIPTIVE_INFO_REQUEST_MAPPER
                        .toHotelDescriptiveInfoRequest(otaHotelDescriptiveInfoRQ2, null);
        assertNotNull(hotelDescriptiveInfoRequest2);

        assertEquals(hotelDescriptiveInfoRequest2.toString(), hotelDescriptiveInfoRequest.toString());

        ObjectToXmlConverter<OTAHotelDescriptiveInfoRQ> toObjectConverter = this.validatingObjectToXmlConverter(
                OTAHotelDescriptiveInfoRQ.class, schema);

        toObjectConverter.toXml(otaHotelDescriptiveInfoRQ2, new ByteArrayOutputStream());
    }

    private <T> XmlToObjectConverter<T> validatingXmlToObjectConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBXmlToObjectConverter.Builder<>(classToBeBound).schema(schema).build();
    }

    private <T> ObjectToXmlConverter<T> validatingObjectToXmlConverter(Class<T> classToBeBound, Schema schema) throws JAXBException {
        return new JAXBObjectToXmlConverter.Builder<>(classToBeBound).schema(schema).build();
    }

}