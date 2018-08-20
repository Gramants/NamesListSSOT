package names.ste.mvpcleanarch.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import names.ste.mvpcleanarch.model.entities.ItemDbEntity;
import names.ste.mvpcleanarch.util.Config;
import io.reactivex.Flowable;


@Dao
public abstract class UsersDao {
    @Query("SELECT * FROM " + Config.TABLE_USERS)
    public abstract Flowable<List<ItemDbEntity>> getAllRepos();

    @Query("DELETE FROM " + Config.TABLE_USERS)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ItemDbEntity> items);

    @Transaction
    public void updateData(List<ItemDbEntity> items) {
        deleteAll();
        insert(items);
    }

}
