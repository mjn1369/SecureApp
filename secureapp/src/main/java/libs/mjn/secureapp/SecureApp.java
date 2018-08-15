package libs.mjn.secureapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by mJafarinejad on 8/15/2018.
 */
public class SecureApp {

    public static boolean checkSignatureIntegrity(Context context, String appSignature) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String currentSignature = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                if(currentSignature.equals(appSignature)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public static boolean checkManifestIntegrity(Context context, String crcManifest) {
        Long dexCRC_Manifest;
        ZipFile zf;

        try {
            dexCRC_Manifest = java.lang.Long.parseLong(crcManifest, 16);
            zf = new ZipFile(context.getPackageCodePath());
            ZipEntry ze = zf.getEntry("AndroidManifest.xml");
            if (ze.getCrc() != dexCRC_Manifest) {
                return false;
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean checkClassesIntegrity(Context context, boolean isMultiDex, String... crcManifest) {
        Long dexCRC_Manifest;
        ZipFile zf;

        for(int i=0;i<(isMultiDex?crcManifest.length:1);i++){
            try {
                dexCRC_Manifest = java.lang.Long.parseLong(crcManifest[i], 16);
                zf = new ZipFile(context.getPackageCodePath());
                ZipEntry ze = zf.getEntry("classes"+(i==0?"":i)+".dex");
                if (ze.getCrc() != dexCRC_Manifest) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDebuggable(Context context){
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static List<String> checkReverseEngineeringTools(Context context){
        List<String> result = new ArrayList<>();

        // check for Frida
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(300);
        if(rs!=null){
            for(ActivityManager.RunningServiceInfo s : rs){
                if(s.process.contains("fridaserver")){
                    result.add("Frida");
                    break;
                }
            }
        }

        // check for Xposed Framework
        if(isPackageInstalled(context,"de.robv.android.xposed.installer"))
            result.add("Xposed Framework");

        // check for Substrate
        if(isPackageInstalled(context,"com.saurik.substrate.apk"))
            result.add("Substrate");

        // check for Drozer
        if(isPackageInstalled(context,"com.mwr.dz"))
            result.add("Drozer");

        return result;
    }

    private static boolean isPackageInstalled(Context context, String packagename) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
