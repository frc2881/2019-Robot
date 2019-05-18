package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;
import org.frc2881.subsystems.Drive.ArmExtensionState;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoysticks extends Command {

    public DriveWithJoysticks() {
        requires(Robot.drive);
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //if the joysticks move, it moves the corrisponding side of the robot \/

        double ySpeed = Robot.oi.driver.getX(GenericHID.Hand.kLeft);
        double xSpeed = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        double rotateSpeed = Robot.oi.driver.getX(GenericHID.Hand.kRight);
        double yaw = -Robot.drive.navX.getYaw();
        //double left = -Robot.oi.driver.getY(GenericHID.Hand.kLeft);
        //double right = -Robot.oi.driver.getY(GenericHID.Hand.kRight);
        Robot.drive.mecanumDriveField(ySpeed, xSpeed, rotateSpeed, yaw);
        if(Robot.drive.getArmExtensionState() == ArmExtensionState.LOCKED){
            //Robot.drive.setLiftCrawler((right + left)/2);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.logEnd(this);
    }

    @Override
    protected void interrupted() {
        Robot.logInterrupted(this);
    }
}
