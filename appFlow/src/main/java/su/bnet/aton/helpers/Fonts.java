package su.bnet.aton.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 23.11.2016.
 */

public class Fonts {
    private final static String TAG = Fonts.class.toString();

    private static Fonts instance;
    private Typeface def;
    private Map<Type, Typeface> typefaceMap = new HashMap<>();

    public enum Type{
        BLACK, BLACK_ITALIC,
        REGULAR_NORMAL, REGULAR_BOLD, REGULAR_BOLD_ITALIC, REGULAR_ITALIC,
        LIGHT_NORMAL, LIGHT_ITALIC,
        MEDIUM_NORMAL, MEDIUM_ITALIC,
        NARROW_MEDIUM, NARROW_BOLD
    }

    public static void create(Context context){
        //Black
        Typeface blackNormal = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Black.ttf");
        Typeface blackItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Black-Italic.ttf");

        //Regular
        Typeface regularNormal = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Reg.ttf");
        Typeface regularBold = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Bold.ttf");
        Typeface regularBoldItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Bold-Italic.ttf");
        Typeface regularItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Italic.ttf");

        //light
        Typeface lightNormal = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Light.ttf");
        Typeface lightItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Light-Italic.ttf");

        //medium
        Typeface mediumNormal = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Medium.ttf");
        Typeface mediumItalic = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Medium-Italic.ttf");

        //narrow
        Typeface narrowMedium = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Narrow-Medium.ttf");
        Typeface narrowBold = Typeface.createFromAsset(context.getAssets(), "fonts/GothamPro-Narrow-Bold.ttf");

        instance = new Fonts();

        instance.def = regularNormal;
        instance.typefaceMap.put(Type.BLACK, blackNormal);
        instance.typefaceMap.put(Type.BLACK_ITALIC, blackItalic);
        instance.typefaceMap.put(Type.REGULAR_NORMAL, regularNormal);
        instance.typefaceMap.put(Type.REGULAR_BOLD, regularBold);
        instance.typefaceMap.put(Type.REGULAR_BOLD_ITALIC, regularBoldItalic);
        instance.typefaceMap.put(Type.REGULAR_ITALIC, regularItalic);
        instance.typefaceMap.put(Type.LIGHT_NORMAL, lightNormal);
        instance.typefaceMap.put(Type.LIGHT_ITALIC, lightItalic);
        instance.typefaceMap.put(Type.MEDIUM_NORMAL, mediumNormal);
        instance.typefaceMap.put(Type.MEDIUM_ITALIC, mediumItalic);
        instance.typefaceMap.put(Type.NARROW_MEDIUM, narrowMedium);
        instance.typefaceMap.put(Type.NARROW_BOLD, narrowBold);
    }

    public Typeface get(int i){
        return get(Type.values()[i]);
    }

    public Typeface get(String name){
        Type t = null;
        try {
            t = Type.valueOf(name);
            return get(t);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, e.getMessage());
            return get();
        }
    }

    public Typeface get(Type type){
        return typefaceMap.get(type);
    }

    public Typeface get(){
        return def;
    }

    public static Fonts getInstance() {
        return instance;
    }
}
