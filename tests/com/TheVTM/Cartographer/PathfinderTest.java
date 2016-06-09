package com.TheVTM.Cartographer;

import com.TheVTM.Cartographer.Data.CollisionData;
import com.TheVTM.Cartographer.Data.Vec2i;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by VTM on 2/6/2016.
 */
public class PathfinderTest {

    /* CONSTANTS */
    final String SOLID_SQUARE_PATH = "/data/corners/RS3 0 0 0.png";
    final String SOLID_L_PATH = "/data/corners/RS3 1 1 1.png";
    final String SOLID_DIAGONAL_PATH = "/data/corners/RS3 2 2 2.png";
    final String OSRS_WIZARDS_TOWER_0_PATH = "/data/OSRS 3064 3112 0.png";

    /* FIELDS */
    private CollisionData solidSquare;
    private CollisionData solidL;
    private CollisionData solidDiagonal;
    private CollisionData OSRSWizardsTower0;

    @Before
    public void setUp() throws Exception {

        // Load solid square collision data
        File solidSquareDataFile = new File(getClass().getResource(SOLID_SQUARE_PATH).toURI());
        solidSquare = CollisionData.fromFile(solidSquareDataFile);

        // Load solid L collision data
        File solidLDataFile = new File(getClass().getResource(SOLID_L_PATH).toURI());
        solidL = CollisionData.fromFile(solidLDataFile);

        // Load solid Diagonal collision data
        File solidDiagonalDataFile = new File(getClass().getResource(SOLID_DIAGONAL_PATH).toURI());
        solidDiagonal = CollisionData.fromFile(solidDiagonalDataFile);

        // Load OSRS Wizards Tower collision data
        File OSRSWizardsTower0DataFile = new File(getClass().getResource(OSRS_WIZARDS_TOWER_0_PATH).toURI());
        OSRSWizardsTower0 = CollisionData.fromFile(OSRSWizardsTower0DataFile);
    }

    @Test
    public void canTraverse() throws Exception {
        // Solid Square
        assertTrue("Can traverse from 1,1 to NE on solid square map.",
            Pathfinder.canTraverse(solidSquare, new Vec2i(1, 1), CollisionData.NORTH_EAST));

        assertFalse("Can't traverse from 6,2 to NE on solid square map.",
            Pathfinder.canTraverse(solidSquare, new Vec2i(6, 2), CollisionData.NORTH_WEST));

        // Solid L
        assertFalse("Can't traverse from 2,6 to SE on solid L map.",
                Pathfinder.canTraverse(solidL, new Vec2i(2, 6), CollisionData.SOUTH_EAST));

        assertTrue("Can traverse from 4,4 to E on solid L map.",
                Pathfinder.canTraverse(solidL, new Vec2i(4, 4), CollisionData.EAST));

        assertFalse("Can't traverse from 4,4 to W on solid L map.",
                Pathfinder.canTraverse(solidL, new Vec2i(4, 4), CollisionData.WEST));

        // OSRS Wizards Tower
        assertFalse("Can't traverse from 44,55 to S on OSRS Wizards Tower map.",
                Pathfinder.canTraverse(OSRSWizardsTower0, new Vec2i(44, 55), CollisionData.SOUTH));

        assertTrue("Can traverse from 44,55 to N on OSRS Wizards Tower map.",
                Pathfinder.canTraverse(OSRSWizardsTower0, new Vec2i(44, 55), CollisionData.NORTH));

        assertFalse("Can't traverse from 52,48 to W on OSRS Wizards Tower map.",
                Pathfinder.canTraverse(OSRSWizardsTower0, new Vec2i(52, 48), CollisionData.WEST));

        assertTrue("Can traverse from 52,48 to E on OSRS Wizards Tower map.",
                Pathfinder.canTraverse(OSRSWizardsTower0, new Vec2i(52, 48), CollisionData.EAST));
    }

}