// SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
//
// SPDX-License-Identifier: MPL-2.0

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package it.bz.opendatahub.alpinebits.validation.schema.v_2022_10.inventory;

import it.bz.opendatahub.alpinebits.validation.Names;
import it.bz.opendatahub.alpinebits.validation.ValidationPath;
import it.bz.opendatahub.alpinebits.validation.Validator;
import it.bz.opendatahub.alpinebits.xml.schema.ota.TextItemsType.TextItem;

/**
 * Use this validator to validate the TextItem in AlpineBits 2022
 * Inventory documents.
 *
 * @see TextItem
 */
public class TextItemValidator implements Validator<TextItem, Void> {

    public static final String ELEMENT_NAME = Names.TEXT_ITEM;

    private static final Validator<TextItem, Void> VALIDATION_DELEGATE =
            new it.bz.opendatahub.alpinebits.validation.schema.v_2020_10.inventory.TextItemValidator();

    @Override
    public void validate(TextItem textItem, Void ctx, ValidationPath path) {
        // Delegate validation to AlpineBits 2020 implementation,
        // since the validation remains the same
        VALIDATION_DELEGATE.validate(textItem, ctx, path);
    }
}
