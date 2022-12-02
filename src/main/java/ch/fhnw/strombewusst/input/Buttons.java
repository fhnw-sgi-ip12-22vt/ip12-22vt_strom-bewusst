package ch.fhnw.strombewusst.input;

import ch.fhnw.strombewusst.input.pi4jcomponents.Ads1115;
import ch.fhnw.strombewusst.input.pi4jcomponents.helpers.PIN;
import ch.fhnw.strombewusst.input.pi4jcomponents.SimpleButton;
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


public class Buttons {
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
    private final SimpleButton button;
    protected static final long DEFAULT_DEBOUNCE = 10000;

    public Buttons(PIN pin) {
      button = new SimpleButton(pi4jContext, pin, Boolean.FALSE, DEFAULT_DEBOUNCE);
    }
    public void pressed(Runnable tasks) {
        if (button.isDown()){
            button.onUp(tasks);
        } else {
            button.onDown(tasks);
        }
    }
}

