package org.frc2881.utils;

import edu.wpi.first.wpilibj.GenericHID;

public class ButtonFromPOV extends edu.wpi.first.wpilibj.buttons.Button {
    private boolean previous;
    private boolean compatible;
    private int counter;

    private GenericHID controller;
    private int angle;

    public ButtonFromPOV(GenericHID controller, int angle) {
        this.controller = controller;
        this.angle = angle;
        previous = controller.getPOV() == angle;
    }

    public boolean get() {
        // Is current above threshold and not dropping?  (ie. is motor stopped & not accelerating?)
        boolean current = controller.getPOV() == angle;
        compatible = previous == current;

        if (!compatible) {
            counter++;
            if (counter == 3) {
                previous = current;
            }
        }
        else {
            counter = 0;
        }

        return previous;
    }
}
