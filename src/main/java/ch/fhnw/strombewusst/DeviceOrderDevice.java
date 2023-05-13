package ch.fhnw.strombewusst;

/**
 * Represents a device used in our device order puzzle.
 *
 * @param device the name of the device
 * @param consumption electricity consumption of the device
 * @param image path to the device texture
 */
public record DeviceOrderDevice(
        String device,
        int consumption,
        String image
) {
}
