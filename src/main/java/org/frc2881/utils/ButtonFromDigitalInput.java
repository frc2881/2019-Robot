package org.frc2881.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;

public class ButtonFromDigitalInput extends edu.wpi.first.wpilibj.buttons.Button {
    private boolean previous;
    private boolean compatible;
    private int counter;

    private DigitalInput digitalInput;

    public ButtonFromDigitalInput(DigitalInput digitalInput) {
        this.digitalInput = digitalInput;
    }

    public boolean get() {
        // Is current above threshold and not dropping?  (ie. is motor stopped & not accelerating?)
        boolean current = !digitalInput.get();
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
