
package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;
import org.frc2881.subsystems.Drive;
import org.frc2881.utils.NTValue;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeSetAsFront extends InstantCommand {

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
        Robot.drive.setIntakeLocation(Drive.IntakeLocation.FRONT);
        //NTValue.setCameraForward(true); REMOVE
        //Prints in the driver station
        Robot.log("Intake has been set to FRONT.");
        Robot.log("Meaning the INTAKE of the robot is now the FRONT");
        //Robot.log("The Driver Camera has been switched to FORWARD"); REMOVE
    }

}

