package com.TheVTM.Cartographer.Preprocessor;

import com.TheVTM.Cartographer.Common;
import com.TheVTM.Cartographer.Data.CollisionData;
import com.TheVTM.Cartographer.Data.Graph;
import com.TheVTM.Cartographer.Data.Vec2i;
import com.TheVTM.Cartographer.Pathfinder;
import com.TheVTM.Cartographer.Visualisations;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by VTM on 1/6/2016.
 */
public class VisibilityGraphTest {

  /* CONSTANTS */
  final String SOLID_SQUARE_PATH = "/data/corners/RS3 0 0 0.png";
  final String SOLID_L_PATH = "/data/corners/RS3 1 1 1.png";
  final String SOLID_DIAGONAL_PATH = "/data/corners/RS3 2 2 2.png";
  final String SOLID_MIX_PATH = "/data/corners/RS3 3 3 3.png";
  final String SOLID_CAVES_PATH = "/data/corners/RS3 4 4 4.png";

  final String SQUARE_PATH = "/data/corners/OSRS 0 0 0.png";
  final String LINES_PATH = "/data/corners/OSRS 1 1 1.png";

  final String OSRS_WIZARDS_TOWER_0_PATH = "/data/OSRS 3064 3112 0.png";
  final String OSRS_F2P_0_PATH = "/data/OSRS 2865 3057 0.png";
  final String OSRS_1_PATH = "/data/OSRS 2944 3312 0.png";

  /* FIELDS */
  private CollisionData solidSquare;
  private CollisionData solidL;
  private CollisionData solidDiagonal;
  private CollisionData solidMix;
  private CollisionData solidCaves;

  private CollisionData square;
  private CollisionData lines;

  private CollisionData OSRSWizardsTower0;
  private CollisionData OSRS1;
  private CollisionData OSRSF2P0;

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

    // Load solid Mix collision data
    File solidMixDataFile = new File(getClass().getResource(SOLID_MIX_PATH).toURI());
    solidMix = CollisionData.fromFile(solidMixDataFile);

    // Load solid Caves collision data
    File solidCavesDataFile = new File(getClass().getResource(SOLID_CAVES_PATH).toURI());
    solidCaves = CollisionData.fromFile(solidCavesDataFile);

    // Load square collision data
    File squareDataFile = new File(getClass().getResource(SQUARE_PATH).toURI());
    square = CollisionData.fromFile(squareDataFile);

    // Load lines collision data
    File linesDataFile = new File(getClass().getResource(LINES_PATH).toURI());
    lines = CollisionData.fromFile(linesDataFile);

    // Load OSRS Wizards Tower collision data
    File OSRSWizardsTower0DataFile = new File(getClass().getResource(OSRS_WIZARDS_TOWER_0_PATH).toURI());
    OSRSWizardsTower0 = CollisionData.fromFile(OSRSWizardsTower0DataFile);

    // Load OSRS Wizards Tower collision data
    File OSRSF2P0DataFile = new File(getClass().getResource(OSRS_F2P_0_PATH).toURI());
    OSRSF2P0 = CollisionData.fromFile(OSRSF2P0DataFile);

    // Load OSRS 1 collision data
    File OSRSDataFile = new File(getClass().getResource(OSRS_1_PATH).toURI());
    OSRS1 = CollisionData.fromFile(OSRSDataFile);

  }

  @Test
  public void testsCreateVisibilityGraphSolidSquare() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(solidSquare, Corners.findCorners(solidSquare));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(solidSquare, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraph.png"));

  }

  @Test
  public void testsCreateVisibilityGraphSolidL() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(solidL, Corners.findCorners(solidL));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(solidL, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphSolidL.png"));

  }

  @Test
  public void testsCreateVisibilityGraphSolidDiagonal() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(solidDiagonal, Corners.findCorners(solidDiagonal));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(solidDiagonal, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphSolidDiagonal.png"));

  }

  @Test
  public void testsCreateVisibilityGraphSolidMix() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(solidMix, Corners.findCorners(solidMix));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(solidMix, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphSolidMix.png"));

  }

  @Test
  public void testsCreateVisibilityGraphSolidCaves() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(solidCaves, Corners.findCorners(solidCaves));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(solidCaves, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphSolidCaves.png"));

  }

  @Test
  public void testsCreateVisibilityGraphOSRSWizardsTower0() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(OSRSWizardsTower0, Corners.findCorners(OSRSWizardsTower0));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(OSRSWizardsTower0, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphOSRSWizardsTower0.png"));

    // Log
    System.out.println(String.format("Vertices: %d | Edges: %d", visibilityGraph.vertices.length, visibilityGraph.edges.length));

  }

  @Test
  public void testsCreateVisibilityGraphOSRS1() throws Exception {
    Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(OSRS1, Corners.findCorners(OSRS1));

    // Save corners image
    Image image = Visualisations.drawGraphMap5(OSRS1, visibilityGraph);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphOSRS1.png"));

    // Log
    System.out.println(String.format("Vertices: %d | Edges: %d", visibilityGraph.vertices.length, visibilityGraph.edges.length));

  }

