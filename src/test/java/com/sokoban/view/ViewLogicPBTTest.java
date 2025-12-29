package com.sokoban.view;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Assertions;

class ViewLogicPBTTest {

    @Property
    void centeringShouldAlwaysBeBalanced(
            @ForAll @IntRange(min = 10, max = 1000) int screenWidth,
            @ForAll @IntRange(min = 1, max = 1000) int objectWidth) {

        Assume.that(screenWidth >= objectWidth);

        int startX = (screenWidth - objectWidth) / 2;

        int spaceLeft = startX;

        int spaceRight = screenWidth - (startX + objectWidth);

        Assertions.assertTrue(Math.abs(spaceLeft - spaceRight) <= 1,
                String.format("Centragem falhou para Screen=%d, Obj=%d. Left=%d, Right=%d",
                        screenWidth, objectWidth, spaceLeft, spaceRight));

        Assertions.assertTrue(startX >= 0, "O X inicial não pode ser negativo");
    }


    @Property
    void gridMathShouldBeReversible(
            @ForAll @IntRange(min = 0, max = 100) int index) {

        int columns = 2;

        // A TUA LÓGICA
        int col = index % columns;
        int row = index / columns;

        int calculatedIndex = (row * columns) + col;

        Assertions.assertEquals(index, calculatedIndex,
                "A matemática da grelha está errada. Mutante alterou / ou %?");

        Assertions.assertTrue(col < columns, "A coluna não pode ser maior que o número de colunas");
    }


    @Property
    void menuBoxesShouldNeverOverlap(
            @ForAll @IntRange(min = 0, max = 50) int index) {

        int columns = 2;
        int boxWidth = 22;
        int boxHeight = 5;
        int paddingX = 6;
        int paddingY = 2;
        int startX = 15;
        int startY = 8;

        int col1 = index % columns;
        int row1 = index / columns;
        int x1 = startX + (col1 * (boxWidth + paddingX));
        int y1 = startY + (row1 * (boxHeight + paddingY));

        int index2 = index + 1;
        int col2 = index2 % columns;
        int row2 = index2 / columns;
        int x2 = startX + (col2 * (boxWidth + paddingX));
        int y2 = startY + (row2 * (boxHeight + paddingY));

        if (row1 == row2) {
            Assertions.assertTrue(x2 >= x1 + boxWidth,
                    "Caixas sobrepostas horizontalmente! Verifique o paddingX.");
        }

        if (row2 > row1) {
            Assertions.assertTrue(y2 >= y1 + boxHeight,
                    "Caixas sobrepostas verticalmente! Verifique o paddingY.");
        }
    }
}