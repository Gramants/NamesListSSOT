package names.ste.mvpcleanarch.interfaces;

import java.util.List;

import io.reactivex.Observable;
import names.ste.mvpcleanarch.model.Item;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {

    @GET("/users")
    Observable<List<Item>> getAllNames();

}
