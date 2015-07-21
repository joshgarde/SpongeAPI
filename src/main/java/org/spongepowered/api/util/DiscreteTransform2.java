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
package org.spongepowered.api.util;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.imaginary.Complexd;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

/**
 * Represents a transform. It is 2 dimensional and discrete.
 * It will never cause aliasing.
 */
public class DiscreteTransform2 {

    /**
     * Represents an identity transformation. Does nothing!
     */
    public static final DiscreteTransform2 IDENTITY = new DiscreteTransform2(Matrix3d.IDENTITY);
    private final Matrix3d transform;
    @Nullable
    private Vector3d transformRow0 = null;
    @Nullable
    private Vector3d transformRow1 = null;

    private DiscreteTransform2(Matrix3d transform) {
        this.transform = transform;
    }

    /**
     * Returns the matrix representation of the transform.
     * It is 3D to allow it to include a translation.
     *
     * @return The matrix for this transform
     */
    public Matrix3d getMatrix() {
        return this.transform;
    }

    /**
     * Transforms a vector using this transforms.
     *
     * @param vector The original vector
     * @return The transformed vector
     */
    public Vector2i transform(Vector2i vector) {
        return transform(vector.getX(), vector.getY());
    }

    /**
     * Transform a vector represented as a pair of
     * coordinates using this transform.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @return The transformed vector
     */
    public Vector2i transform(int x, int y) {
        return this.transform.transform(x, y, 1).toVector2().toInt();
    }

    /**
     * Transforms the x coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param vector The original vector
     * @return The transformed x coordinate
     */
    public int transformX(Vector2i vector) {
        return transformX(vector.getX(), vector.getY());
    }

    /**
     * Transforms the x coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @return The transformed x coordinate
     */
    public int transformX(int x, int y) {
        if (this.transformRow0 == null) {
            this.transformRow0 = this.transform.getRow(0);
        }
        return GenericMath.floor(this.transformRow0.dot(x, y, 1));
    }

    /**
     * Transforms the y coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param vector The original vector
     * @return The transformed y coordinate
     */
    public int transformY(Vector2i vector) {
        return transformY(vector.getX(), vector.getY());
    }

    /**
     * Transforms the y coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @return The transformed y coordinate
     */
    public int transformY(int x, int y) {
        if (this.transformRow1 == null) {
            this.transformRow1 = this.transform.getRow(1);
        }
        return GenericMath.floor(this.transformRow1.dot(x, y, 1));
    }

    /**
     * Adds a translation to this transform and returns
     * it as a new transform.
     *
     * @param vector The translation vector
     * @return The translated transform as a copy
     */
    public DiscreteTransform2 withTranslation(Vector2i vector) {
        return withTranslation(vector.getX(), vector.getY());
    }

    /**
     * Adds a translation to this transform and returns
     * it as a new transform.
     *
     * @param x The x coordinate of the translation
     * @param y The y coordinate of the translation
     * @return The translated transform as a copy
     */
    public DiscreteTransform2 withTranslation(int x, int y) {
        return new DiscreteTransform2(this.transform.translate(x, y));
    }

    /**
     * Adds a scale factor to this transform and returns
     * it as a new transform. This factor must be greater
     * than zero.
     *
     * @param a The scale factor
     * @return The scaled transform as a copy
     */
    public DiscreteTransform2 withScale(int a) {
        return withScale(a, a);
    }

    /**
     * Adds a scale factor for each axis to this transform
     * and returns it as a new transform. The factors must
     * be greater than zero.
     *
     * @param vector The scale vector
     * @return The scaled transform as a copy
     */
    public DiscreteTransform2 withScale(Vector2i vector) {
        return withScale(vector.getX(), vector.getY());
    }

    /**
     * Adds a scale factor for each axis to this transform
     * and returns it as a new transform. The factors must
     * be greater than zero.
     *
     * @param x The scale factor on x
     * @param y The scale factor on y
     * @return The scaled transform as a copy
     */
    public DiscreteTransform2 withScale(int x, int y) {
        Preconditions.checkArgument(x > 0, "x <= 0");
        Preconditions.checkArgument(y > 0, "y <= 0");
        return new DiscreteTransform2(this.transform.scale(x, y, 1));
    }

    /**
     * Adds a rotation to this transform, in the xy plane,
     * and returns it as a new transform. The rotation
     * is given is quarter turns. The actual rotation
     * is {@code quarterTurns * 90}.
     *
     * @param quarterTurns The number of quarter turns in this rotation
     * @return The rotated transform as a copy
     */
    public DiscreteTransform2 withRotation(int quarterTurns) {
        return new DiscreteTransform2(this.transform.rotate(Complexd.fromAngleDeg(quarterTurns * 90)));
    }

    /**
     * Adds another transformation to this transformation and
     * returns int as a new transform.
     *
     * @param transform The transformation to add
     * @return The added transforms as a copy
     */
    public DiscreteTransform2 withTransformation(DiscreteTransform2 transform) {
        return new DiscreteTransform2(transform.getMatrix().mul(getMatrix()));
    }

    /**
     * Returns a new transform representing a translation.
     *
     * @param vector The translation vector
     * @return The new translation transform
     */
    public static DiscreteTransform2 fromTranslation(Vector2i vector) {
        return fromTranslation(vector.getX(), vector.getY());
    }

    /**
     * Returns a new transform representing a translation.
     *
     * @param x The x coordinate of the translation
     * @param y The y coordinate of the translation
     * @return The new translation transform
     */
    public static DiscreteTransform2 fromTranslation(int x, int y) {
        return new DiscreteTransform2(Matrix3d.createTranslation(x, y));
    }

    /**
     * Returns a new transform representing a scaling.
     *
     * @param a The scale factor
     * @return The new scale transform
     */
    public static DiscreteTransform2 fromScale(int a) {
        return fromScale(a, a);
    }

    /**
     * Returns a new transform representing a scaling on each axis.
     *
     * @param vector The scale vector
     * @return The new scale transform
     */
    public static DiscreteTransform2 fromScale(Vector2i vector) {
        return fromScale(vector.getX(), vector.getY());
    }

    /**
     * Returns a new transform representing a scaling on each axis.
     *
     * @param x The scale factor on x
     * @param y The scale factor on y
     * @return The new scale transform
     */
    public static DiscreteTransform2 fromScale(int x, int y) {
        Preconditions.checkArgument(x > 0, "x <= 0");
        Preconditions.checkArgument(y > 0, "y <= 0");
        return new DiscreteTransform2(Matrix3d.createScaling(x, y, 1));
    }

    /**
     * Returns a new transform representing a rotation in the xy plane.
     * The rotation is given is quarter turns. The actual rotation
     * is {@code quarterTurns * 90}.
     *
     * @param quarterTurns The number of quarter turns in this rotation
     * @return The new rotation transform
     */
    public static DiscreteTransform2 fromRotation(int quarterTurns) {
        return new DiscreteTransform2(Matrix3d.createRotation(Complexd.fromAngleDeg(quarterTurns * 90)));
    }

}
