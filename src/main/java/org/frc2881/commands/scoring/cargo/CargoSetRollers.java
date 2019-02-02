// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.frc2881.commands.scoring.cargo;

import org.frc2881.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CargoSetRollers extends Command {

    private double speed;

    public CargoSetRollers(double speed) {
        requires(Robot.intake);
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this, speed);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.intake.cargoRollers(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.intake.stopCargoRollers();
        Robot.logEnd(this);
    }
}
