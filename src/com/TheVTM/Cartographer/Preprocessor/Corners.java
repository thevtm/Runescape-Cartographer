package com.TheVTM.Cartographer.Preprocessor;

import com.TheVTM.Cartographer.Data.CollisionData;
import com.TheVTM.Cartographer.Data.Vec2i;
import com.TheVTM.Cartographer.Pathfinder;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by VTM on 29/4/2016.
 */
public class Corners {

    /* LOGGER */
    private static final Logger LOGGER = Logger.getLogger(Corners.class.getName());


    public static Vec2i[] findCorners(CollisionData collisionData) {

        int[][] cf = collisionData.collisionFlags;
        int width = collisionData.width;
        int height = collisionData.height;

        Set<Vec2i> corners = new HashSet<>();

        for (int x = 2; x < (width - 2); x++) {
            for (int y = 2; y < (height - 2); y++) {

                // Skip if solid
                if (CollisionData.isBlocked(cf[x][y])) {
                    continue;
                }

                final Vec2i v = new Vec2i(x, y);

                /* RUNESCAPE CARDINAL SYSTEM */
                // SW .S SE
                // .W .. .E
                // NW .N NE
                // N > S
                // W < E

                final int cfN  = cf[x    ][y + 1];
                final int cfS  = cf[x    ][y - 1];
                final int cfW  = cf[x - 1][y    ];
                final int cfE  = cf[x + 1][y    ];
                final int cfC  = cf[x    ][y    ];
                final int cfNW = cf[x - 1][y + 1];
                final int cfNE = cf[x + 1][y + 1];
                final int cfSW = cf[x - 1][y - 1];
                final int cfSE = cf[x + 1][y - 1];

                final boolean canTraverseN = Pathfinder.canTraverse(collisionData, v, CollisionData.NORTH);
                final boolean canTraverseS = Pathfinder.canTraverse(collisionData, v, CollisionData.SOUTH);
                final boolean canTraverseW = Pathfinder.canTraverse(collisionData, v, CollisionData.WEST);
                final boolean canTraverseE = Pathfinder.canTraverse(collisionData, v, CollisionData.EAST);
                final boolean canTraverseNW = Pathfinder.canTraverse(collisionData, v, CollisionData.NORTH_WEST);
                final boolean canTraverseNE = Pathfinder.canTraverse(collisionData, v, CollisionData.NORTH_EAST);
                final boolean canTraverseSW = Pathfinder.canTraverse(collisionData, v, CollisionData.SOUTH_WEST);
                final boolean canTraverseSE = Pathfinder.canTraverse(collisionData, v, CollisionData.SOUTH_EAST);

                final boolean northTileCanTraverseN = Pathfinder.canTraverse(collisionData, new Vec2i(v.x, v.y + 1), CollisionData.NORTH);
                final boolean southTileCanTraverseS = Pathfinder.canTraverse(collisionData, new Vec2i(v.x, v.y - 1), CollisionData.SOUTH);
                final boolean westTileCanTraverseW = Pathfinder.canTraverse(collisionData, new Vec2i(v.x - 1, v.y), CollisionData.WEST);
                final boolean eastTileCanTraverseE = Pathfinder.canTraverse(collisionData, new Vec2i(v.x + 1, v.y), CollisionData.EAST);

                /******************/
                /* SQUARE CORNERS */
                /******************/

                // North West Corner
                // Can traverse S and E but can't SE
                // S tile can traverse S
                // E tile can traverse E
                // . D D
                // . @ D
                // C . .
                if (canTraverseS && canTraverseE && !canTraverseSE && southTileCanTraverseS && eastTileCanTraverseE) {
                    // Check if is a diagonal
                    if (CollisionData.isBlocked(cf[x + 2][y - 2])) { // Possibly diagonal
                        if (CollisionData.isBlocked(cf[x + 1][y - 2]) || CollisionData.isBlocked(cf[x + 2][y - 1])) {
                            corners.add(new Vec2i(x, y));
                        }

                    } else { // Not diagonal
                        corners.add(new Vec2i(x, y));
                    }
                }

                // North East Corner
                // Can traverse S and W but can't SW
                // S tile can traverse S
                // W tile can traverse W
                // D D .
                // D @ .
                // . . C
                else if(canTraverseS && canTraverseW && !canTraverseSW && southTileCanTraverseS && westTileCanTraverseW) {
                    // Check if is a diagonal
                    if (CollisionData.isBlocked(cf[x - 2][y - 2])) { // Possibly diagonal
                        if (CollisionData.isBlocked(cf[x - 2][y - 1]) || CollisionData.isBlocked(cf[x - 1][y - 2])) {
                            corners.add(new Vec2i(x, y));
                        }

                    } else { // Not diagonal
                        corners.add(new Vec2i(x, y));
                    }
                }

                // South West Corner
                // Can traverse N and E but can't NE
                // N tile can traverse N
                // E tile can traverse E
                // C . v
                // . @ D
                // v D D
                else if(canTraverseN && canTraverseE && !canTraverseNE && northTileCanTraverseN && eastTileCanTraverseE) {
                    // Check if is a diagonal
                    if (CollisionData.isBlocked(cf[x + 2][y + 2])) { // Possibly diagonal
                        if (CollisionData.isBlocked(cf[x + 1][y + 2]) || CollisionData.isBlocked(cf[x + 2][y + 1])) {
                            corners.add(new Vec2i(x, y));
                        }

                    } else { // Not diagonal
                        corners.add(new Vec2i(x, y));
                    }
                }

                // South East Corner
                // Can traverse N and W but can't NW
                // N tile can traverse N
                // W tile can traverse W
                // . . C
                // D @ .
                // D D .
                else if((canTraverseN && canTraverseW) && !canTraverseNW && northTileCanTraverseN && westTileCanTraverseW) {
                    // Check if is a diagonal
                    if (CollisionData.isBlocked(cf[x - 2][y + 2])) { // Possibly diagonal
                        if (CollisionData.isBlocked(cf[x - 2][y + 1]) || CollisionData.isBlocked(cf[x - 1][y + 2])) {
                            corners.add(new Vec2i(x, y));
                        }

                    } else { // Not diagonal
                        corners.add(new Vec2i(x, y));
                    }
                }


                /********************/
                /* DIAGONAL CORNERS */
                /********************/

                // North West diagonal corner
                // @ * *
                // . @ *
                // . C .
                if (canTraverseW && canTraverseE && canTraverseSW && !canTraverseS &&
                        (CollisionData.isBlocked(cf[x - 1][y - 2]) || CollisionData.isBlocked(cfSW, CollisionData.SOUTH))) {
                    corners.add(new Vec2i(x, y));

                }

                // North East diagonal corner
                // * * @
                // * @ .
                // . C .
                if (canTraverseW && canTraverseE && canTraverseSE && !canTraverseS &&
                        (CollisionData.isBlocked(cf[x + 1][y - 2]) || CollisionData.isBlocked(cfSE, CollisionData.SOUTH))) {
                    corners.add(new Vec2i(x, y));
                }

                // South West diagonal corner
                // . C .
                // . @ *
                // @ * *
                if (canTraverseW && canTraverseE && canTraverseNW && !canTraverseN &&
                        (CollisionData.isBlocked(cf[x - 1][y + 2]) || CollisionData.isBlocked(cfNW, CollisionData.NORTH))) {
                    corners.add(new Vec2i(x, y));
                }

                // South East diagonal corner
                // . C .
                // * @ .
                // * * @
                if (canTraverseW && canTraverseE && canTraverseNE && !canTraverseN &&
                        (CollisionData.isBlocked(cf[x + 1][y + 2]) || CollisionData.isBlocked(cfNE, CollisionData.NORTH))) {
                    corners.add(new Vec2i(x, y));
                }

                // West North diagonal corner
                // . * *
                // C @ *
                // . . @
                if(canTraverseN && canTraverseS && canTraverseNE && !canTraverseE &&
                        (CollisionData.isBlocked(cf[x + 2][y + 1]) || CollisionData.isBlocked(cfNE, CollisionData.EAST))) {
                    corners.add(new Vec2i(x, y));
                }

                // West South diagonal corner
                // . . @
                // C @ *
                // . * *
                if(canTraverseN && canTraverseS && canTraverseSE && !canTraverseE &&
                        (CollisionData.isBlocked(cf[x + 2][y - 1]) || CollisionData.isBlocked(cfSE, CollisionData.EAST))) {
                    corners.add(new Vec2i(x, y));
                }

                // East North diagonal corner
                // * * .
                // * @ C
                // @ . .
                if(canTraverseN && canTraverseS && canTraverseNW && !canTraverseW &&
                        (CollisionData.isBlocked(cf[x - 2][y + 1]) || CollisionData.isBlocked(cfNW, CollisionData.WEST))) {
                    corners.add(new Vec2i(x, y));
                }

                // East South diagonal corner
                // @ . .
                // * @ C
                // * * .
                // West tile must be solid
                if(canTraverseN && canTraverseSW && canTraverseS && !canTraverseW &&
                        (CollisionData.isBlocked(cf[x - 2][y - 1]) || CollisionData.isBlocked(cfSW, CollisionData.WEST))) {
                    corners.add(new Vec2i(x, y));
                }


                /******************/
                /* CLOSED CORNERS */
                /******************/

                // North corner
                // @ . @
                // v C v
                if(!canTraverseSW && canTraverseS && !canTraverseSE && (canTraverseW || canTraverseE)) {
                    corners.add(new Vec2i(x, y));
                }

                // South corner
                // v C v
                // @ . @
                if(!canTraverseNW && canTraverseN && !canTraverseNE && (canTraverseW || canTraverseE)) {
                    corners.add(new Vec2i(x, y));
                }

                // West corner
                // v @
                // C .
                // v @
                if(!canTraverseNE && canTraverseE && !canTraverseSE && (canTraverseN || canTraverseS)) {
                    corners.add(new Vec2i(x, y));
                }

                // East corner
                // @ v
                // . C
                // @ v
                if(!canTraverseNW && canTraverseW && !canTraverseSW && (canTraverseN || canTraverseS)) {
                    corners.add(new Vec2i(x, y));
                }
            }
        }

        return corners.toArray(new Vec2i[corners.size()]);
    }

}
