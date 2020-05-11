package es.jasolgar.posts.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.jasolgar.posts.data.model.db.Comment;
import io.reactivex.Single;

@Dao
public interface CommentDao {

    @Query("DELETE FROM comments")
    void deleteAll();

    @Delete
    void delete(Comment comment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Comment> comments);

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    Single<List<Comment>> getCommentsByPostId(String postId);
}
