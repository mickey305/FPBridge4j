package com.mickey305.fpbridge.v1;

public class OSCheck {
    private static final String OS_NAME_KEY = "os.name";
    private static final String OS_NAME = System.getProperty(OS_NAME_KEY).toLowerCase();

    private static final String WINDOWS = "windows";
    private static final String MAC = "mac";
    private static final String LINUX = "linux";
    private static final String SUN_OS = "sunos";

    // static method only class
    private OSCheck() {}

    public static boolean isWindows() {
        return is(WINDOWS);
    }

    public static boolean isMac() {
        return is(MAC);
    }

    public static boolean isLinux() {
        return is(LINUX);
    }

    public static boolean isSunOS() {
        return is(SUN_OS);
    }

    public static boolean isUnix() {
        return isLinux() || isMac() || isSunOS();
    }

    private static boolean is(String os) {
        return OS_NAME.startsWith(os);
    }
}