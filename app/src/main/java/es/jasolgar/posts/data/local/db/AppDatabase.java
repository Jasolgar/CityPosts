package es.jasolgar.posts.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.jasolgar.posts.data.local.db.dao.CommentDao;
import es.jasolgar.posts.data.local.db.dao.PostDao;
import es.jasolgar.posts.data.local.db.dao.UserDao;
import es.jasolgar.posts.data.model.db.Comment;
import es.jasolgar.posts.data.model.db.Post;
import es.jasolgar.posts.data.model.db.User;

@Database(entities = {Post.class, User.class, Comment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PostDao postDao();

    public abstract UserDao userDao();

    public abstract CommentDao commentDao();

}