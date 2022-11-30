package ch.fhnw.strombewusst.input;

import ch.fhnw.strombewusst.input.pi4jcomponents.Ads1115;
import ch.fhnw.strombewusst.input.pi4jcomponents.JoystickAnalog;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;

public class Player1Controller extends Controller {
    public Player1Controller() {
        Ads1115 ads1115 = new Ads1115(pi4jContext, 0x01, Ads1115.GAIN.GAIN_4_096V, Ads1115.ADDRESS.GND, 4);
        JoystickAnalog joystick = new JoystickAnalog(pi4jContext, ads1115, 0, 1, 3.3, true, PIN.D26);

        joystick.xOnMove((value) -> System.out.println("Current value of joystick x axis is: " + String.format("%.3f", value)));
        joystick.yOnMove((value) -> System.out.println("Current value of joystick y axis is: " + String.format("%.3f", value)));

        joystick.calibrateJoystick();

        joystick.start(0.05, 10);
    }
}
