package names.ste.mvpcleanarch.domain;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import names.ste.mvpcleanarch.data.database.DeleteRecords;
import names.ste.mvpcleanarch.data.database.ProjectDb;
import names.ste.mvpcleanarch.data.database.UsersDao;
import names.ste.mvpcleanarch.data.database.UpdateRecords;
import names.ste.mvpcleanarch.interfaces.Api;
import names.ste.mvpcleanarch.interfaces.Interactor;
import names.ste.mvpcleanarch.model.Item;
import names.ste.mvpcleanarch.model.entities.ItemDbEntity;
import io.reactivex.Flowable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;


public class InteractorImpl implements Interactor {

    private Api api;
    private UsersDao namesDao;
    private ProjectDb projectDb;


    public InteractorImpl(Api api, ProjectDb projectDb, UsersDao namesDao) {
        this.api = api;
        this.namesDao = namesDao;
        this.projectDb = projectDb;
    }


    @Override
    public void fetchAllReposUseCase(DatafromServer datafromServer) {

        api.getAllNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            datafromServer.onGetRepoFromRemote(Lists.transform(data, dbEntity));
                        },
                        throwable -> {
                            datafromServer.setError(throwable.getMessage());
                        }
                );


    }


    public Function<Item, ItemDbEntity> dbEntity =
            new Function<Item, ItemDbEntity>() {
                @Override
                public ItemDbEntity apply(Item item) {
                    return new ItemDbEntity(item.getId(), item.getName());
                }

            };

    @Override
    public Flowable<List<ItemDbEntity>> loadReposFromDb() {
        return namesDao.getAllRepos();
    }


    @Override
    public void updateDb(List<ItemDbEntity> sanitizedRecords) {
        new UpdateRecords() {

            @Override
            protected void updateRecords() {
                projectDb.beginTransaction();
                try {
                    namesDao.updateData(sanitizedRecords);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };

    }


}
