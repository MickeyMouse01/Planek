package com.example.thomas.plan.previewTasks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;

import java.util.ArrayList;
import java.util.List;

public class PreviewTasksActivity extends BaseActivity implements TasksListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private PreviewTasksViewModel mViewModel;
    private String viewPlanId;
    private String NAME_OF_CLASS = getClass().getName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        viewPlanId = intent.getStringExtra("PlanId");
        mViewModel.setViewedPlanId(viewPlanId);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        showDialog("Loading data");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mViewModel);


        mViewModel.getListOfTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
                hideDialog();

            }
        });

    }

    private static PreviewTasksViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(PreviewTasksViewModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_preview_tasks;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTasks(List<Task> tasks) {
        mViewModel.getListOfTasks().setValue(tasks);
    }

    @Override
    public void getTaskInfo() {

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
        private static final String LIST_SIZE = "list_size";
        private String NAME_OF_CLASS = getClass().getName();


        public PreviewTasksViewModel mViewModel;
        private List<Task> listOfTasks = new ArrayList<>();
        private TextView textView;
        private TasksListener tasksListener;

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
                                 final Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_preview_tasks, container, false);


            mViewModel = PreviewTasksActivity.obtainViewModel(getActivity());
            mViewModel.getListOfTasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    listOfTasks = tasks;
                    setText();
                }
            });

            textView = rootView.findViewById(R.id.preview_name);
            return rootView;
        }

        private void setText() {
            textView.setText(listOfTasks.get(getArguments().getInt(ARG_SECTION_NUMBER) - 1).getName());
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private PreviewTasksViewModel tasks;
        private int sizeOfList;

        public SectionsPagerAdapter(FragmentManager fm, PreviewTasksViewModel tasks) {
            super(fm);
            this.tasks = tasks;
        }



        //musim pockat na nacteni
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mViewModel.getListOfTasks().getValue().size();
        }
    }
}
