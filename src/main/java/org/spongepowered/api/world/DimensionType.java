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
package org.spongepowered.api.world;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.service.permission.context.Contextual;
import org.spongepowered.api.util.annotation.CatalogedBy;

/**
 * Represents the dimension of a {@link World}.
 */
@CatalogedBy(DimensionTypes.class)
public interface DimensionType extends Contextual, CatalogType {

    /**
     * Returns the name of this {@link DimensionType}.
     *
     * @return The name
     */
    String getName();

    /**
     * Returns whether players can respawn within {@link DimensionType} after death.
     *
     * @return True if players can respawn, false if not
     */
    boolean allowsPlayerRespawns();

    /**
     * Sets whether players in this {@link DimensionType} can respawn.
     *
     * @param allow Whether players can respawn
     */
    void setAllowsPlayerRespawns(boolean allow);

    /**
     * Returns the minimum spawn height for {@link DimensionType}.
     *
     * @return The minimum spawn height
     */
    int getMinimumSpawnHeight();

    /**
     * Returns whether water evaporates for {@link DimensionType}.
     *
     * @return True if water evaporates, false if not
     */
    boolean doesWaterEvaporate();

    /**
     * Sets whether water in this {@link DimensionType} evaporates.
     *
     * @param evaporates Whether water evaporates
     */
    void setWaterEvaporates(boolean evaporates);

    /**
     * Returns whether this {@link DimensionType} has a sky (lack of bedrock).
     *
     * @return True if sky is present, false if not
     */
    boolean hasSky();

    /**
     * Get the type of dimension.
     *
     * @return The type of dimension
     */
    DimensionType getType();

    /**
     * Gets the highest naturally generated y-coordinate of {@link World}s in this dimension. Usually 128 (no sky) or 256 (sky).
     * @return The generated height
     */
    int getHeight();

    /**
     * Gets the maximum y-coordinate a non-air cuboid can exist at of {@link World}s in this dimension. Usually 256.
     * @return The build height
     */
    int getBuildHeight();

    /**
     * Returns whether spawn chunks of this {@link DimensionType} remain loaded
     * when no players are present.
     *
     * @return True if spawn chunks of this {@link DimensionType} remain loaded
     *         without players, false if not
     */
    boolean doesKeepSpawnLoaded();
}
