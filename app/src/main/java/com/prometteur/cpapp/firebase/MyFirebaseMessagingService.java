package com.prometteur.cpapp.firebase;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.NotificationBean;
import com.prometteur.cpapp.dialogs.CollectDialogActivity;
import com.prometteur.cpapp.drawer.DashboardMainActivity;
import com.prometteur.cpapp.history.HistoryDetailsActivity;
import com.prometteur.cpapp.onboarding.LoginActivity;
import com.prometteur.cpapp.profile.ProfileActivity;
import com.prometteur.cpapp.settings.SettingsActivity;
import com.prometteur.cpapp.utils.Preferences;

import java.util.Random;

import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Constants.NOTCOUNT;


/**
 * Created by prometteur-3 on 24/4/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID = "admin_channel";

    public MyFirebaseMessagingService() {
        super();
    }

    NotificationBean.Result result=new NotificationBean.Result();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
         Intent intent = new Intent(this, LoginActivity.class);
        result.setNotiType(remoteMessage.getData().get("noti_type"));
        result.setNotiAppointmentId(remoteMessage.getData().get("noti_appointment_id"));
        switch (Integer.parseInt(remoteMessage.getData().get("noti_type"))) {
            case 1:// appoinment_request
                intent=new Intent(this, LoginActivity.class).putExtra("objNoti",result);
                break;
            case 2: // appoinment
                intent=new Intent(this, LoginActivity.class).putExtra("objNoti",result);
                if(remoteMessage.getData().get("title").equalsIgnoreCase("Amount Paid"))
                {
                    startActivity(new Intent(this, HistoryDetailsActivity.class).putExtra("pageType","endOtp").putExtra("appId",""+remoteMessage.getData().get("noti_appointment_id")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    if(remoteMessage.getData().get("payment_type").equalsIgnoreCase("2")) {
                        startActivity(new Intent(this, CollectDialogActivity.class).putExtra("pageType", "endOtp").putExtra("appId", "" + remoteMessage.getData().get("noti_appointment_id")).putExtra("payAmt", "" + remoteMessage.getData().get("amount")).putExtra("userName", "" + remoteMessage.getData().get("username")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
                break;
            case 3: // review
                intent=new Intent(this, ProfileActivity.class);
                intent.putExtra("objNoti",result);

                break;
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }
        try {

            String userId = Preferences.getPreferenceValue(MyFirebaseMessagingService.this,"userId");


            if (null != remoteMessage) {
               /* if (remoteMessage.getData().get("messageids").contains(userId)) {*/
                    Log.i("remoteData",remoteMessage.getData().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                            PendingIntent.FLAG_ONE_SHOT);

                    Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.ic_launcher);

                    Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(largeIcon)
                            .setContentTitle(remoteMessage.getData().get("title"))
                            .setContentText(remoteMessage.getData().get("message"))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(remoteMessage.getData().get("message")))
                            .setAutoCancel(true)
                            .setSound(notificationSoundUri)
                            .setContentIntent(pendingIntent);

                    //Set notification color to match your app color template
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                if(Preferences.getPreferenceValue(this,"notification").equalsIgnoreCase("true"))
                {
                    notificationManager.notify(notificationID, notificationBuilder.build());
                }

                int notCount= Preferences.getPreferenceValueInt(getApplicationContext(), NOTCOUNT);
                if(notCount>0) {
                    Preferences.setPreferenceValue(getApplicationContext(), NOTCOUNT, (notCount + 1));
                }else
                {
                    Preferences.setPreferenceValue(getApplicationContext(), NOTCOUNT, 1);
                }
                int count= Preferences.getPreferenceValueInt(getApplicationContext(), APTCOUNT);
                if(count>0) {
                    Preferences.setPreferenceValue(getApplicationContext(), APTCOUNT, (count + 1));
                }else
                {
                    Preferences.setPreferenceValue(getApplicationContext(), APTCOUNT, 1);
                }
                Intent intent1=new Intent("SendToService");
                sendBroadcast(intent1);
               /* } else {
{noti_title=Appointment Request, noti_create_date=2020-03-13 15:04:11, noti_cp_user_id=1, title=Appointment Request, noti_end_user_id=1, noti_appointment_id=78, noti_message=You have new appointment request, message=You have new appointment request, noti_from=2, noti_type=1, noti_branch_id=1, noti_to=1}
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        CharSequence adminChannelName = "My Event";
        String adminChannelDescription = "";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.i("Notification", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
    }
}

