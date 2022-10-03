package gtPlusPlus.core.util.sys;

public class SystemUtils {

    private static OS SystemType;

    public static OS getOS() {
        if (SystemType == null) {
            SystemType = getOperatingSystem();
        }
        return SystemType;
    }

    /**
     * Try invoke the runtime's Garbage Collector.
     */
    public static void invokeGC() {
        try {
            Runtime r = Runtime.getRuntime();
            r.gc();
        } catch (Throwable t) {
            // Do nothing.
        }
    }

    public static boolean isWindows() {
        return (getOSString().contains("win"));
    }

    public static boolean isMac() {
        return (getOSString().contains("mac"));
    }

    public static boolean isUnix() {
        return (getOSString().contains("nix")
                || getOSString().contains("nux")
                || getOSString().indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (getOSString().contains("sunos"));
    }

    public static String getOSString() {
        try {
            return System.getProperty("os.name").toLowerCase();
        } catch (Throwable t) {
            return "other";
        }
    }

    public static OS getOperatingSystem() {
        if (isMac()) {
            return OS.MAC;
        } else if (isWindows()) {
            return OS.WINDOWS;
        } else if (isUnix()) {
            return OS.UNIX;
        } else if (isSolaris()) {
            return OS.SOLARIS;
        } else {
            return OS.OTHER;
        }
    }

    public enum OS {
        MAC(1),
        WINDOWS(2),
        UNIX(3),
        SOLARIS(4),
        OTHER(0);

        private int mID;

        OS(final int ID) {
            this.mID = ID;
        }

        public int getID() {
            return this.mID;
        }
    }
}
