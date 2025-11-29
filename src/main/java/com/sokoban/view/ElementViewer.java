package com.sokoban.view;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.sokoban.model.Element;

public interface ElementViewer<T extends Element> {
    void draw(T element, TextGraphics graphics);
}
