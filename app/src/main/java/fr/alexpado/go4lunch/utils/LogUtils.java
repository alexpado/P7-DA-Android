package fr.alexpado.go4lunch.utils;

import android.app.Activity;
import android.util.Log;

public class LogUtils {

    public static void debug(Object source, String message) {

        Log.d(source.getClass().getSimpleName(), message);
    }

    public static void debug(Object source, String message, Object... args) {

        Log.d(source.getClass().getSimpleName(), String.format(message, args));
    }

    public static void info(Object source, String message) {

        Log.i(source.getClass().getSimpleName(), message);
    }

    public static void info(Object source, String message, Object... args) {

        Log.i(source.getClass().getSimpleName(), String.format(message, args));
    }

    public static void warn(Object source, String message) {

        Log.w(source.getClass().getSimpleName(), message);
    }

    public static void warn(Object source, String message, Object... args) {

        Log.w(source.getClass().getSimpleName(), String.format(message, args));
    }

    public static void warn(Object source, Throwable throwable, String message) {

        Log.w(source.getClass().getSimpleName(), message, throwable);
    }

    public static void warn(Object source, Throwable throwable, String message, Object... args) {

        Log.w(source.getClass().getSimpleName(), String.format(message, args), throwable);
    }


}
