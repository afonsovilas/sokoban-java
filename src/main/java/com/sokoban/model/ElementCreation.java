package com.sokoban.model;

public class ElementCreation {
    public static Element createElement(char c, int x, int y) {
        switch (c) {
            case '#': return new Wall(x, y);
            case 'O': return new Box(x, y);
            case '*': return new Target(x, y);
            case 'X': return new Hero(x, y);
            case 'P': return new Portal(x,y);
            case '$': return new PowerUp(x,y);
            default:  return null;
        }
    }
}
