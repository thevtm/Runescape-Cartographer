package com.TheVTM.Cartographer.Preprocessor;

import com.TheVTM.Cartographer.Data.CollisionData;
import com.TheVTM.Cartographer.Data.Vec2i;
import com.TheVTM.Cartographer.Visualisations;
import com.runemate.game.api.hybrid.region.Region;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by VTM on 3/5/2016.
 */
public class CornersTest extends TestCase {

  /* CONSTANTS */
  final String SOLID_SQUARE_PATH = "/data/corners/RS3 0 0 0.png";
  final String SOLID_L_PATH = "/data/corners/RS3 1 1 1.png";
  final String SOLID_DIAGONAL_PATH = "/data/corners/RS3 2 2 2.png";
  final String SOLID_MIX_PATH = "/data/corners/RS3 3 3 3.png";
  final String SOLID_CAVES_PATH = "/data/corners/RS3 4 4 4.png";

  final String SQUARE_PATH = "/data/corners/OSRS 0 0 0.png";
  final String LINES_PATH = "/data/corners/OSRS 1 1 1.png";

  final String OSRS_WIZARDS_TOWER_0_PATH = "/data/OSRS 3064 3112 0.png";
  final String OSRS_1_PATH = "/data/OSRS 2944 3312 0.png";

//    final String CORNER_DETECTION_JSON = "/data/corners/CornerDetection.json";

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
//    private Corners.CornerDetector[] cornerDetectors;

    /* SET UP */

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

    // Load solid Diagonal collision data
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

    // Load OSRS 1 collision data
    File OSRSDataFile = new File(getClass().getResource(OSRS_1_PATH).toURI());
    OSRS1 = CollisionData.fromFile(OSRSDataFile);

