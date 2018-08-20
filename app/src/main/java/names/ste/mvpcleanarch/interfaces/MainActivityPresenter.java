package names.ste.mvpcleanarch.interfaces;


public interface MainActivityPresenter {

    void doFetchAllRepos();

    void getResultFromDb();

    void networkStatusChanged(boolean mActive);
}
