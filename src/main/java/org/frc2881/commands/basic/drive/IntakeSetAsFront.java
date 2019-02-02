
package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;
import org.frc2881.subsystems.Drive;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeSetAsFront extends InstantCommand {

    @Override
    protected void initialize() {
        //Robot.Drive.setIntakeLocation(Drive.IntakeLocation.FRONT);  // TODO
        //Prints in the driver station
        Robot.log("Intake has been set to FRONT.");
        Robot.log("Meaning the INTAKE of the robot is now the FRONT");
    }

}

