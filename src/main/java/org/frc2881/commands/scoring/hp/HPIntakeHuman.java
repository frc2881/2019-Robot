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

import org.frc2881.subsystems.Intake.GrabberState;
import org.frc2881.subsystems.Intake.SuctionState;
import org.frc2881.subsystems.Intake.TongueState;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class HPIntakeHuman extends CommandGroup {

    public HPIntakeHuman() {
        addSequential(new HPSuction(SuctionState.CLOSED));
        addSequential(new HPGrabber(GrabberState.GRAB));
        addSequential(new WaitCommand(0.5));
        addSequential(new HPTongue(TongueState.IN));
    }
}