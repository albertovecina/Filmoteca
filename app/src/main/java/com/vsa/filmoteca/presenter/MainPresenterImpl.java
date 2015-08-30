package com.vsa.filmoteca.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.vsa.filmoteca.model.Movie;
import com.vsa.filmoteca.model.MoviesFactory;
import com.vsa.filmoteca.view.activity.MainActivity;
import com.vsa.filmoteca.view.MainView;
import com.vsa.filmoteca.R;
import com.vsa.filmoteca.view.dialog.interfaces.OkCancelDialogListener;
import com.vsa.filmoteca.presenter.utils.Constants;
import com.vsa.filmoteca.presenter.utils.NetworkUtils;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by seldon on 10/03/15.
 */

public class MainPresenterImpl extends AsyncHttpResponseHandler implements MainPresenter, OkCancelDialogListener {

    private MainView mMainView;

    private List<Movie> mMoviesList;

    public MainPresenterImpl(MainView mainView){
        mMainView = mainView;
    }

    @Override
    public void onMovieClicked(int position) {
        mMainView.navigateToDetail(mMoviesList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_refresh:
                loadMovies();
                return true;
            case R.id.menu_item_about_us:
                mMainView.showAboutUs();
                return true;
        }
        return false;
    }

    @Override
    public void onResume(Intent intent) {
        Movie movie = (Movie) intent.getSerializableExtra(MainActivity.EXTRA_MOVIE);
        if(movie != null) {
            intent.removeExtra(MainActivity.EXTRA_MOVIE);
            mMainView.navigateToDetail(movie);
        } else {
            if (!mMainView.isListLoaded()) {
                if (!NetworkUtils.isNetworkAvailable(mMainView.getContext())) {
                    mMainView.showWifiRequestDialog(this);
                } else {
                    loadMovies();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void loadMovies() {
        mMainView.stopRefreshing();
        mMainView.showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constants.TIMEOUT_APP);
        client.get(Constants.URL_SOURCE, this);
    }

    @Override
    public Context getContext() {
        return mMainView.getContext();
    }


    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String html = new String(responseBody);
        mMoviesList = MoviesFactory.getMoviesList(html);
        mMainView.showTitle(mMoviesList.size());
        if(mMoviesList.size() < 1)
            mMainView.showNoEventsDialog();
        else
            mMainView.setMovies(mMoviesList);
        mMainView.showChangeLog();
        mMainView.hideProgressDialog();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        mMainView.showTimeOutDialog();
    }

    @Override
    public void onAcceptButtonPressed() {
        mMainView.showWifiSettings();
    }

    @Override
    public void onCancelButtonPressed() {
        mMainView.finish();
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }
}
