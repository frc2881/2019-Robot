package org.frc2881.commands.basic.drive;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForDistance extends Command {

    double distance;

    double startingPosition;

    public DriveForDistance(double distance) {
        this.distance = distance;
        requires(Robot.drive);
        startingPosition = Robot.drive.strafeEncoder.getPosition();
    } 
    
    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    @Override
    protected void execute() {
        Robot.drive.setStrafeMotorSpeed(distance);
        Robot.drive.strafeEncoder.getPosition();
        
    }

    @Override
    protected boolean isFinished() { 
    
        return false;
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