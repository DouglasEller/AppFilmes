package douglas.com.appfilmes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import douglas.com.appfilmes.Adapter.RecyclerFilmesAdapter;
import douglas.com.appfilmes.fragments.FilmePager;
import douglas.com.appfilmes.model.Filme;

/**
 * Created by douglasEller on 30/01/17.
 */

public class FullDescription extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public List<Filme> listaFilmes;
    private int position = 0;
    private FragmentManager fragmentManager;
    public Toolbar toolbarPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_description);

        fragmentManager = this.getSupportFragmentManager();

        listaFilmes = RecyclerFilmesAdapter.ItemHolder.getFilmesHolder();
        position = this.getIntent().getIntExtra("filmeEscolhido", 0);

        toolbarPager = (Toolbar) findViewById(R.id.toolbarPager);
        toolbarPager.setTitle(listaFilmes.get(position).getTitulo());
        setSupportActionBar(toolbarPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarPager.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager, listaFilmes);

        mViewPager = (ViewPager) findViewById(R.id.viewPagerContent);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        toolbarPager.setTitle(listaFilmes.get(position).getTitulo());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Filme> filmeList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, List<Filme> filmes) {
            super(fm);
            filmeList = filmes;
        }

        @Override
        public Fragment getItem(int position) {
            return FilmePager.newInstance(filmeList.get(position));
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return filmeList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }

    }
}

