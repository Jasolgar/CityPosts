package es.jasolgar.posts.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.jasolgar.posts.data.model.db.Post;
import io.reactivex.Single;

@Dao
public interface PostDao {

    @Query("DELETE FROM posts")
    void deleteAll();

    @Delete
    void delete(Post comment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Post comment);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Post> comments);

    @Query("SELECT * FROM posts WHERE id = :id")
    Single<Post> getPostById(String id);

    @Query("SELECT * FROM posts")
    Single<List<Post>> loadAll();
}
