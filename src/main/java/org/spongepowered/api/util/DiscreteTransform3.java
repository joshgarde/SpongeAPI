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
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix4d;
import com.flowpowered.math.vector.Vector3i;
import com.flowpowered.math.vector.Vector4d;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

/**
 * Represents a transform. It is 3 dimensional and discrete.
 * It will never cause aliasing.
 */
public class DiscreteTransform3 {

    /**
     * Represents an identity transformation. Does nothing!
     */
    public static final DiscreteTransform3 IDENTITY = new DiscreteTransform3(Matrix4d.IDENTITY);
    private final Matrix4d transform;
    @Nullable
    private Vector4d transformRow0 = null;
    @Nullable
    private Vector4d transformRow1 = null;
    @Nullable
    private Vector4d transformRow2 = null;

    private DiscreteTransform3(Matrix4d transform) {
        this.transform = transform;
    }

    /**
     * Returns the matrix representation of the transform.
     * It is 4D to allow it to include a translation.
     *
     * @return The matrix for this transform
     */
    public Matrix4d getMatrix() {
        return this.transform;
    }

    /**
     * Transforms a vector using this transforms.
     *
     * @param vector The original vector
     * @return The transformed vector
     */
    public Vector3i transform(Vector3i vector) {
        return transform(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Transform a vector represented as a pair of
     * coordinates using this transform.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @param z The z coordinate of the original vector
     * @return The transformed vector
     */
    public Vector3i transform(int x, int y, int z) {
        return this.transform.transform(x, y, z, 1).toVector3().toInt();
    }

    /**
     * Transforms the x coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param vector The original vector
     * @return The transformed x coordinate
     */
    public int transformX(Vector3i vector) {
        return transformX(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Transforms the x coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @param z The z coordinate of the original vector
     * @return The transformed x coordinate
     */
    public int transformX(int x, int y, int z) {
        if (this.transformRow0 == null) {
            this.transformRow0 = this.transform.getRow(0);
        }
        return GenericMath.floor(this.transformRow0.dot(x, y, z, 1));
    }

    /**
     * Transforms the y coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param vector The original vector
     * @return The transformed y coordinate
     */
    public int transformY(Vector3i vector) {
        return transformY(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Transforms the y coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @param z The z coordinate of the original vector
     * @return The transformed y coordinate
     */
    public int transformY(int x, int y, int z) {
        if (this.transformRow1 == null) {
            this.transformRow1 = this.transform.getRow(1);
        }
        return GenericMath.floor(this.transformRow1.dot(x, y, z, 1));
    }

    /**
     * Transforms the z coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param vector The original vector
     * @return The transformed z coordinate
     */
    public int transformZ(Vector3i vector) {
        return transformZ(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Transforms the z coordinate of a vector
     * using this transform. Only creates a new
     * object on the first call.
     *
     * @param x The x coordinate of the original vector
     * @param y The y coordinate of the original vector
     * @param z The z coordinate of the original vector
     * @return The transformed z coordinate
     */
    public int transformZ(int x, int y, int z) {
        if (this.transformRow2 == null) {
            this.transformRow2 = this.transform.getRow(2);
        }
        return GenericMath.floor(this.transformRow2.dot(x, y, z, 1));
    }

    /**
     * Adds a translation to this transform and returns
     * it as a new transform.
     *
     * @param vector The translation vector
     * @return The translated transform as a copy
     */
    public DiscreteTransform3 withTranslation(Vector3i vector) {
        return withTranslation(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Adds a translation to this transform and returns
     * it as a new transform.
     *
     * @param x The x coordinate of the translation
     * @param y The y coordinate of the translation
     * @param z The z coordinate of the translation
     * @return The translated transform as a copy
     */
    public DiscreteTransform3 withTranslation(int x, int y, int z) {
        return new DiscreteTransform3(this.transform.translate(x, y, z));
    }

    /**
     * Adds a scale factor to this transform and returns
     * it as a new transform. This factor must be greater
     * than zero.
     *
     * @param a The scale factor
     * @return The scaled transform as a copy
     */
    public DiscreteTransform3 withScale(int a) {
        return withScale(a, a, a);
    }

    /**
     * Adds a scale factor for each axis to this transform
     * and returns it as a new transform. The factors must
     * be greater than zero.
     *
     * @param vector The scale vector
     * @return The scaled transform as a copy
     */
    public DiscreteTransform3 withScale(Vector3i vector) {
        return withScale(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Adds a scale factor for each axis to this transform
     * and returns it as a new transform. The factors must
     * be greater than zero.
     *
     * @param x The scale factor on x
     * @param y The scale factor on y
     * @param z The scale factor on z
     * @return The scaled transform as a copy
     */
    public DiscreteTransform3 withScale(int x, int y, int z) {
        Preconditions.checkArgument(x > 0, "x <= 0");
        Preconditions.checkArgument(y > 0, "y <= 0");
        Preconditions.checkArgument(z > 0, "z <= 0");
        return new DiscreteTransform3(this.transform.scale(x, y, z, 1));
    }

    /**
     * Adds a rotation to this transform, around and axis,
     * and returns it as a new transform. The rotation
     * is given is quarter turns. The actual rotation
     * is {@code quarterTurns * 90}.
     *
     * @param quarterTurns The number of quarter turns in this rotation
     * @param axis The axis to rotate around
     * @return The rotated transform as a copy
     */
    public DiscreteTransform3 withRotation(int quarterTurns, Axis axis) {
        return new DiscreteTransform3(this.transform.rotate(Quaterniond.fromAngleDegAxis(quarterTurns * 90, axis.toVector3d())));
    }

    /**
     * Adds another transformation to this transformation and
     * returns int as a new transform.
     *
     * @param transform The transformation to add
     * @return The added transforms as a copy
     */
    public DiscreteTransform3 withTransformation(DiscreteTransform3 transform) {
        return new DiscreteTransform3(transform.getMatrix().mul(getMatrix()));
    }

    /**
     * Returns a new transform representing a translation.
     *
     * @param vector The translation vector
     * @return The new translation transform
     */
    public static DiscreteTransform3 fromTranslation(Vector3i vector) {
        return fromTranslation(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Returns a new transform representing a translation.
     *
     * @param x The x coordinate of the translation
     * @param y The y coordinate of the translation
     * @param z The z coordinate of the translation
     * @return The new translation transform
     */
    public static DiscreteTransform3 fromTranslation(int x, int y, int z) {
        return new DiscreteTransform3(Matrix4d.createTranslation(x, y, z));
    }

    /**
     * Returns a new transform representing a scaling.
     *
     * @param a The scale factor
     * @return The new scale transform
     */
    public static DiscreteTransform3 fromScale(int a) {
        return fromScale(a, a, a);
    }

    /**
     * Returns a new transform representing a scaling on each axis.
     *
     * @param vector The scale vector
     * @return The new scale transform
     */
    public static DiscreteTransform3 fromScale(Vector3i vector) {
        return fromScale(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Returns a new transform representing a scaling on each axis.
     *
     * @param x The scale factor on x
     * @param y The scale factor on y
     * @param z The scale factor on z
     * @return The new scale transform
     */
    public static DiscreteTransform3 fromScale(int x, int y, int z) {
        Preconditions.checkArgument(x > 0, "x <= 0");
        Preconditions.checkArgument(y > 0, "y <= 0");
        Preconditions.checkArgument(z > 0, "z <= 0");
        return new DiscreteTransform3(Matrix4d.createScaling(x, y, z, 1));
    }

    /**
     * Returns a new transform representing a rotation around an axis.
     * The rotation is given is quarter turns. The actual rotation
     * is {@code quarterTurns * 90}.
     *
     * @param quarterTurns The number of quarter turns in this rotation
     * @param axis The axis to rotate around
     * @return The new rotation transform
     */
    public static DiscreteTransform3 fromRotation(int quarterTurns, Axis axis) {
        return new DiscreteTransform3(Matrix4d.createRotation(Quaterniond.fromAngleDegAxis(quarterTurns * 90, axis.toVector3d())));
    }

}
