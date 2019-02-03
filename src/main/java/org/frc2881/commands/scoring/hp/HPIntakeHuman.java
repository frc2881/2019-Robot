// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.frc2881.commands.scoring.hp;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.frc2881.Robot;
import org.frc2881.subsystems.Intake.GripperState;
import org.frc2881.subsystems.Intake.SuctionState;

/**
 *
 */
public class HPIntakeHuman extends CommandGroup {

    public HPIntakeHuman() {
        addSequential(new HPSuction(SuctionState.CLOSED));
        addSequential(new HPGripper(GripperState.OPEN));
    }
}