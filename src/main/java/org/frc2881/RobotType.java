package org.frc2881;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public enum RobotType {

    /**
     * One of the competition bots. Hardware should be identical on all the bots.
     */
    COMPETITION_BOT,

    /**
     * The big test board that stands on its own.
     */
    TEST_BOARD_1,

    /**
     * The small test board (we have two of these).
     */
    TEST_BOARD_2;

    private static final RobotType TYPE = determineType();

    public static RobotType get() {
        return TYPE;
    }

    private static RobotType determineType() {
        // To configure the board type, login to each board and create a file like this, replacing TEST_BOARD_2 with the correct name:
        // $ ssh lvuser@10.28.81.2
        // $ echo TEST_BOARD_2 > TestBoardIdentifier.txt
        RobotType defaultType = COMPETITION_BOT;
        String filename = "/home/lvuser/TestBoardIdentifier.txt";
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            RobotType type = valueOf(in.readLine().trim());
            Robot.log("This robot is: " + type);
            return type;
        } catch (FileNotFoundException e) {
            Robot.log(String.format("Assuming %s because file not found: %s", defaultType, filename));
            return defaultType;
        } catch (IOException | IllegalArgumentException e) {
            Robot.log(String.format("Assuming %s because error reading file: %s, %s", defaultType, filename, e));
            return defaultType;
        }
    }
}
