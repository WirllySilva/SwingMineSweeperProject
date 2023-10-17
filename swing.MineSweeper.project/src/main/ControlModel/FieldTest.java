package Main.ControlModel;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Exception.TriggeredException;

public class FieldTest {

    private Field field;

    @BeforeEach
    void startField() {
        field = new Field(3, 3);
    }

    @Test
    void realAdjacentSquareFirstDistance() {
        Field leftAdjacentSquare = new Field(3, 2);
        boolean resultLeftAdjacentSquare = field.addAdjacentSquare(leftAdjacentSquare);
        assertTrue(resultLeftAdjacentSquare);

    }

    @Test
    void realAdjacentSquareSecondDistance() {
        Field rightAdjacentSquare = new Field(3, 2);
        boolean resultRightAdjacentSquare = field.addAdjacentSquare(rightAdjacentSquare);
        assertTrue(resultRightAdjacentSquare);
    }

    @Test
    void realAdjacentSquareThirdDistance() {
        Field downAdjacentSquare = new Field(4, 3);
        boolean resultDownAdjacentSquare = field.addAdjacentSquare(downAdjacentSquare);
        assertTrue(resultDownAdjacentSquare);
    }

    @Test
    void realAdjacentSquareFourthDistance() {
        Field upAdjacentSquare = new Field(2, 3);
        boolean resultUpAdjacentSquare = field.addAdjacentSquare(upAdjacentSquare);
        assertTrue(resultUpAdjacentSquare);
    }

    @Test
    void realdAdjacentSquareFifthDistance() {
        Field leftUpDiagonallyAdjacentSquare = new Field(2, 4);
        boolean leftUpdiagonallyAdjacentSquareResult = field.addAdjacentSquare(leftUpDiagonallyAdjacentSquare);
        assertTrue(leftUpdiagonallyAdjacentSquareResult);
    }

    @Test
    void markedAttributeDefaultValueTest() {
        assertFalse(field.isMarked());
    }

    @Test
    void markingToggleTest() {
        field.markingToggle();
        assertTrue(field.isMarked());
    }

    @Test
    void markingToggleTestTwoCalls() {
        field.markingToggle();
        field.markingToggle();
        assertFalse(field.isMarked());
    }

    @Test
    void notMinedNotMarkedTest() {
        assertTrue(field.openField());
    }

    @Test
    void openNotMinedNotmarkedTest() {
        field.markingToggle();
        assertFalse(field.openField());
    }

    @Test
    void openMinedAndMarkedFieldTest() {
        field.layMine();
        field.markingToggle();
        assertFalse(field.openField());
    }

    @Test
    void openMinedNotMarkedTest() {
        field.layMine();
        assertThrows(TriggeredException.class, () -> {
            field.openField();
        });
    }

    @Test
    void openFieldAndTheirAdjacentSquares() {

        Field field1 = new Field(1, 1);

        Field field2 = new Field(2, 2);
        field2.addAdjacentSquare(field1);

        field.addAdjacentSquare(field2);

        field.openField();

        assertTrue(field2.isOpened() && field1.isOpened());
    }

    @Test
    void openFieldAndTheirAdjacentSquares2() {

        Field field1 = new Field(1, 1);
        Field field2 = new Field(1, 1);
        field2.layMine();

        Field field3 = new Field(2, 2);
        field3.addAdjacentSquare(field1);
        field3.addAdjacentSquare(field2);

        field.addAdjacentSquare(field3);
        field.openField();

        assertTrue(field3.isOpened() && !field1.isOpened());

    }

    @Test
    void goalHasBeenMetTest_RevealedUnmined() {
        Field field1 = new Field(3, 3);
        field1.openField();
        assertTrue(field1.goalHasBeenMet());

    }

    @Test
    void goalHasBeenMetTest_SafeguardedField() {
        Field fieldTest = new Field(1, 1);
        fieldTest.markingToggle();
        assertTrue(fieldTest.goalHasBeenMet());
    }

    @Test
    void goalHasBeenMetTest_MinedField() {
        Field fieldTest = new Field(1, 1);
        fieldTest.layMine();
        assertFalse(fieldTest.goalHasBeenMet());
    }

    @Test
    void adjacentSquareMinedTest() {
        Field fieldTest1 = new Field(3, 3);
        Field fieldTest2 = new Field(3, 4);
        fieldTest2.layMine();
        fieldTest1.addAdjacentSquare(fieldTest2);

        assertEquals(1, fieldTest1.minesOnAdjacentSquare());
    }

    @Test
    void adjacentSquareNotmined() {
        Field fieldtest1 = new Field(3, 3);
        Field fieldtest2 = new Field(3, 4);
        fieldtest1.addAdjacentSquare(fieldtest2);

        assertEquals(0, fieldtest1.minesOnAdjacentSquare());
    }

    @Test
    void restartGameTest_Mined() {
        Field fieldTest = new Field(1,1);
        fieldTest.layMine();
        fieldTest.restartGame();
        assertFalse(fieldTest.isMined());
    }

    @Test
    void restartGameTest_Marked() {
        Field fieldTest = new Field(1,1);
        fieldTest.markingToggle();
        fieldTest.restartGame();
        assertFalse(fieldTest.isMarked());
    }

    @Test
    void restartGameTest_Opened() {
        Field fieldTest = new Field(1,1);
        fieldTest.openField();
        fieldTest.restartGame();
        assertFalse(fieldTest.isOpened());
    }

    @Test
    void toStringTest_FieldMarked() {
        Field fieldTest = new Field(3, 3);
        fieldTest.markingToggle();
        assertEquals("X", fieldTest.toString());
    }

    @Test
    void toStringTest_FieldOpened() {
        Field fieldTest = new Field(1,1);
        fieldTest.openField();
        assertEquals(" ", fieldTest.toString());
    }

    @Test
    void toStringTest_FieldMined() {
        Field fieldTest = new Field(1,1);
        fieldTest.layMine();
        assertThrows(TriggeredException.class, () -> {
            fieldTest.openField();
        });    
        assertEquals("*", fieldTest.toString());    
    }


}
