package dentistassistant.core.util;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Utility class used to manipulating String
 *
 * @author pietia
 */
final public class StringTools {

    private static final Logger LOG = Logger.getLogger(StringTools.class);

    private static final String FORMAT = "%02x";
    private static final String ALGORITHM = "MD5";
    private static final String SYMBOLS[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"};

    /**
     * Private constructor prevents from instantiating this class
     */
    private StringTools() {
    }

    /**
     * Converts Array of bytes to String (in hexs)
     *
     * @param inputByteArr array of bytes
     * @return String of hexs
     * @throws IllegalArgumentException when input array null or empty
     */
    public static String byteArrayToHexString(byte inputByteArr[]) {

        if (inputByteArr == null || inputByteArr.length == 0)
            throw new IllegalArgumentException("Input array must not be empty or null");

        byte ch = 0x00;

        StringBuilder strb = new StringBuilder(inputByteArr.length * 2);
        int it = 0;
        while (it < inputByteArr.length) {
            ch = (byte) (inputByteArr[it] & 0xF0);
            ch = (byte) (ch >>> 4);
            ch = (byte) (ch & 0x0F);
            strb.append(SYMBOLS[ch]);
            ch = (byte) (inputByteArr[it] & 0x0F);
            strb.append(SYMBOLS[ch]);
            it++;
        }
        return strb.toString();
    }

    /**
     * Converts String into md5 hash
     *
     * @param input string to encode
     * @return encoded input string
     * @throws RuntimeException         when platform doesn't support MD5 alg.
     * @throws IllegalArgumentException when input string is null
     */

    public static String getMD5(String input) {

        if (input == null)
            throw new IllegalArgumentException("Input can't be null.");

        InputStream inputStream = null;

        try {
            Formatter formatter = new Formatter();
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

            inputStream = new ByteArrayInputStream(input.getBytes());
            byte[] buffer = new byte[4];
            int bufferLength;

            while ((bufferLength = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bufferLength);
            }

            final byte[] digest = messageDigest.digest();

            for (byte db : digest) {
                formatter.format(FORMAT, db);
            }
            return formatter.toString();

        } catch (NoSuchAlgorithmException e) {
            // LOG.error("Platform dosn't support algorithm: " + ALGORITHM);
            throw new RuntimeException(e);
        } catch (IOException e) {
            // LOG.error("IOExceotion: " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOG.warn(e);
                    //e.printStackTrace();
                }
            }
        }
    }


}
