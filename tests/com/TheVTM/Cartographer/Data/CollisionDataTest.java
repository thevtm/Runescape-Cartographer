package com.TheVTM.Cartographer.Data;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.script.data.GameType;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by VTM on 28/4/2016.
 */
public class CollisionDataTest extends TestCase {

    /* CONSTANTS */
    final String COMPLETE_COLLISION_DATA_PATH = "/data/OSRS 3064 3112 0.png";

    /* FIELDS */
    private CollisionData completeCollisionData;

    /* SET UP */

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Load complete collision data
        File completeCollisionDataFile = new File(getClass().getResource(COMPLETE_COLLISION_DATA_PATH).toURI());
        completeCollisionData = CollisionData.fromFile(completeCollisionDataFile);

    }

    /* TESTS */

    @Test
    public void testGameType() throws Exception {
        // complete collision data
        assertTrue(String.format("%s, game type must be OSRS.", COMPLETE_COLLISION_DATA_PATH),
                completeCollisionData.gameType == GameType.OSRS);

        assertFalse(String.format("%s, game type must not be RS3.", COMPLETE_COLLISION_DATA_PATH),
                completeCollisionData.gameType.equals(GameType.RS3));


    }

    @Test
    public void testBaseCoordinates() throws Exception {
        // complete collision data
        Coordinate COMPLETE_COLLISION_DATA_EXPECTED_COORDINATES = new Coordinate(3064, 3112, 0);
        Coordinate COMPLETE_COLLISION_DATA_WRONG_COORDINATES = new Coordinate(666, 666, 6);

        assertTrue(String.format("%s, base coordinates must be %s.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_COORDINATES.toString()),
                completeCollisionData.base.equals(COMPLETE_COLLISION_DATA_EXPECTED_COORDINATES));

        assertFalse(String.format("%s, base coordinates must not be %s.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_WRONG_COORDINATES.toString()),
                completeCollisionData.base.equals(COMPLETE_COLLISION_DATA_WRONG_COORDINATES));

    }

    @Test
    public void testCollisionFlags() throws Exception {
        /* complete collision data */
        // 0, 0
        int COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_0 = 0xFFFFFF;
        int COMPLETE_COLLISION_DATA_WRONG_PIXEL_0    = 0x80C000;

        assertTrue(String.format("%s, 0,0 pixel must be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_0),
                completeCollisionData.collisionFlags[0][0] == COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_0);

        assertFalse(String.format("%s, 0,0 pixel must not be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_0),
                completeCollisionData.collisionFlags[0][0] == COMPLETE_COLLISION_DATA_WRONG_PIXEL_0);

        // 43, 42
        int COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_1 = 0x00C060;
        int COMPLETE_COLLISION_DATA_WRONG_PIXEL_1 = 0xF180C0;

        assertTrue(String.format("%s, 43, 42 pixel must be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_1),
                completeCollisionData.collisionFlags[43][42] == COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_1);

        assertFalse(String.format("%s, 43, 42 pixel must not be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_1),
                completeCollisionData.collisionFlags[43][42] == COMPLETE_COLLISION_DATA_WRONG_PIXEL_1);

        // 96, 96
        int COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_2 = 0x000000;
        int COMPLETE_COLLISION_DATA_WRONG_PIXEL_2    = 0x010101;

        assertTrue(String.format("%s, 43, 42 pixel must be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_2),
                completeCollisionData.collisionFlags[96][96] == COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_2);

        assertFalse(String.format("%s, 43, 42 pixel must not be %05x.",
                COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_PIXEL_2),
                completeCollisionData.collisionFlags[96][96] == COMPLETE_COLLISION_DATA_WRONG_PIXEL_2);
    }

    @Test
    public void testWidthAndHeight() throws Exception {
        /* complete collision data */
        final int COMPLETE_COLLISION_DATA_EXPECTED_WIDTH = 104;
        final int COMPLETE_COLLISION_DATA_WRONG_WIDTH = 1984;
        final int COMPLETE_COLLISION_DATA_EXPECTED_HEIGHT = 104;
        final int COMPLETE_COLLISION_DATA_WRONG_HEIGHT = 1004;

        assertTrue(String.format("%s, width should be %d.",
            COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_WIDTH),
            completeCollisionData.width == COMPLETE_COLLISION_DATA_EXPECTED_WIDTH);

        assertTrue(String.format("%s, width should not be %d.",
            COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_WRONG_WIDTH),
            completeCollisionData.width != COMPLETE_COLLISION_DATA_WRONG_WIDTH);

        assertTrue(String.format("%s, height should be %d.",
            COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_EXPECTED_HEIGHT),
            completeCollisionData.width == COMPLETE_COLLISION_DATA_EXPECTED_HEIGHT);

        assertTrue(String.format("%s, height should not be %d.",
            COMPLETE_COLLISION_DATA_PATH, COMPLETE_COLLISION_DATA_WRONG_HEIGHT),
            completeCollisionData.width != COMPLETE_COLLISION_DATA_WRONG_HEIGHT);

    }
}