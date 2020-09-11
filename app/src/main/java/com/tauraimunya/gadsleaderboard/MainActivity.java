package com.tauraimunya.gadsleaderboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tauraimunya.gadsleaderboard.UTILS.UniversalImageLoader;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LearningLeadersFrag(), getString(R.string.tab_title_1));
        adapter.addFragment(new SkillLeadersFrag(), getString(R.string.tab_title_2));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Button submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SubmitProjectActivity.class);
            startActivity(intent);
        });

       initImageLoader();
    }

    private void initImageLoader() {
       UniversalImageLoader imageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(imageLoader.getConfig());
    }
}