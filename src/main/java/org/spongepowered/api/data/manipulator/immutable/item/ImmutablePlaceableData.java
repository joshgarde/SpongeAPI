/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.data.manipulator.immutable.item;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.mutable.item.BlockItemData;
import org.spongepowered.api.data.manipulator.mutable.item.PlaceableData;
import org.spongepowered.api.data.value.immutable.ImmutableSetValue;
import org.spongepowered.api.item.ItemBlock;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Set;

/**
 * An {@link ImmutableDataManipulator} handling the {@link Set} of
 * {@link BlockType}s that the owning {@link ItemStack} may be placed on.
 * If an item can be placed as a block, such as {@link BlockItemData},
 * this item data, if added to the item, can prevent placement of the item
 * excluding the {@link BlockType}s from this data.
 */
public interface ImmutablePlaceableData extends ImmutableDataManipulator<ImmutablePlaceableData, PlaceableData> {

    /**
     * Gets the {@link ImmutableSetValue} of {@link BlockType}s the
     * {@link ItemBlock} can be placed on.
     *
     * @return The immutable set value of block types the item can be placed on
     */
    ImmutableSetValue<BlockType> placeable();

}
