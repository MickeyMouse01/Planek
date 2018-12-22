package com.example.thomas.plan.selectedTask;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.GlideApp;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.loginAndRegister.LoginActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SelectedTaskActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SelectedTaskVieModel mViewModel;
    private int positionOfSelectedTask;
    private ViewPager mViewPager;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mViewModel = obtainViewModel(this);
        String viewPlanId = intent.getStringExtra("PlanId");
        positionOfSelectedTask = intent.getIntExtra("positionOfTask", 0);
        mViewModel.setViewedPlanId(viewPlanId);
        showDialog("Loading data");
        mViewPager =  findViewById(R.id.container);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewModel.getListOfTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mViewPager.setCurrentItem(positionOfSelectedTask);
                hideDialog();
            }
        });

    }

    private static SelectedTaskVieModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(SelectedTaskVieModel.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_selected_task;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,3);
        return super.onOptionsItemSelected(item);
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

        public SelectedTaskVieModel mViewModel;
        private List<Task> listOfTasks = new ArrayList<>();
        private TextView textView;
        private ConstraintLayout constraintLayout;
        private TextView txtIsDone;
        private ImageView previewImage;
        private ActionItemListener actionItemListener;

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

            mViewModel = SelectedTaskActivity.obtainViewModel(getActivity());
            final int position = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
            actionItemListener = new ActionItemListener<Task>() {
                @Override
                public void onCheckedClick(Task item) {

                }

                @Override
                public void onItemClick(Task item) {
                    Task task = listOfTasks.get(position);
                    boolean isPassed = !task.isPassed();
                    task.setPassed(isPassed);
                    mViewModel.saveTask(task);
                }

                @Override
                public void onItemInfoClick(Task item) {

                }

                @Override
                public void onItemDeleteClick(Task item) {

                }
            };

            mViewModel.getListOfTasks().observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    listOfTasks = tasks;
                    int position = getArguments().getInt(ARG_SECTION_NUMBER) - 1;
                    setText(position);
                    setPassed(position);
                    setImageOfTask(position);
                }
            });

            textView = rootView.findViewById(R.id.preview_name);
            previewImage = rootView.findViewById(R.id.preview_image);
            constraintLayout = rootView.findViewById(R.id.viewpager_ConstraintLayout);
            txtIsDone = rootView.findViewById(R.id.preview_isDone);
            txtIsDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtIsDone.setBackgroundResource(R.color.isPassed);
                    txtIsDone.setText("Splněno");
                    actionItemListener.onItemClick(null);
                }
            });
            return rootView;
        }

        private void setPassed(int position){
            boolean isPassed = listOfTasks.get(position).isPassed();
            if (isPassed) {
                constraintLayout.setBackgroundResource(R.color.isPassed);
                txtIsDone.setBackgroundResource(R.color.isPassed);
                txtIsDone.setText("Splněno");
            }
        }

        private void setText(int position) {
            textView.setText(listOfTasks.get(position).getName());
        }

        private void setImageOfTask(int position){
            if (listOfTasks.get(position).isImageSet()){
                StorageReference ref = FirebaseStorage.getInstance()
                        .getReference()
                        .child(listOfTasks.get(position).getNameOfImage());

                GlideApp.with(this)
                        .load(ref)
                        .into(previewImage);
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
            return mViewModel.getListOfTasks().getValue().size();
        }
    }
}
