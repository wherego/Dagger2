package com.werocksta.dagger2demo.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.werocksta.dagger2demo.MainApplication;
import com.werocksta.dagger2demo.R;
import com.werocksta.dagger2demo.manager.ApiService;
import com.werocksta.dagger2demo.model.GithubUserInfoCollection;
import com.werocksta.dagger2demo.model.RepoCollection;
import com.werocksta.dagger2demo.presenter.GithubUserInfoPresenter;
import com.werocksta.dagger2demo.presenter.GithubUserInfoPresenterImpl;
import com.werocksta.dagger2demo.presenter.RepoPresenter;
import com.werocksta.dagger2demo.presenter.RepoPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements GithubUserInfoPresenter.View {

    @Inject
    ApiService service;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    GithubUserInfoPresenter presenter;
    ProgressDialog progressDialog;

    public MainFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((MainApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        presenter = new GithubUserInfoPresenterImpl(this, service);
        progressDialog = new ProgressDialog(getContext());
        return view;
    }

    @OnClick(R.id.btnLoad)
    void onClickLoadUserInfo() {
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        presenter.getUserInfo(edtUsername.getText().toString());
        Log.d("Clicked", "Clicked");
    }

    @Override
    public void getUserInfoSuccess(GithubUserInfoCollection userInfo) {
        Log.d("username", userInfo.getUsername());
    }

    @Override
    public void getUserInfoError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUserInfoComplete() {
        progressDialog.cancel();
        Log.d("Completed", "Completed");
    }
}
