package com.TheVTM.Cartographer.Data;

import com.sun.javafx.geom.Vec2d;

/**
 * Created by VTM on 23/5/2016.
 */
public class Vec2i {
    public final int x, y;

    public static Vec2i[] orderVertex(Vec2i a, Vec2i b) {
        boolean aXisSmaller = a.x < b.x;
        boolean aXisEquals = a.x == b.x;
        boolean aYisSmaller = a.y < b.y;

        if (aXisEquals) {
            if (aYisSmaller) {
                return new Vec2i[]{a, b};
            } else {
                return new Vec2i[]{b, a};
            }

        } else {
            if (aXisSmaller) {
                return new Vec2i[]{a, b};
            } else {
                return new Vec2i[]{b, a};
            }
        }
    }

    public static double distance(Vec2i a, Vec2i b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Vec2i other) {
        return distance(this, other);
    }

    @Override
    public String toString() {
        return "Vec2i{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec2i vec2i = (Vec2i) o;

        if (x != vec2i.x) return false;
        return y == vec2i.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 10000 * result + y;
        return result;
    }
}
