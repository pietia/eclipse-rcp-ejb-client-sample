package dentistassistant.core.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class used to manipulating MAC addresses
 *
 * @author pietia
 */
final public class MacTools {

    /**
     * Private constructor prevents from instantiating this class.
     */
    private MacTools() {
    }

    /**
     * Method returns list of MAC addresses (interfaces with assigned IP
     * address only).
     *
     * @return List of MAC addresses;
     * @throws SocketException if there were problems with getting information about network interfaces
     */
    public static List<String> getInterfaces() throws SocketException {
        final List<String> macs = new LinkedList<String>();
        final Enumeration<NetworkInterface> inter = NetworkInterface
                .getNetworkInterfaces();
        while (inter.hasMoreElements()) {
            byte[] address = inter.nextElement().getHardwareAddress();

            if (address != null) {
                System.out.println(address.toString() + "=" + address[0] + "=" + address[1] +
                        "=" + address[2] + "=" + address[3] + "=" + address[4] + "=" + address[5] + "-->" + StringTools.byteArrayToHexString(address));

                macs.add(StringTools.byteArrayToHexString(address));
            }
        }
        return macs;
    }
}
