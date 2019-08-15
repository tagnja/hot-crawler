package com.taogen.hotcrawler.commons.util;

public class OsUtils
{
    public static final String OS_TYPE_LINUX = "LINUX";
    public static final String OS_TYPE_WINDOWS = "WIN";
    public static final String OS_TYPE_MAC = "MAC";
    public static final String OS_TYPE_UNIX = "UNIX";
    public static final String OS_TYPE_SOLARIS = "SOLARIS";

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getOsType()
    {
        if (isWindows())
        {
            return OS_TYPE_WINDOWS;
        }
        else if (isMac())
        {
            return OS_TYPE_MAC;
        }
        else if (isSolaris())
        {
            return OS_TYPE_SOLARIS;
        }
        else if (isUnix())
        {
            return OS_TYPE_UNIX;
        }
        else
        {
            return null;
        }
    }

    public static boolean isWindows()
    {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac()
    {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix()
    {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    public static boolean isSolaris()
    {
        return (OS.indexOf("sunos") >= 0);
    }

}
