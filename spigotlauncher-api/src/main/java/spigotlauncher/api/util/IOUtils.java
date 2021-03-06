package spigotlauncher.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class IOUtils {

    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    public static byte[] readAllBytes(InputStream stream) throws IOException {
        int capacity = 16;
        byte[] buf = new byte[capacity];
        int nread = 0;
        int n;
        for (; ; ) {
            while ((n = stream.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            if (n < 0 || (n = stream.read()) < 0)
                break;

            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = Math.max(capacity << 1, BUFFER_SIZE);
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
            buf[nread++] = (byte) n;
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }
}
