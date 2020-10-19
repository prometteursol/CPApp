package com.prometteur.cpapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prometteur.cpapp.R;
import com.prometteur.cpapp.beans.OngoingBean;
import com.prometteur.cpapp.dialogs.ConfirmDialogActivity;
import com.prometteur.cpapp.onboarding.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.prometteur.cpapp.utils.Constants.APTCOUNT;
import static com.prometteur.cpapp.utils.Preferences.getClearPrefs;

public class Utils {

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = null;
            try {
                is = context.getAssets().open("cities.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static byte[] getCompressedFile(File path) {

//        File f = new File(path);
        BitmapFactory.Options btf = new BitmapFactory.Options();

        Bitmap bt = BitmapFactory.decodeFile(path.getAbsolutePath(), btf);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (bt != null) {
            bt.compress(Bitmap.CompressFormat.JPEG, 60, out);
            byte[] bta = out.toByteArray();
            Log.v("bytearry", bta.toString());
            return bta;

        } else {
            Log.i("Bitmap", "bitmap is null");
            return out.toByteArray();
        }


    }


    public static void getStrikeString(TextView someTextView) {
        someTextView.setPaintFlags(someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void getResizedDrawable(Context context, int imgId, TextView textView, Button button, EditText editText, int size) {
        Drawable img = context.getResources().getDrawable(imgId);
        if (button != null) {
            img.setBounds(0, 0, img.getIntrinsicWidth() * button.getMeasuredHeight() / img.getIntrinsicHeight(), button.getMeasuredHeight());
            button.setCompoundDrawables(img, null, null, null);
        }
        if (textView != null) {
            img.setBounds(0, 0, (int) textView.getContext().getResources().getDimension(size), (int) textView.getContext().getResources().getDimension(size));
            textView.setCompoundDrawables(img, null, null, null);
        }
        if (editText != null) {
            img.setBounds(0, 0, img.getIntrinsicWidth() * editText.getMeasuredHeight() / img.getIntrinsicHeight(), editText.getMeasuredHeight());
            editText.setCompoundDrawables(img, null, null, null);
        }

    }


    public static void getBottomNavigationCount(Context mContext, BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView mbottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View chatBadge = LayoutInflater.from(mContext).inflate(R.layout.layout_appointment,
                mbottomNavigationMenuView, false);
        BottomNavigationItemView itemView = (BottomNavigationItemView) mbottomNavigationMenuView.getChildAt(1);

        TextView tvUnreadChats = chatBadge.findViewById(R.id.tvUnreadChats);
        tvUnreadChats.setTextColor(mContext.getResources().getColor(R.color.white));
        if(Preferences.getPreferenceValueInt(mContext,APTCOUNT)!=0) {
            if(Preferences.getPreferenceValueInt(mContext,APTCOUNT)>9) {
                tvUnreadChats.setText("9+");//String that you want to show in badge
            }else {
                tvUnreadChats.setText("" + Preferences.getPreferenceValueInt(mContext, APTCOUNT));//String that you want to show in badge
            }
            itemView.addView(chatBadge);
        }else
        {
            tvUnreadChats.setText("0");
            tvUnreadChats.setBackground(mContext.getResources().getDrawable(R.drawable.bg_tranceparent_circle));
            tvUnreadChats.setTextColor(mContext.getResources().getColor(R.color.transparent));
            Log.i("Children Count",""+itemView.getChildCount());
            View view=itemView.getChildAt(2);
            itemView.removeView(view);
        }

    }


    //validations

    public static boolean isValidPassword(final String password) {


        if (password.length() >= 8) {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
            //Pattern eight = Pattern.compile (".{8}");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        } else
            return false;


    }

    public static MultipartBody.Part[] getImageBody(String strPath) {
        if (strPath != null) {
            if (!strPath.isEmpty()) {
                String[] strPathArr = strPath.split(",");
                MultipartBody.Part[] body = new MultipartBody.Part[strPathArr.length];
                for (int i = 0; i < strPathArr.length; i++) {
                    File file = new File(strPathArr[i]);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body[i] = MultipartBody.Part.createFormData("surv_img[]", file.getName(), requestFile);
                }
                return body;
            } else {
                return null;
            }

        }
        return null;
    }

    public static RequestBody getOtherFields(String data) {
        // add another part within the multipart request
        if (data == null) {
            data = "";
        }
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), data);
        return body;
    }


    public static Dialog showProgress(Context context, int txtType) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_progress);
        // set the custom dialog components - text, image and button
        //ImageView ivLogo = (ImageView) dialog.findViewById(R.id.ivLogo);
        TextView text = (TextView) dialog.findViewById(R.id.tvProgressMsg);
       /* if (txtType == 0) {
            text.setText(context.getResources().getString(R.string.please_wait));
        } else {
            text.setText(context.getResources().getString(R.string.loading));
        }*/
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        //image.setImageResource(R.drawable.iclau);

       // Glide.with(context).load(R.drawable.salon_gif_logo).into(ivLogo);

        return dialog;

    }
    public static Dialog dialog=null;
    public static void showNoInternetDialog(Context context) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_no_internet);
        dialog.setCancelable(true);
        dialog.show();
    }

    public static void setNoInternetMsg(RecyclerView recyclerView, ImageView ivNoAppoint) {


            recyclerView.setVisibility(View.GONE);
            ivNoAppoint.setVisibility(View.VISIBLE);

    }
    public static void setEmptyMsg(List mDataList, RecyclerView recyclerView, ImageView ivNoAppoint) {
        if (mDataList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            ivNoAppoint.setVisibility(View.GONE);

        } else {
            recyclerView.setVisibility(View.GONE);
            ivNoAppoint.setVisibility(View.VISIBLE);
        }
    }


 public static void setEmptyMsg(List<OngoingBean.Result> mDataList, ViewPager viewPager, ImageView ivNoAppoint) {
        if (mDataList.size() > 0) {
            viewPager.setVisibility(View.VISIBLE);
            ivNoAppoint.setVisibility(View.GONE);

        } else {
            viewPager.setVisibility(View.GONE);
            ivNoAppoint.setVisibility(View.VISIBLE);
        }
    }


   /* public static List<String> getTimeSlots(String strTimeSlots) {

        List<String> timeSlots = new ArrayList<>();
        String strTimeSlotsStart = strTimeSlots.split("-")[0];
        String strTimeSlotsEnd = strTimeSlots.split("-")[1];

        *//*String timeValue = "2015-10-28T18:37:04.899+05:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");*//*
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Calendar startCalendar = Calendar.getInstance();
            try {
                startCalendar.setTime(sdf.parse(strTimeSlotsStart));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (startCalendar.get(Calendar.MINUTE) == 0) {
                startCalendar.set(Calendar.MINUTE, 00);
            } else if (startCalendar.get(Calendar.MINUTE) < 15) {
                startCalendar.set(Calendar.MINUTE, 15);
            } else {
                startCalendar.add(Calendar.MINUTE, 15); // overstep hour and clear minutes
                startCalendar.clear(Calendar.MINUTE);
            }

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(sdf.parse(strTimeSlotsEnd));

            endCalendar.clear(Calendar.MINUTE);
            endCalendar.clear(Calendar.SECOND);
            endCalendar.clear(Calendar.MILLISECOND);


            SimpleDateFormat slotTimeRes = new SimpleDateFormat("hh:mm a");

            String slotEndTime = slotTimeRes.format(startCalendar.getTime());

            slotEndTime = slotEndTime.replace("am", "AM");
            slotEndTime = slotEndTime.replace("pm", "PM");
            timeSlots.add(slotEndTime);
            while (endCalendar.after(startCalendar)) {


                startCalendar.add(Calendar.MINUTE, 15);
                slotEndTime = slotTimeRes.format(startCalendar.getTime());
                slotEndTime = slotEndTime.replace("am", "AM");
                slotEndTime = slotEndTime.replace("pm", "PM");
                timeSlots.add(slotEndTime);
                Log.d("DATE", slotEndTime);
            }

        } catch (Exception e) {
            // date in wrong format
        }
        return timeSlots;
    }*/

    public static List<String> getTimeSlots(String strTimeSlots) {

        List<String> timeSlots = new ArrayList<>();
        String strTimeSlotsStart = strTimeSlots.split("-")[0];
        String strTimeSlotsEnd = strTimeSlots.split("-")[1];

        /*String timeValue = "2015-10-28T18:37:04.899+05:30";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");*/
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Calendar startCalendar = Calendar.getInstance();
            try {
                startCalendar.setTime(sdf.parse(strTimeSlotsStart));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (startCalendar.get(Calendar.MINUTE) == 0) {
                startCalendar.set(Calendar.MINUTE, 00);
            } else if (startCalendar.get(Calendar.MINUTE) < 15) {
                startCalendar.set(Calendar.MINUTE, 15);
            } else if (startCalendar.get(Calendar.MINUTE) >= 15 && startCalendar.get(Calendar.MINUTE) < 30) {
                startCalendar.set(Calendar.MINUTE, 30); // overstep hour and clear minutes
                // startCalendar.clear(Calendar.MINUTE);
            } else if (startCalendar.get(Calendar.MINUTE) >= 30 && startCalendar.get(Calendar.MINUTE) < 45) {
                startCalendar.set(Calendar.MINUTE, 45); // overstep hour and clear minutes
//                startCalendar.clear(Calendar.MINUTE);
            } else {
                startCalendar.clear(Calendar.MINUTE);
                startCalendar.add(Calendar.HOUR, 1);
            }

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.clear(Calendar.MINUTE);
            endCalendar.clear(Calendar.SECOND);
            endCalendar.clear(Calendar.MILLISECOND);
            endCalendar.setTime(sdf.parse(strTimeSlotsEnd));

            SimpleDateFormat slotTimeRes = new SimpleDateFormat("hh:mm a");

            String slotEndTime = slotTimeRes.format(startCalendar.getTime());

            slotEndTime = slotEndTime.replace("am", "AM");
            slotEndTime = slotEndTime.replace("pm", "PM");
            timeSlots.add(slotEndTime);
            while (endCalendar.after(startCalendar)) {


                startCalendar.add(Calendar.MINUTE, 15);
                slotEndTime = slotTimeRes.format(startCalendar.getTime());
                slotEndTime = slotEndTime.replace("am", "AM");
                slotEndTime = slotEndTime.replace("pm", "PM");
                timeSlots.add(slotEndTime);
                Log.d("DATE", slotEndTime);
            }

        } catch (Exception e) {
            // date in wrong format
        }
        if(timeSlots.size()>0) {
            timeSlots.remove(timeSlots.size() - 1);
        }
        return timeSlots;
    }
    public static String getAppDetDate(String date) {
        SimpleDateFormat sourceDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat destDate = new SimpleDateFormat("dd MMMM");

        try {
            Date date1 = sourceDate.parse(date);
            date = destDate.format(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getReviewDate(Context context,String date) {
        SimpleDateFormat sourceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String formattedDate="";
        try {
            Date date1 = sourceDate.parse(date);
            formattedDate= (String) DateUtils.getRelativeTimeSpanString(date1.getTime(),new Date().getTime(),DateUtils.MINUTE_IN_MILLIS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }


    public static void showFailToast(Context context, String msg) {
       // Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show();
        Toasty.custom(context, msg, context.getDrawable(R.drawable.ic_close_black_24dp), context.getResources().getColor(R.color.redLight),context.getResources().getColor(R.color.white),  Toast.LENGTH_SHORT, true, true).show();
    }
    public static void showSuccessToast(Context context, String msg) {
       // Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show();
        Toasty.custom(context, msg, context.getDrawable(R.drawable.ic_tick_service), context.getResources().getColor(R.color.green),context.getResources().getColor(R.color.white),  Toast.LENGTH_SHORT, true, true).show();
    }
    public static void showInfoToast(Activity context, String msg) {
        Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show();

    }

    public static void logout(Context context) {
        getClearPrefs(context);
        context.startActivity(new Intent(context, LoginActivity.class));
        ((Activity)context).finishAffinity();

    }

    public static String getTimeShow24to12HR(String time) {
        time = time.trim();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat slotTimeRes = new SimpleDateFormat("hh:mm a");

        Date changeFTime = null;
        try {
            changeFTime = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String timeIn12Hr = slotTimeRes.format(changeFTime.getTime());
        timeIn12Hr = timeIn12Hr.replace("am", "AM").replace("pm", "PM");
        return timeIn12Hr;
    }
    public static String getTimeShow12to24HR(String time) {
        time = time.trim();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat slotTimeRes = new SimpleDateFormat("hh:mm a");

        Date changeFTime = null;
        try {
            changeFTime = slotTimeRes.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String timeIn24Hr = sdf.format(changeFTime.getTime());
        return timeIn24Hr;
    }


    public static String getDateShowDDMMMYYYY(String date) {
        SimpleDateFormat sourceDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat destDate = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date date1 = sourceDate.parse(date);
            date = destDate.format(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = Utils.getConnectivityStatus(context);
        String status = null;
        if (conn == Utils.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == Utils.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == Utils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static boolean getNoShowTime(String aptDate,String aptTime,String noShowTime) {
        SimpleDateFormat sourceDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = sourceDate.parse(aptDate+" "+aptTime);
            long longNoShowTime=Long.parseLong(noShowTime)*60*1000;
            if((date1.getTime()+longNoShowTime)<new Date().getTime())
            {
               return true;
            }else
            {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
