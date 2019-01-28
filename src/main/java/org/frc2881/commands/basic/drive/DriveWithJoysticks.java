package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoysticks extends Command {
    public DriveWithJoysticks() {

        requires(Robot.drive);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

    }
    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //What are the names of the joysticks?
        double left = -Robot.oi.driverJoysticks.getY(GenericHID.Hand.kLeft);
        double right = -Robot.oi.driverJoysticks.getY(GenericHID.Hand.kRight);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
