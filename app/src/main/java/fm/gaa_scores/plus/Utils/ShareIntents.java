package fm.gaa_scores.plus.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by fm on 20/11/2015.
 */
public class ShareIntents {

    private static ShareIntents instance = null;
    private boolean haveTwitter = false;

    private ShareIntents() {
        // Exists only to defeat instantiation.
    }

    public static ShareIntents getInstance() {
        if (instance == null) {
            instance = new ShareIntents();
        }
        return instance;
    }

    public boolean isHaveTwitter() {
        return haveTwitter;
    }

    public Intent getTweetIntent(Context c) {
        final String twitterApps = "com.twitter.android";
        haveTwitter = false;

        Intent tweetIntent = new Intent();
        tweetIntent.setType("text/plain");
        //try fibnd twitter first
        final PackageManager packageManager = c.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(
                tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);


        for (ResolveInfo resolveInfo : list) {
            String p = resolveInfo.activityInfo.packageName;
            if (p != null && p.startsWith("com.twitter.android")) {
                Log.i("got twitter", p);
                tweetIntent.setPackage(p);
                String cls = resolveInfo.activityInfo.name;
                if (cls != null && cls.startsWith("com.twitter.android.composer.ComposerActivity")) {
                    Log.i("twitter class", cls);
                    tweetIntent.setClassName(p, cls);
                    haveTwitter = true;
                    return tweetIntent;
                }
            }
        }

//        Toast.makeText(
//                c,
//                "Can't find Twitter App\n"
//                        + "Please install Twitter App\nand login to Twitter",
//                Toast.LENGTH_LONG).show();
        Log.i("cant find twitter", "W");
        tweetIntent.setAction(Intent.ACTION_SEND);
        return tweetIntent;

    }


}
