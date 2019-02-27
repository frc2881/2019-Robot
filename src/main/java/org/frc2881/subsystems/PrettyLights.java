package org.frc2881.subsystems;

import org.frc2881.commands.basic.background.TWINKLES;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class PrettyLights extends Subsystem implements SendableWithChildren {

    public static final double red_heartbeat = -0.25;
    public static final double blue_heartbeat = -0.23;
    public static final double hotPink = 0.57;
    public static final double green = 0.77;
    public static final double orange = 0.65;
    public final Spark tWINKLES;
    public final Spark ringLight;
//intake - 3 and 4, lift - 1 and 2, driveCrawler - 5, twinkles - 6, 
    public PrettyLights() {
        tWINKLES = new Spark(6);
        addChild("TWINKLES",tWINKLES);
        tWINKLES.setInverted(false);
        
        ringLight = new Spark(7);
        addChild("Ring Light", ringLight);
        ringLight.setInverted(false);

    }

    @Override
    public void initDefaultCommand() {
             // Set the default command for a subsystem here.
             setDefaultCommand(new TWINKLES());
                                        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void setColor(double color){
        tWINKLES.set(color);
        ringLight.set(hotPink);
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