        /*// Load Corner Detection sample
        File cornerDetectionFile = new File(getClass().getResource(CORNER_DETECTION_JSON).toURI());
        cornerDetectors = Corners.loadCornerDetectorsJSON(new FileReader(cornerDetectionFile));*/

  }

    /* TESTS */

  @Test
  public void testIsBlocked() throws Exception {
    assertEquals("Primary objects are impassible.", CollisionData.isBlocked(Region.CollisionFlags.PRIMARY_OBJECT), true);
    assertEquals("Primary objects are impassible.", CollisionData.isBlocked(Region.CollisionFlags.IMPASSIBLE_PRIMARY_OBJECT), true);
    assertEquals("Floor objects are impassible.", CollisionData.isBlocked(Region.CollisionFlags.FLOOR_OBJECT), true);
    assertEquals("Lowered elevations are impassible.", CollisionData.isBlocked(Region.CollisionFlags.FLOOR_OBJECT), true);
    assertEquals("0x000000 should be passable.", CollisionData.isBlocked(0x000000), false);

  }

  @Test
  public void testFindCornersSolidSquare() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(2, 2), new Vec2i(2, 6), new Vec2i(6, 2), new Vec2i(6, 6)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersArray = Corners.findCorners(solidSquare);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersArray));

    // Save corners image
    Image image = Visualisations.drawCornersMap5(solidSquare, cornersArray);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindCornersSolidSquare.png"));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);

  }

  @Test
  public void testFindCornersSolidL() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(2, 2), new Vec2i(4, 2), new Vec2i(2, 6), new Vec2i(6, 4), new Vec2i(6, 6)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersArray = Corners.findCorners(solidL);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersArray));

    // Save corners image
    Image image = Visualisations.drawCornersMap5(solidL, cornersArray);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindCornersSolidL.png"));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);
  }

  @Test
  public void testFindCornersSolidDiagonal() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(2, 3), new Vec2i(3, 2), new Vec2i(8, 9), new Vec2i(9, 8),
        new Vec2i(12, 8), new Vec2i(13, 9), new Vec2i(18, 2), new Vec2i(19, 3), new Vec2i(22, 3),
        new Vec2i(22, 8), new Vec2i(23, 2), new Vec2i(23, 9), new Vec2i(28, 2), new Vec2i(28, 9),
        new Vec2i(29, 3), new Vec2i(29, 8)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersArray = Corners.findCorners(solidDiagonal);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersArray));

    // Save corners image
    Image image = Visualisations.drawCornersMap5(solidDiagonal, cornersArray);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindCornersSolidDiagonal.png"));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);
  }

  @Test
  public void testFindCornersSolidMix() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {
        // 0, 0 - Box
        new Vec2i(2, 2), new Vec2i(8, 2), new Vec2i(2, 7), new Vec2i(8, 7),

        // 1, 0
        new Vec2i(9, 2), new Vec2i(10, 2), new Vec2i(9, 5), new Vec2i(13, 7), new Vec2i(14, 7), new Vec2i(14, 4),

        // 2, 0
        new Vec2i(15, 2), new Vec2i(15, 3), new Vec2i(18, 2), new Vec2i(17, 7), new Vec2i(20, 7),
        new Vec2i(20, 6),

        // 3, 0
        new Vec2i(23, 2), new Vec2i(26, 2), new Vec2i(26, 3), new Vec2i(21, 6), new Vec2i(21, 7), new Vec2i(24, 7),

        // 0, 1
        new Vec2i(6, 8), new Vec2i(7, 8), new Vec2i(7, 11), new Vec2i(2, 10), new Vec2i(2, 13), new Vec2i(3, 13),

        // 1, 1
        new Vec2i(11, 8), new Vec2i(13, 8), new Vec2i(16, 11), new Vec2i(16, 13), new Vec2i(8, 11),
        new Vec2i(8, 13), new Vec2i(11, 16), new Vec2i(13, 16), new Vec2i(27, 17),

        // 2, 1
        new Vec2i(17, 8), new Vec2i(17, 9), new Vec2i(21, 8), new Vec2i(20, 12), new Vec2i(21, 12),
        new Vec2i(17, 13), new Vec2i(18, 13), new Vec2i(17, 17), new Vec2i(21, 16), new Vec2i(21, 17),

        // 3, 1
        new Vec2i(23, 8), new Vec2i(27, 8), new Vec2i(27, 9), new Vec2i(23, 12), new Vec2i(24, 12),
        new Vec2i(23, 16), new Vec2i(23, 17), new Vec2i(26, 13), new Vec2i(27, 13),

        // 0, 2
        new Vec2i(2, 17), new Vec2i(4, 17), new Vec2i(2, 19), new Vec2i(7, 22), new Vec2i(5, 24), new Vec2i(7, 24),

        // 1, 2
        new Vec2i(11, 17), new Vec2i(13, 17), new Vec2i(13, 19), new Vec2i(8, 22), new Vec2i(8, 24), new Vec2i(10, 24),

        // 2, 2
        new Vec2i(14, 19), new Vec2i(16, 17), new Vec2i(17, 17), new Vec2i(14, 20), new Vec2i(21, 21),
        new Vec2i(21, 22), new Vec2i(19, 24), new Vec2i(18, 24),

        // 3, 2
        new Vec2i(22, 18), new Vec2i(26, 18), new Vec2i(22, 20), new Vec2i(25, 23), new Vec2i(29, 21), new Vec2i(29, 23),

        // 0, 3
        new Vec2i(2, 25), new Vec2i(7, 25), new Vec2i(2, 27), new Vec2i(7, 26), new Vec2i(2, 29), new Vec2i(7, 28),
        new Vec2i(2, 30), new Vec2i(7, 30)

    };

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(Corners.findCorners(solidMix)));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

