
package org.frc2881.commands.basic.background;

import org.frc2881.Robot;
import org.frc2881.subsystems.PrettyLights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class TWINKLES extends Command {

    public TWINKLES() {
        requires(Robot.prettyLights);
    }

    @Override
    protected void initialize() {
        Robot.logInitialize(this);
    }

    @Override
    protected void execute() {
        //depnding clor of on the allince, the chang LED strp to wil the tha colr, or Red Blu

        //depending on the color of the alliance, the LED strip will change to that color, Red or Blue
        //Pink if robot is idle
        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
        if (alliance == DriverStation.Alliance.Blue) {
            Robot.prettyLights.setColor(PrettyLights.blue_heartbeat);

        } else if (alliance == DriverStation.Alliance.Red) {
            Robot.prettyLights.setColor(PrettyLights.red_heartbeat);

        } else {
            Robot.prettyLights.setColor(PrettyLights.hotPink);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.logEnd(this);
    }
}