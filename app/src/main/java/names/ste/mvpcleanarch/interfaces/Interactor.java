package names.ste.mvpcleanarch.interfaces;


import java.util.List;

import names.ste.mvpcleanarch.model.entities.ItemDbEntity;
import io.reactivex.Flowable;

public interface Interactor {

    void fetchAllReposUseCase(DatafromServer datafromServer);

    Flowable<List<ItemDbEntity>> loadReposFromDb();

    void updateDb(List<ItemDbEntity> data);


    interface DatafromServer {

        void onGetRepoFromRemote(List<ItemDbEntity> data);

        void setError(String message);
    }

}
