package names.ste.mvpcleanarch.interfaces;


import java.util.List;


import names.ste.mvpcleanarch.model.entities.ItemDbEntity;

public interface MainActivityView {
    void showProgress();

    void hideProgress();

    void onError(String type);

    void writeToStatus(String status);

    void onGetRepoListFromDb(List<ItemDbEntity> list);

    void writeToStatus(int stringId);
}