//        assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);
  }

  @Test
  public void testFindCornersCaves() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {

        // Margin corners
        new Vec2i(2, 2), new Vec2i(33, 2), new Vec2i(2, 17), new Vec2i(33, 17),

        // Big square rooms
        new Vec2i(2, 5), new Vec2i(4, 5),
        new Vec2i(9, 2), new Vec2i(9, 4),
        new Vec2i(31, 5), new Vec2i(33, 5),
        new Vec2i(24, 15), new Vec2i(24, 17),

        // Small square rooms
        new Vec2i(2, 9), new Vec2i(4, 9),
        new Vec2i(2, 11), new Vec2i(4, 11),
        new Vec2i(31, 9), new Vec2i(33, 9),
        new Vec2i(31, 11), new Vec2i(33, 11),
        new Vec2i(23, 2), new Vec2i(23, 4),
        new Vec2i(27, 2), new Vec2i(27, 4),
        new Vec2i(27, 15), new Vec2i(27, 17),
        new Vec2i(31, 15), new Vec2i(31, 17),

        // T
        new Vec2i(7, 9), new Vec2i(10, 9),
        new Vec2i(13, 2), new Vec2i(13, 5),
        new Vec2i(16, 14), new Vec2i(16, 17),
        new Vec2i(18, 6), new Vec2i(21, 6),

        // Cross
        new Vec2i(7, 13), new Vec2i(10, 13),
        new Vec2i(18, 10), new Vec2i(21, 10),
        new Vec2i(20, 14), new Vec2i(20, 17),
        new Vec2i(13, 11)


    };

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(Corners.findCorners(solidCaves)));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

//        assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);
  }

  @Test
  public void testFindCornersSquare() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(2, 2), new Vec2i(2, 6), new Vec2i(6, 2), new Vec2i(6, 6)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersList = Corners.findCorners(square);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersList));

    // Save corners image
    Image image = Visualisations.drawCornersMap5(square, cornersList);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindCornersSquare.png"));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);

  }

  @Test
  public void testFindCornersLines() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(2, 2), new Vec2i(2, 6), new Vec2i(6, 2), new Vec2i(6, 6)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersList = Corners.findCorners(lines);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersList));

    // Save corners image
    Image image = Visualisations.drawCornersMap5(lines, cornersList);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindCornersLines.png"));

    // Corners Surplus
    HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
    cornersSurplus.removeAll(EXPECTED_CORNERS_SET);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);
    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);

  }

  @Test
  public void testFindOSRS1() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(43, 42), new Vec2i(48, 55)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersList = Corners.findCorners(OSRS1);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersList));

    // Save corners image
//        Image image = Visualisations.CreateCornersMask(cornersList, 104, 104);
    Image image = Visualisations.drawCornersMap5(OSRS1, cornersList);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindOSRS1.png"));

    // Corners Surplus
//        HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
//        cornersSurplus.removeAll(EXPECTED_CORNERS_SET);
//
//        assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);

  }

  @Test
  public void testFindOSRSWizardsTower0() throws Exception {
    // Constants
    final Vec2i EXPECTED_CORNERS[] = {new Vec2i(43, 42), new Vec2i(48, 55)};

    final HashSet<Vec2i> EXPECTED_CORNERS_SET = new HashSet<>(Arrays.asList(EXPECTED_CORNERS));
    Vec2i[] cornersList = Corners.findCorners(OSRSWizardsTower0);
    final HashSet<Vec2i> CORNERS = new HashSet<>(Arrays.asList(cornersList));

    // Save corners image
//        Image image = Visualisations.CreateCornersMask(cornersList, 104, 104);
    Image image = Visualisations.drawCornersMap5(OSRSWizardsTower0, cornersList);
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    ImageIO.write(bImage, "png", new File("testFindOSRSWizardsTower0.png"));

    // Corners Surplus
//        HashSet<Vec2i> cornersSurplus = new HashSet<>(CORNERS);
//        cornersSurplus.removeAll(EXPECTED_CORNERS_SET);
//
//        assertEquals("Has no surplus corners.", new HashSet<Vec2i>(), cornersSurplus);

    // Corners Missing
    HashSet<Vec2i> cornersMissing = new HashSet<>(EXPECTED_CORNERS_SET);
    cornersMissing.removeAll(CORNERS);

    assertEquals("Has no missing corners.", new HashSet<Vec2i>(), cornersMissing);

  }

}