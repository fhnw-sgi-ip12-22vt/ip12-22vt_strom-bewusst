package ch.fhnw.strombewusst.ui.scene;

import ch.fhnw.strombewusst.ControllerComponents.Ads1115;
import ch.fhnw.strombewusst.ControllerComponents.JoystickAnalog;
import ch.fhnw.strombewusst.ControllerComponents.helpers.PIN;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;




public class JoystickTest {
    public static void main(String[] args) {
        Context pi4j;
        final var piGpio = PiGpio.newNativeInstance();
        pi4j = Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio),
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();

        System.out.println("Joystick test started ...");
            Ads1115 ads1115 = new Ads1115(pi4j, 0x01, Ads1115.GAIN.GAIN_4_096V, Ads1115.ADDRESS.GND, 4);

//joystick with normalized axis from 0 to 1
            JoystickAnalog joystick = new JoystickAnalog(pi4j, ads1115, 0, 1, 3.3, true, PIN.D26);

//joystick with normalized axis from -1 to 1
//JoystickAnalog joystick = new JoystickAnalog(pi4j, ads1115, 0, 1, 3.3, false, PIN.D26);

//register event handlers
            joystick.xOnMove((value) -> System.out.println("Current value of joystick x axis is: " + String.format("%.3f", value)));
            joystick.yOnMove((value) -> System.out.println("Current value of joystick y axis is: " + String.format("%.3f", value)));

            joystick.pushOnDown(() -> System.out.println("Pressing the Button"));
            joystick.pushOnUp(() -> System.out.println("Stopped pressing."));
            joystick.pushWhilePressed(() -> System.out.println("Button is still pressed."), 1000);

            joystick.calibrateJoystick();

//start continuous reading with single shot in this mode you can connect up to 4 devices to the analog module
            joystick.start(0.05, 10);

//wait while handling events before exiting
            System.out.println("Move the joystick to see it in action!");
//deregister all event handlers

        }
    }

