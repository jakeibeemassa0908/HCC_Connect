package com.hccs.app.studentIn.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.hccs.app.studentIn.R;

/**
 * Created by theotherside on 11/2/16.
 */

public class Utils {
    public static boolean check(String url,Context context){
        if(url.startsWith("tel:")){
            String number =url.replace("-","").replace("/","");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
            context.startActivity(intent);
            return true;
        }else if(url.startsWith("mailto:")){
            String email=url.replace("mailto:","").trim();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
            try{
                context.startActivity(intent);
            }catch (android.content.ActivityNotFoundException ex ){
                Toast.makeText(context, context.getString(R.string.no_email_client), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return false;
    }
}
