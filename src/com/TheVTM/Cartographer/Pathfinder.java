package com.TheVTM.Cartographer;

import com.TheVTM.Cartographer.Data.CollisionData;
import com.TheVTM.Cartographer.Data.Vec2i;

/**
 * Created by VTM on 31/5/2016.
 */
public class Pathfinder {

    public static boolean canTraverse(CollisionData collisionData, Vec2i origin, int target) {
        int[][] cf = collisionData.collisionFlags;

        final int x = origin.x;
        final int y = origin.y;

        final int cfN  = cf[x    ][y + 1];
        final int cfS  = cf[x    ][y - 1];
        final int cfW  = cf[x - 1][y    ];
        final int cfE  = cf[x + 1][y    ];
        final int cfC  = cf[x    ][y    ];
        final int cfNW = cf[x - 1][y + 1];
        final int cfNE = cf[x + 1][y + 1];
        final int cfSW = cf[x - 1][y - 1];
        final int cfSE = cf[x + 1][y - 1];

        switch (target)  {
            case CollisionData.NORTH:
                return !(CollisionData.isBlocked(cfN) || CollisionData.isBlocked(cfC, CollisionData.NORTH));

            case CollisionData.SOUTH:
                return !(CollisionData.isBlocked(cfS) || CollisionData.isBlocked(cfC, CollisionData.SOUTH));

            case CollisionData.WEST:
                return !(CollisionData.isBlocked(cfW) || CollisionData.isBlocked(cfC, CollisionData.WEST));

            case CollisionData.EAST:
                return !(CollisionData.isBlocked(cfE) || CollisionData.isBlocked(cfC, CollisionData.EAST));

            case CollisionData.NORTH_WEST:
                if (CollisionData.isBlocked(cfNW) || CollisionData.isBlocked(cfC, CollisionData.NORTH_WEST)) {
                    return false;

                } else if (canTraverse(collisionData, origin, CollisionData.NORTH)) { // Can traverse North
                    return canTraverse(collisionData, new Vec2i(origin.x, origin.y + 1), CollisionData.WEST);

                } else if (canTraverse(collisionData, origin, CollisionData.WEST)) { // Can traverse West
                    return canTraverse(collisionData, new Vec2i(origin.x - 1, origin.y), CollisionData.NORTH);

                } else {
                    return false;
                }

            case CollisionData.NORTH_EAST:
                if (CollisionData.isBlocked(cfNE) || CollisionData.isBlocked(cfC, CollisionData.NORTH_EAST)) {
                    return false;

                } else if (canTraverse(collisionData, origin, CollisionData.NORTH)) { // Can traverse North
                    return canTraverse(collisionData, new Vec2i(origin.x, origin.y + 1), CollisionData.EAST);

                } else if (canTraverse(collisionData, origin, CollisionData.EAST)) { // Can traverse East
                    return canTraverse(collisionData, new Vec2i(origin.x + 1, origin.y), CollisionData.NORTH);

                } else {
                    return false;
                }

            case CollisionData.SOUTH_WEST:
                if (CollisionData.isBlocked(cfSW) || CollisionData.isBlocked(cfC, CollisionData.SOUTH_WEST)) {
                    return false;

                } else if (canTraverse(collisionData, origin, CollisionData.SOUTH)) { // Can traverse South
                    return canTraverse(collisionData, new Vec2i(origin.x, origin.y - 1), CollisionData.WEST);

                } else if (canTraverse(collisionData, origin, CollisionData.WEST)) { // Can traverse West
                    return canTraverse(collisionData, new Vec2i(origin.x - 1, origin.y), CollisionData.SOUTH);

                } else {
                    return false;
                }

            case CollisionData.SOUTH_EAST:
                if (CollisionData.isBlocked(cfSE) || CollisionData.isBlocked(cfC, CollisionData.SOUTH_EAST)) {
                    return false;

                } else if (canTraverse(collisionData, origin, CollisionData.SOUTH)) { // Can traverse South
                    return canTraverse(collisionData, new Vec2i(origin.x, origin.y - 1), CollisionData.EAST);

                } else if (canTraverse(collisionData, origin, CollisionData.EAST)) { // Can traverse East
                    return canTraverse(collisionData, new Vec2i(origin.x + 1, origin.y), CollisionData.SOUTH);

                } else {
                    return false;
                }

            default:
                throw new RuntimeException(String.format("Target invalid: %d", target));
        }
    }
}