//    @Test
//    public void testsCreateVisibilityGraphOSRSF2P0() throws Exception {
//        Graph visibilityGraph = VisibilityGraph.createVisibilityGraph(OSRSF2P0, Corners.findCorners(OSRSF2P0));
//
//        // Save corners image
//        Image image = Visualisations.drawGraphMap5(OSRSF2P0, visibilityGraph);
//        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
//        ImageIO.write(bImage, "png", new File("testsCreateVisibilityGraphOSRSF2P0.png"));
//
//    }

  @Test
  public void testsCanTraversePath() {

    // Solid Square
    assertFalse("Can't traverse from 1,1 to 7,7 in the solid square map.",
        VisibilityGraph.canTraversePath(solidSquare,
            Common.GenerateBresenhamLine(new Vec2i(1, 1), new Vec2i(7, 7))));

    assertTrue("Can traverse from 1,1 to 2,7 in the solid square map.",
        VisibilityGraph.canTraversePath(solidSquare,
            Common.GenerateBresenhamLine(new Vec2i(1, 1), new Vec2i(2, 7))));

    assertFalse("Can't traverse from 1,3 to 7,3 in the solid square map.",
        VisibilityGraph.canTraversePath(solidSquare,
            Common.GenerateBresenhamLine(new Vec2i(1, 3), new Vec2i(7, 3))));

    // Load OSRS Wizards Tower collision data
    assertFalse("Can't traverse from 35,48 to 36,54 in the OSRS Wizards Tower map.",
        VisibilityGraph.canTraversePath(OSRSWizardsTower0,
            Common.GenerateBresenhamLine(new Vec2i(35, 48), new Vec2i(36, 54))));

    assertFalse("Can't traverse from 48,48 to 54,35 in the OSRS Wizards Tower map.",
        VisibilityGraph.canTraversePath(OSRSWizardsTower0,
            Common.GenerateBresenhamLine(new Vec2i(48, 48), new Vec2i(54, 35))));
  }

  @Test
  public void testsGetDirection() throws Exception {

    assertEquals("North case.", CollisionData.NORTH, VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(0, 1)));
    assertEquals("North case.", CollisionData.NORTH, VisibilityGraph.getDirection(new Vec2i(1, 1), new Vec2i(1, 2)));
    assertEquals("South case.", CollisionData.SOUTH, VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(0, -1)));
    assertEquals("West case.", CollisionData.WEST, VisibilityGraph.getDirection(new Vec2i(10, 1), new Vec2i(9, 1)));
    assertEquals("East case.", CollisionData.EAST, VisibilityGraph.getDirection(new Vec2i(16, 1), new Vec2i(17, 1)));
    assertEquals("South West case.", CollisionData.SOUTH_WEST, VisibilityGraph.getDirection(new Vec2i(100, 100), new Vec2i(100 - 1, 100 - 1)));
    assertEquals("North West case.", CollisionData.NORTH_WEST, VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(-1, +1)));
    assertEquals("South East case.", CollisionData.SOUTH_EAST, VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(+1, -1)));
    assertEquals("North East case.", CollisionData.NORTH_EAST, VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(+1, +1)));

    // False cases
    assertFalse("North false case.", CollisionData.NORTH == VisibilityGraph.getDirection(new Vec2i(0, 0), new Vec2i(1, -1)));
    assertFalse("South West false case.", CollisionData.SOUTH_WEST == VisibilityGraph.getDirection(new Vec2i(66, 30), new Vec2i(66 + 1, 30)));

  }

}