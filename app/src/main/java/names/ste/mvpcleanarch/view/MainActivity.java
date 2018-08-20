package names.ste.mvpcleanarch.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import names.ste.mvpcleanarch.R;
import names.ste.mvpcleanarch.interfaces.MainActivityView;
import names.ste.mvpcleanarch.model.ConnectionModel;
import names.ste.mvpcleanarch.model.entities.ItemDbEntity;
import names.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;
import names.ste.mvpcleanarch.util.Utils;
import names.ste.mvpcleanarch.util.checknetwork.ConnectionLiveData;
import names.ste.mvpcleanarch.view.adapters.UserListAdapter;

import android.arch.lifecycle.Observer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainActivityView, UserListAdapter.OnItemClickListener {

    @Inject
    MainActivityPresenterImpl presenter;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.repo_list)
    RecyclerView rvRepoList;
    @BindView(R.id.text_status)
    TextView statusText;
    @BindView(R.id.fetch)
    TextView forceFetch;

    private UserListAdapter repoAdapter;
    private ConnectionLiveData connectionLiveData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        connectionLiveData = new ConnectionLiveData(getApplicationContext());
        repoAdapter = new UserListAdapter(this);
        rvRepoList.setAdapter(repoAdapter);
        rvRepoList.setLayoutManager(new LinearLayoutManager(this));
        subscribeUiUpdates();


        forceFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doFetchAllRepos();
            }
        });
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String errorMessage) {
        hideProgress();
        statusMessage(errorMessage);
    }


    private void statusMessage(String msg) {
        statusText.setText(msg);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void writeToStatus(String status) {
        statusMessage(status);
    }


    @Override
    public void onGetRepoListFromDb(List<ItemDbEntity> list) {
        statusMessage(getString(R.string.reposfound, String.valueOf(list.size())));
        Collections.sort(list, new Utils.CustomComparator());
        forceFetch.setVisibility(View.VISIBLE);
        repoAdapter.updateRepoList(list);
    }


    @Override
    public void writeToStatus(int stringId) {
        statusMessage(getString(stringId));
    }



    private void subscribeUiUpdates() {
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                presenter.networkStatusChanged(connection.mActive);

            }
        });

    }

    @Override
    public void onItemClick(ItemDbEntity item) {
        Toast.makeText(getApplicationContext(), getString(R.string.toastmsg,item.getName()), Toast.LENGTH_SHORT).show();

    }
}