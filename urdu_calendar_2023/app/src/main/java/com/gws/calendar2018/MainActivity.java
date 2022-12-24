package com.gws.calendar2018;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;


public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static int[] CALIMAGES;
    public static String[] CALNAMES;
    private LinearLayout buttonPanel;
    private TextView tvMonthName;
    private AdView mAdView;
    PhotoViewAttacher mAttacher;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    String currentVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        tvMonthName = (TextView) findViewById(R.id.tvMonthName);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        ImageView ivPrev = (ImageView) findViewById(R.id.ivPrev);
        ImageView ivNext = (ImageView) findViewById(R.id.ivNext);
        ImageView ivStar = (ImageView) findViewById(R.id.ivStar);
        ImageView flipImage = (ImageView) findViewById(R.id.flipImage);
        // ImageView ivInfo = (ImageView) findViewById(R.id.ivInfo);


        buttonPanel = (LinearLayout) findViewById(R.id.llButtonPanel);
        buttonPanel.bringToFront();
        mAdView = (AdView) findViewById(R.id.adView);
        //  mAdView.setAdUnitId(getString(R.string.banner_add_id));
        // mAdView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        CALIMAGES = new int[]{
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
                R.drawable.dec

        };
        CALNAMES = new String[]{
                "December",
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
        tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
            }
        });
        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
            }
        });
        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.gws.calendar2018")));
            }
        });
        ;

        flipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDuaOfMonthDialog(mViewPager.getCurrentItem());
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initMenuFragment();
        addFragment(new MainFragment(), true, R.id.container);

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            new GetVersionCode().execute();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        if (year == 2023) {
            mViewPager.setCurrentItem(month + 1, true);
            tvMonthName.setText(CALNAMES[month + 1]);
        } else if (year == 2022) {
            mViewPager.setCurrentItem(0, true);
            tvMonthName.setText("December");

        } else {
            mViewPager.setCurrentItem(1, true);
            tvMonthName.setText(CALNAMES[0]);
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }


    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject("Close Menu");
        close.setResource(R.drawable.close);


        MenuObject duaOfMaonth = new MenuObject("Nafil salat & Duas");
        duaOfMaonth.setResource(R.drawable.duahands);


        MenuObject holidaysList = new MenuObject("Special Days");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.holidays);
        holidaysList.setBitmap(b);

        /*MenuObject islamicLibrary = new MenuObject("Islamic Library");
        islamicLibrary.setResource(R.drawable.library);*/


        MenuObject rateUs = new MenuObject("Rate Us");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.rateus));
        rateUs.setDrawable(bd);

        MenuObject aboutUs = new MenuObject("About Us");
        aboutUs.setResource(R.drawable.aboutus);

        MenuObject shareApp = new MenuObject("Share App");
        shareApp.setResource(R.drawable.share);

        menuObjects.add(close);
        menuObjects.add(duaOfMaonth);
        menuObjects.add(holidaysList);
        //menuObjects.add(islamicLibrary);
        menuObjects.add(rateUs);
        menuObjects.add(aboutUs);
        menuObjects.add(shareApp);
        return menuObjects;
    }


    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        if (position == 1) {
            //showDuaOfMonthDialog(mViewPager.getCurrentItem());
           /* UserFunctions userFunctions = new UserFunctions();
            userFunctions.showDuaOfMonthDialog(mViewPager.getCurrentItem(), MainActivity.this);*/
            Intent intent = new Intent(MainActivity.this, MonthDua.class);
            startActivity(intent);


        } else if (position == 2) {
            UserFunctions.showHolidaysList(MainActivity.this);
        } else if (position == 6) {
            /*String packageName = "com.gwsoftware.alahazratkakalam";
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if(intent != null) {
                startActivity(intent);
            }*/
            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.gwsoftware.alahazratkakalam")));
        } else if (position == 3) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.gws.calendar2018")));
        } else if (position == 4) {
            UserFunctions.showAboutUsDialog(MainActivity.this);
        } else if (position == 5) {
            UserFunctions.shareApp(MainActivity.this);
        }

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.cal_images);
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
            photoViewAttacher.update();
            int i = getArguments().getInt(ARG_SECTION_NUMBER);
            imageView.setImageDrawable(getResources().getDrawable(CALIMAGES[i]));


            // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //tvMonthName.setText(CALNAMES[position]);
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 13;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }*/

            return null;
        }

    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    UserFunctions.showUpdateAlert(MainActivity.this);
                }
            }
            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);
        }
    }
}
