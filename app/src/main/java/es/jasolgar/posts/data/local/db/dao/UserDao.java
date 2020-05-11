package es.jasolgar.posts.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.jasolgar.posts.data.model.db.User;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("DELETE FROM users")
    void deleteAll();

    @Delete
    void delete(User comment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User comment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<User> comments);

    @Query("SELECT * FROM users WHERE id = :userId")
    Single<User> getUserById(String userId);
}
