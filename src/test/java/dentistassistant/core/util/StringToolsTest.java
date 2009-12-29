package dentistassistant.core.util;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;


public class StringToolsTest {


    private static final String OUTPUT_MD5 = "a3dcca66f849d7adb12e4f41f0837d8d";
    private static final String OUTPUT_FROM_EMPTY_STRING = "d41d8cd98f00b204e9800998ecf8427e";
    private static final String INPUT_STRING = "ala ma psa";
    private static final String INPUT_EMPTY = "";

    private static final byte[] INPUT_ARRAY = {0, 28, -65, -81, 65, 9};
    private static final String OUTPUT_MAC = "001CBFAF4109";

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseIllegallArgumentExceptionWhenInputStringForMD5IsNull() throws IllegalArgumentException {
        StringTools.getMD5(null);
    }


    @Ignore(value = "Platform specific test. At most of platforms JVM can generate MD5 hash code")
    @Test(expected = RuntimeException.class)
    public void shouldRaiseRuntimeExceptionWhenPlatformDoesntSupportGeneratingMD5() throws RuntimeException {
        StringTools.getMD5("tajnehasło");
    }


    @Test
    public void shouldReturnCorrectMD5ForNotEmptyInput() throws RuntimeException {
        assertEquals(StringTools.getMD5(INPUT_STRING), OUTPUT_MD5);
    }

    @Test
    public void shouldReturnCorrectMD5ForEmptyInput() throws RuntimeException {
        assertEquals(StringTools.getMD5(INPUT_EMPTY), OUTPUT_FROM_EMPTY_STRING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseIllegallArgumentExceptionWhenInputArrayIsNull() throws IllegalArgumentException {
        StringTools.byteArrayToHexString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseIllegallArgumentExceptionWhenInputArrayIsEmpty() throws IllegalArgumentException {
        StringTools.byteArrayToHexString(new byte[0]);
    }

    @Test
    public void shouldReturnCorrectMacAddressForByteArray() {
        assertEquals(StringTools.byteArrayToHexString(INPUT_ARRAY), OUTPUT_MAC);
    }

}
