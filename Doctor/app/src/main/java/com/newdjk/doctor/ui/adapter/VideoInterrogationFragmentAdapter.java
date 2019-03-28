package com.newdjk.doctor.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.newdjk.doctor.ui.fragment.ConsultListFragment;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment1;
import com.newdjk.doctor.ui.fragment.VideoInterrogationListFragment2;
import com.newdjk.doctor.ui.fragment.VideoInterrogationScheduleFragment;

import java.util.List;

public class VideoInterrogationFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"待问诊", "问诊中","已完成"};
    private Context context;
    private List<Fragment> mList;
    public VideoInterrogationFragmentAdapter(FragmentManager fm, Context context, List<Fragment> list) {
        super(fm);
        mList = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
       /* if (position ==0) {
            return VideoInterrogationScheduleFragment.newInstance(position);
        }else if (position ==1) {
            return VideoInterrogationListFragment1.newInstance(position);
        }
        else {
            return VideoInterrogationListFragment2.newInstance(position);
        }*/
       return  mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

  /*  @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }*/
}
