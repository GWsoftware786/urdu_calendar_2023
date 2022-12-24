package com.gws.calendar2018;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rd.PageIndicatorView;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MonthDua extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private static int[] CALIMAGES;
    public static String[] CALNAMES;
    private LinearLayout buttonPanel;
    private TextView tvMonthName;
    private TextView duaName;
    PhotoViewAttacher mAttacher;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    PageIndicatorView pageIndicatorView;
    ImageView goBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_dua);
        CALIMAGES = new int[]{
                R.drawable.ashura,
                R.drawable.milad,
                R.drawable.gyarvi,
                R.drawable.shab_e_mehraj,
                R.drawable.shab_e_barath,
                R.drawable.shab_e_qadr,
                R.drawable.bakra_eid

        };
        CALNAMES = new String[]{
                "Dua E Ashura",
                "Milad",
                "Gyarvi",
                "Shab E Meraj",
                "Shab E Barat",
                "Shab E Qdar",
                "Bakra Eid"

        };
        fragmentManager = getSupportFragmentManager();
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        tvMonthName = (TextView) findViewById(R.id.tvMonthName);
        duaName = (TextView) findViewById(R.id.dua_name);
        goBack = (ImageView) findViewById(R.id.backButton);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setCount(CALIMAGES.length);
        pageIndicatorView.setSelection(0);

        ImageView ivPrev = (ImageView) findViewById(R.id.ivPrev);
        ImageView ivNext = (ImageView) findViewById(R.id.ivNext);
        buttonPanel = (LinearLayout) findViewById(R.id.llButtonPanel);
        buttonPanel.bringToFront();



       // tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
        duaName.setText(CALNAMES[mViewPager.getCurrentItem()]);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity( new Intent(MonthDua.this,MainActivity.class));
            }
        });
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
             //   tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
                duaName.setText(CALNAMES[mViewPager.getCurrentItem()]);
            }
        });
        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
             //   tvMonthName .setText(CALNAMES[mViewPager.getCurrentItem()]);
                duaName .setText(CALNAMES[mViewPager.getCurrentItem()]);
            }
        });


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // tvMonthName.setText(CALNAMES[mViewPager.getCurrentItem()]);
                duaName.setText(CALNAMES[mViewPager.getCurrentItem()]);
                pageIndicatorView.setSelection(mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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
            return CALIMAGES.length;
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


}
