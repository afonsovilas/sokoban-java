package com.sokoban.controller;

import com.sokoban.Game;

public interface Command {
    void execute(Game game);
}
