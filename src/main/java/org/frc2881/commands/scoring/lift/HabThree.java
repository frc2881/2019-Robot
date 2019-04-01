// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.frc2881.commands.scoring.lift;

import org.frc2881.Robot;
import org.frc2881.commands.basic.wait.WaitForever;
import org.frc2881.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HabThree extends CommandGroup {

    public HabThree() {
        addSequential(new LiftToHeight(Lift.HAB_THREE_HEIGHT));
        addSequential(new WaitForever());
        addSequential(new SetCrawler(1));
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }
    
    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected void end() {
        Robot.arm.setArmMotorSpeed(0);
        Robot.lift.setLiftMotors(0);
        Robot.drive.setLiftCrawler(0);
        Robot.logEnd(this);
    }

}