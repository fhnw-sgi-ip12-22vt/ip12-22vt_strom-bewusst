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

import java.util.ArrayList;
import java.util.List;


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
    private SimpleButton SteckdoseLinks;
    private SimpleButton SteckdoseMitte;
    private SimpleButton SteckdoseRechts;
    private SimpleButton ButtonOben;
    private SimpleButton ButtonUnten;

    private List<Runnable> linksDown = new ArrayList<>();
    private List<Runnable> rechtsDown = new ArrayList<>();
    private List<Runnable> mitteDown = new ArrayList<>();
    private List<Runnable> obenDown = new ArrayList<>();
    private  List<Runnable> untenDown = new ArrayList<>();

    public Buttons(PIN links, PIN unten, PIN rechts, PIN mitte, PIN oben) {
        SteckdoseLinks = new SimpleButton(pi4jContext,links, false);
        SteckdoseLinks.onDown(() -> {
            for (Runnable task : linksDown) {
                task.run();
            }
        });
        SteckdoseRechts = new SimpleButton(pi4jContext,rechts, false);
        SteckdoseRechts.onDown(() -> {
            for (Runnable task : rechtsDown) {
            task.run();
            }
        });
        SteckdoseMitte = new SimpleButton(pi4jContext,mitte, false);
        SteckdoseMitte.onDown(() -> {
            for (Runnable task : mitteDown) {
                task.run();
            }
        });
        ButtonOben = new SimpleButton(pi4jContext,oben, false);
        ButtonOben.onDown(() -> {
            for (Runnable task : obenDown) {
                task.run();
            }
        });
        ButtonUnten = new SimpleButton(pi4jContext,unten, false);
        ButtonUnten.onDown(() -> {
            for (Runnable task : untenDown) {
                task.run();
            }
        });
    }


    public void linksDown(Runnable tasks){
        linksDown.add(tasks);
    }
    public void rechtsDown(Runnable tasks) {
        rechtsDown.add(tasks);
    }
    public void mitteDown(Runnable tasks){
        mitteDown.add(tasks);
    }
    public void obenDown(Runnable tasks) {
        obenDown.add(tasks);
    }
    public void untenDown(Runnable tasks) {
        untenDown.add(tasks);
    }
}


