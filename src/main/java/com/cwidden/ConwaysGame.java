package com.cwidden;

public class ConwaysGame {

    public static void main(String[] args) {
        ConwaysGameController conwaysGameController = ConwaysGameController.getInstance();
        conwaysGameController.buildGui();
        conwaysGameController.startMouseLocationTimer();
    }
}
