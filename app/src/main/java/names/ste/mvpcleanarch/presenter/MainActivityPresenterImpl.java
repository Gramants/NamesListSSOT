package names.ste.mvpcleanarch.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import names.ste.mvpcleanarch.R;
import names.ste.mvpcleanarch.interfaces.Interactor;
import names.ste.mvpcleanarch.interfaces.MainActivityPresenter;
import names.ste.mvpcleanarch.interfaces.MainActivityView;
import names.ste.mvpcleanarch.interfaces.PersistentStorageProxy;
import names.ste.mvpcleanarch.model.entities.ItemDbEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenterImpl implements MainActivityPresenter, Interactor.DatafromServer, LifecycleObserver {

    private PersistentStorageProxy persistence;
    private Interactor interactor;
    private MainActivityView mainActivityView;
    private CompositeDisposable disposeBag;


    public MainActivityPresenterImpl(MainActivityView mainActivityView, Interactor interactor, PersistentStorageProxy persistence) {
        this.mainActivityView = mainActivityView;
        this.interactor = interactor;
        this.persistence = persistence;

        if (mainActivityView instanceof LifecycleOwner) {
            ((LifecycleOwner) mainActivityView).getLifecycle().addObserver(this);
        }
        disposeBag = new CompositeDisposable();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onAttach() {
        loadAllReposFromDb();
    }

    private void loadAllReposFromDb() {
        Disposable disposable = interactor.loadReposFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(this::handleReturnedData, this::handleError, () -> mainActivityView.hideProgress());

        disposeBag.add(disposable);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }


    private void handleReturnedData(List<ItemDbEntity> list) {
        mainActivityView.hideProgress();
        if (list != null && !list.isEmpty()) {
            mainActivityView.onGetRepoListFromDb(list);
        } else {
            mainActivityView.showProgress();
            doFetchAllRepos();
        }
    }


    private void handleError(Throwable error) {
        mainActivityView.hideProgress();
        mainActivityView.writeToStatus(error.getLocalizedMessage());
    }


    @Override
    public void onGetRepoFromRemote(List<ItemDbEntity> data) {
        mainActivityView.hideProgress();
        interactor.updateDb(data);
    }

    @Override
    public void setError(String message) {
        mainActivityView.onError(message);
    }


    @Override
    public void doFetchAllRepos() {
        mainActivityView.showProgress();
        interactor.fetchAllReposUseCase(this);
    }

    @Override
    public void getResultFromDb() {
        onAttach();
    }


    @Override
    public void networkStatusChanged(boolean isNetworkActive) {
        if ((isNetworkActive) && (persistence.getLastNetworkStatus() != isNetworkActive)) {
            mainActivityView.writeToStatus(R.string.online);
            doFetchAllRepos();
            persistence.setNetworkStatus(true);
        } else {
            mainActivityView.writeToStatus(R.string.no_connection);
            if (isNetworkActive) {
                persistence.setNetworkStatus(true);
            } else {
                persistence.setNetworkStatus(false);
            }
        }

    }

}
