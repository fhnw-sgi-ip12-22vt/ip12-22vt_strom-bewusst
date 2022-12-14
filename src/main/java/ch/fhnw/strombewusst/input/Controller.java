package ch.fhnw.strombewusst.input;

import ch.fhnw.strombewusst.input.pi4jcomponents.Ads1115;
import ch.fhnw.strombewusst.input.pi4jcomponents.JoystickAnalog;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.ContinuousMeasuringException;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
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

import java.util.ArrayList;
import java.util.List;

public class Controller {
    protected final static PiGpio piGpio = PiGpio.newNativeInstance();
    protected final static Context pi4jContext = Pi4J.newContextBuilder()
            .noAutoDetect()
            .add(new RaspberryPiPlatform() {
                @Override
                protected String[] getProviders() {
                    return new String[]{};
                }
            })
            .add(
                    PiGpioDigitalInputProvider.newInstance(piGpio),
                    PiGpioDigitalOutputProvider.newInstance(piGpio),
                    PiGpioPwmProvider.newInstance(piGpio),
                    PiGpioSerialProvider.newInstance(piGpio),
                    PiGpioSpiProvider.newInstance(piGpio),
                    LinuxFsI2CProvider.newInstance()
            )
            .build();

    protected static Ads1115 ads1115;

    static {
        try {
            ads1115 = new Ads1115(pi4jContext, 0x01, Ads1115.GAIN.GAIN_4_096V, Ads1115.ADDRESS.GND, 4);
        } catch (Exception ignored) {
            ads1115 = null;
        }
    }

    public JoystickAnalog joystick;

    private final List<Runnable> onJoystickRightTasks = new ArrayList<>();
    private final List<Runnable> onJoystickLeftTasks = new ArrayList<>();
    private final List<Runnable> onJoystickHorizontalIdleTasks = new ArrayList<>();
    private final List<Runnable> onJoystickUpTasks = new ArrayList<>();
    private final List<Runnable> onJoystickDownTasks = new ArrayList<>();
    private final List<Runnable> onJoystickVerticalIdleTasks = new ArrayList<>();

    public Controller(int channelXAxis, int channelYAxis, PIN pin) {
        joystick = new JoystickAnalog(pi4jContext, ads1115, channelXAxis, channelYAxis, 3.3, true, pin);

        joystick.xOnMove(this::handleXMove);
        joystick.yOnMove(this::handleYMove);

        try {
            joystick.calibrateJoystick();
        } catch (ContinuousMeasuringException ignored) {
            System.out.println("ContinuousMeasuringException, ignoring");
        }
        joystick.start(0.05, 15);
    }

    private void handleXMove(double value) {
        List<Runnable> tasks;
        if (value > 0.75) {
            tasks = onJoystickRightTasks;
        } else if (value < 0.25) {
            tasks = onJoystickLeftTasks;
        } else {
            tasks = onJoystickHorizontalIdleTasks;
        }
        for (Runnable task : tasks) {
            task.run();
        }
    }

    private void handleYMove(double value) {
        List<Runnable> tasks;
        if (value > 0.75) {
            tasks = onJoystickDownTasks;
        } else if (value < 0.25) {
            tasks = onJoystickUpTasks;
        } else {
            tasks = onJoystickVerticalIdleTasks;
        }
        for (Runnable task : tasks) {
            task.run();
        }
    }

    /**
     * Method to register Tasks to be run when the Joystick is moved to the right
     *
     * @param task the task to be run
     */
    public void onJoystickRight(Runnable task) {
        onJoystickRightTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the Joystick is moved to the left
     *
     * @param task the task to be run
     */
    public void onJoystickLeft(Runnable task) {
        onJoystickLeftTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the Joystick returns to the horizontal middle
     * The vertical position is ignored, so the Joystick could still be moved up or down
     *
     * @param task the task to be run
     */
    public void onJoystickHorizontalIdle(Runnable task) {
        onJoystickHorizontalIdleTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the Joystick is moved up
     *
     * @param task the task to be run
     */
    public void onJoystickUp(Runnable task) {
        onJoystickUpTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the Joystick is moved down
     *
     * @param task the task to be run
     */
    public void onJoystickDown(Runnable task) {
        onJoystickDownTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the Joystick returns to the vertical middle
     * The horizontal position is ignored, so the Joystick could still be moved to the right or left
     *
     * @param task the task to be run
     */
    public void onJoystickVerticalIdle(Runnable task) {
        onJoystickVerticalIdleTasks.add(task);
    }
}
