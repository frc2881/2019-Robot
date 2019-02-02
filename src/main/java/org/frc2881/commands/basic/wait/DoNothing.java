package org.frc2881.commands.basic.wait;

import org.frc2881.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Pick this command in Shuffleboard to do nothing in Autonomous mode.
 */
public class DoNothing extends InstantCommand {
    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.log("Do Nothing has ended");
    }
}
