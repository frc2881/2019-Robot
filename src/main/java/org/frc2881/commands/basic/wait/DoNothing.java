package org.frc2881.commands.basic.wait;

import edu.wpi.first.wpilibj.command.InstantCommand;

import org.frc2881.Robot;

/**
 * Pick this command in Shuffleboard to do nothing in Autonomous mode.
 */
public class DoNothing extends InstantCommand {
    @Override
    protected void end() {
        Robot.logEnd(this);
    }
}
