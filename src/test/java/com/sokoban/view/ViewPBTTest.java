package com.sokoban.view;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

import static org.assertj.core.api.Assertions.assertThat;

class ViewPBTTest {



    @Property
    void centeringLogicShouldAlwaysBeBalanced(
            @ForAll @IntRange(min = 10, max = 200) int screenWidth,
            @ForAll @IntRange(min = 1, max = 199) int objectWidth) {

        Assume.that(screenWidth >= objectWidth);

        int offset = (screenWidth - objectWidth) / 2;

        int spaceLeft = offset;
        int spaceRight = screenWidth - (offset + objectWidth);


        assertThat(Math.abs(spaceLeft - spaceRight)).isLessThanOrEqualTo(1);

        assertThat(offset).isNotNegative();
    }

    @Property
    void gridBoxesShouldNeverOverlap(
            @ForAll @IntRange(min = 0, max = 100) int index1,
            @ForAll @IntRange(min = 0, max = 100) int index2) {

        Assume.that(index1 != index2);

        int columns = 2;
        int boxWidth = 22;
        int boxHeight = 5;
        int paddingX = 6;
        int paddingY = 2;
        int startX = 10;
        int startY = 8;


        int col1 = index1 % columns;
        int row1 = index1 / columns;
        int x1 = startX + (col1 * (boxWidth + paddingX));
        int y1 = startY + (row1 * (boxHeight + paddingY));

        int col2 = index2 % columns;
        int row2 = index2 / columns;
        int x2 = startX + (col2 * (boxWidth + paddingX));
        int y2 = startY + (row2 * (boxHeight + paddingY));

        boolean overlapX = (x1 < x2 + boxWidth) && (x1 + boxWidth > x2);
        boolean overlapY = (y1 < y2 + boxHeight) && (y1 + boxHeight > y2);

        assertThat(overlapX && overlapY)
                .withFailMessage("Caixas %d e %d estão sobrepostas!", index1, index2)
                .isFalse();
    }



    @Property
    void hudTextShouldHandleAnyIntegers(
            @ForAll int steps,
            @ForAll int lives) {

        String stepText = "Steps: " + steps;
        String livesText = "Lives: ";

        StringBuilder hearts = new StringBuilder();
        if (lives > 0 && lives < 100) {
            for (int i = 0; i < lives; i++) hearts.append("H ");
        } else if (lives <= 0) {
            hearts.append("0");
        }
        livesText += hearts.toString();

        assertThat(stepText).contains(String.valueOf(steps));

        if (lives <= 0) {
            assertThat(livesText).endsWith("0");
        } else if (lives < 100) {
            assertThat(livesText).contains("H");
        }
    }
}
