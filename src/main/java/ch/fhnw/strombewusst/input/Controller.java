package ch.fhnw.strombewusst.input;

import ch.fhnw.strombewusst.input.pi4jcomponents.Ads1115;
import ch.fhnw.strombewusst.input.pi4jcomponents.JoystickAnalog;
import ch.fhnw.strombewusst.input.pi4jcomponents.SimpleButton;
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
    public static final PiGpio PI_GPIO = PiGpio.newNativeInstance();
    public static final Context PI4J_CONTEXT = Pi4J.newContextBuilder()
            .noAutoDetect()
            .add(new RaspberryPiPlatform() {
                @Override
                protected String[] getProviders() {
                    return new String[] {};
                }
            })
            .add(
                    PiGpioDigitalInputProvider.newInstance(PI_GPIO),
                    PiGpioDigitalOutputProvider.newInstance(PI_GPIO),
                    PiGpioPwmProvider.newInstance(PI_GPIO),
                    PiGpioSerialProvider.newInstance(PI_GPIO),
                    PiGpioSpiProvider.newInstance(PI_GPIO),
                    LinuxFsI2CProvider.newInstance()
            )
            .build();

    private final JoystickAnalog joystick;

    private final SimpleButton outletLeft;
    private final SimpleButton outletMiddle;
    private final SimpleButton outletRight;
    private final SimpleButton buttonUpper;
    private final SimpleButton buttonLower;

    private final List<Runnable> onJoystickRightTasks = new ArrayList<>();
    private final List<Runnable> onJoystickLeftTasks = new ArrayList<>();
    private final List<Runnable> onJoystickHorizontalIdleTasks = new ArrayList<>();
    private final List<Runnable> onJoystickUpTasks = new ArrayList<>();
    private final List<Runnable> onJoystickDownTasks = new ArrayList<>();
    private final List<Runnable> onJoystickVerticalIdleTasks = new ArrayList<>();

    private final List<Runnable> onButtonLeftDownTasks = new ArrayList<>();
    private final List<Runnable> onButtonRightDownTasks = new ArrayList<>();
    private final List<Runnable> onButtonMiddleDownTasks = new ArrayList<>();
    private final List<Runnable> onButtonUpperDownTasks = new ArrayList<>();
    private final List<Runnable> onButtonLowerDownTasks = new ArrayList<>();

    protected Controller() {
        joystick = null;
        outletLeft = null;
        outletMiddle = null;
        outletRight = null;
        buttonUpper = null;
        buttonLower = null;
    }

    public Controller(Ads1115 ads1115, int channelXAxis, int channelYAxis, PIN pin,
                      PIN links, PIN mitte, PIN rechts, PIN oben, PIN unten) {
        joystick = new JoystickAnalog(PI4J_CONTEXT, ads1115, channelXAxis, channelYAxis, 3.3, true, pin);

        joystick.xOnMove(this::handleXMove);
        joystick.yOnMove(this::handleYMove);

        try {
            joystick.calibrateJoystick();
        } catch (ContinuousMeasuringException ignored) {
            System.out.println("ContinuousMeasuringException, ignoring");
        }
        joystick.start(0.05, 16);

        outletLeft = new SimpleButton(PI4J_CONTEXT, links, false);
        outletLeft.onDown(() -> {
            for (Runnable task : onButtonLeftDownTasks) {
                task.run();
            }
        });
        outletRight = new SimpleButton(PI4J_CONTEXT, rechts, false);
        outletRight.onDown(() -> {
            for (Runnable task : onButtonRightDownTasks) {
                task.run();
            }
        });
        outletMiddle = new SimpleButton(PI4J_CONTEXT, mitte, false);
        outletMiddle.onDown(() -> {
            for (Runnable task : onButtonMiddleDownTasks) {
                task.run();
            }
        });
        buttonUpper = new SimpleButton(PI4J_CONTEXT, oben, false);
        buttonUpper.onDown(() -> {
            for (Runnable task : onButtonUpperDownTasks) {
                task.run();
            }
        });
        buttonLower = new SimpleButton(PI4J_CONTEXT, unten, false);
        buttonLower.onDown(() -> {
            for (Runnable task : onButtonLowerDownTasks) {
                task.run();
            }
        });
    }

    protected void handleXMove(double value) {
        List<Runnable> tasks;
        if (value > 0.75) {
            tasks = onJoystickLeftTasks;
        } else if (value < 0.25) {
            tasks = onJoystickRightTasks;
        } else {
            tasks = onJoystickHorizontalIdleTasks;
        }
        for (Runnable task : tasks) {
            task.run();
        }
    }

    protected void handleYMove(double value) {
        List<Runnable> tasks;
        if (value > 0.75) {
            tasks = onJoystickUpTasks;
        } else if (value < 0.25) {
            tasks = onJoystickDownTasks;
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


    /**
     * Method to register Tasks to be run when the left Button on the Controller is pressed
     *
     * @param task the task to be run
     */
    public void onButtonLeftDown(Runnable task) {
        onButtonLeftDownTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the right Button on the Controller is pressed
     *
     * @param task the task to be run
     */
    public void onButtonRightDown(Runnable task) {
        onButtonRightDownTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the middle Button on the Controller is pressed
     *
     * @param task the task to be run
     */
    public void onButtonMiddleDown(Runnable task) {
        onButtonMiddleDownTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the upper Button on the Controller is pressed
     *
     * @param task the task to be run
     */
    public void onButtonUpperDown(Runnable task) {
        onButtonUpperDownTasks.add(task);
    }

    /**
     * Method to register Tasks to be run when the lower Button on the Controller is pressed
     *
     * @param task the task to be run
     */
    public void onButtonLowerDown(Runnable task) {
        onButtonLowerDownTasks.add(task);
    }
}
