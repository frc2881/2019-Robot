package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForDistance extends Command {

    double targetPosition;


    public DriveForDistance(double distance) {
        requires(Robot.drive);
        Robot.log("position " + Robot.drive.strafeEncoder.getPosition() + " distance " + distance);
        targetPosition = Robot.drive.strafeEncoder.getPosition() + distance;
    } 
    
    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    @Override
    protected void execute() {
        double error = targetPosition - Robot.drive.strafeEncoder.getPosition();
        Robot.drive.setStrafeMotorSpeed(error * 1);
        Robot.log("position " + Robot.drive.strafeEncoder.getPosition() + " error " + error);
    }

    @Override
    protected boolean isFinished() {       
        double error = targetPosition - Robot.drive.strafeEncoder.getPosition();
        return Math.abs(error) < 1;
    }

    @Override
    protected void interrupted() {
        Robot.drive.tankDrive(0, 0, 0);
        Robot.logEnd(this);
        end();
    }

    @Override
    protected void end() {
        Robot.drive.tankDrive(0, 0, 0);
    }



}