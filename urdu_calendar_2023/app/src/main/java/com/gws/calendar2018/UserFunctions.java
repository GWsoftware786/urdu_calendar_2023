package com.gws.calendar2018;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gws.calendar2018.models.Holidays;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Maheboob on 1/20/2018.
 */

public class UserFunctions extends AppCompatActivity {

    private static RecyclerView recyclerView;
    public static List<Holidays> mHolidaysList = new ArrayList<>();
    public static HolidaysListAdapter mHolidaysListAdapter;
    private static final String KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE";
    private static final String KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS";


    public static String[] CALNAMES = new String[]{
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };
    public static int[] CALIMAGES = new int[]{
            R.drawable.dec_2022,
            R.drawable.jan,
            R.drawable.feb,
            R.drawable.march,
            R.drawable.apr,
            R.drawable.may,
            R.drawable.june,
            R.drawable.july,
            R.drawable.aug,
            R.drawable.sept,
            R.drawable.oct,
            R.drawable.nov,
            R.drawable.dec_2022

    };


    public static void showHolidaysList(Context context) {
        final View dialogView = View.inflate(context, R.layout.holidays_list, null);
        final Dialog dialog = new Dialog(context, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.holidays_list);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mHolidaysListAdapter = new HolidaysListAdapter(mHolidaysList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mHolidaysListAdapter);

        prepareHolidaysData();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    return true;
                }

                return false;
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    public static void showAboutUsDialog(Context context) {
        final View dialogView = View.inflate(context, R.layout.about_us, null);
        final Dialog dialog = new Dialog(context, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);

        JustifiedTextView aboutUs = (JustifiedTextView) dialog.findViewById(R.id.aboutUs);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        aboutUs.setText(R.string.aboutUs);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    return true;
                }

                return false;
            }
        });
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    public static class ShareApp extends AsyncTask<Void, Void, Void> {
        Context context;
        private ProgressDialog dialog;

        public ShareApp(Context context) {
            this.context = context;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            shareApp(context);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    private static void prepareHolidaysData() {

        if (!mHolidaysList.isEmpty() && mHolidaysList.size() > 0) {
            mHolidaysList.clear();
        }

        Holidays holiday = new Holidays();
        holiday.setHolidayName("Republic Day");
        holiday.setHolidayDate("26 Jan 2023");
        mHolidaysList.add(holiday);



        holiday = new Holidays();
        holiday.setHolidayName("Shab E Me'raj");
        holiday.setHolidayDate("18 Feb 2023");
        mHolidaysList.add(holiday);

        holiday = new Holidays();
        holiday.setHolidayName("Maha ShivRatri");
        holiday.setHolidayDate("18 Feb 2023");
        mHolidaysList.add(holiday);


        holiday = new Holidays();
        holiday.setHolidayName("Shab E Bara'at");
        holiday.setHolidayDate("7 March 2023");
        mHolidaysList.add(holiday);


        holiday = new Holidays();
        holiday.setHolidayName("Holi");
        holiday.setHolidayDate("7 March 2023");
        mHolidaysList.add(holiday);


        holiday = new Holidays();
        holiday.setHolidayName("Good Friday");
        holiday.setHolidayDate("7 April 2023");
        mHolidaysList.add(holiday);


        holiday = new Holidays();
        holiday.setHolidayName("Eid Ul Fitr");
        holiday.setHolidayDate("22 April 2023");
        mHolidaysList.add(holiday);

        holiday = new Holidays();
        holiday.setHolidayName("Bakra Eid");
        holiday.setHolidayDate("29 June 2023");
        mHolidaysList.add(holiday);

        holiday = new Holidays();
        holiday.setHolidayName("Aashura");
        holiday.setHolidayDate("29 July 2023");
        mHolidaysList.add(holiday);

        holiday = new Holidays();
        holiday.setHolidayName("Independence Day");
        holiday.setHolidayDate("15 Aug 2023");
        mHolidaysList.add(holiday);

        holiday = new Holidays();
        holiday.setHolidayName("Eid E Milad");
        holiday.setHolidayDate("28 Sept 2023");
        mHolidaysList.add(holiday);


       /* holiday = new Holidays();
        holiday = new Holidays();
        holiday.setHolidayName("Hajj");
        holiday.setHolidayDate("29 July – 3 Aug. 2020");
        mHolidaysList.add(holiday);
        holiday = new Holidays();
        holiday.setHolidayName("Eid al-Adha");
        holiday.setHolidayDate("1 Aug 2020");
        mHolidaysList.add(holiday);
        holiday = new Holidays();
        holiday.setHolidayName("Aashura");
        holiday.setHolidayDate("30 Aug 2020");
        mHolidaysList.add(holiday);
        holiday = new Holidays();
        holiday.setHolidayName("Urs Huzoor Mufti e Azam");
        holiday.setHolidayDate("2 Sept 2020");
        mHolidaysList.add(holiday);


        holiday = new Holidays();
        holiday.setHolidayName("Jashn Huzoor Gaus E Azam");
        holiday.setHolidayDate("27 Nov 2020");
        mHolidaysList.add(holiday);
*/

        mHolidaysListAdapter.notifyDataSetChanged();
    }

    public static void showUpdateAlert(final Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("New Version Available");

        // set dialog message
        alertDialogBuilder
                .setMessage("Please update new version app.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.gws.calendar2018")));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
