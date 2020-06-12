package es.jasolgar.cityposts.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.jasolgar.cityposts.data.local.db.dao.CommentDao;
import es.jasolgar.cityposts.data.local.db.dao.PostDao;
import es.jasolgar.cityposts.data.local.db.dao.UserDao;
import es.jasolgar.cityposts.data.model.db.Comment;
import es.jasolgar.cityposts.data.model.db.Post;
import es.jasolgar.cityposts.data.model.db.User;

@Database(entities = {Post.class, User.class, Comment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PostDao postDao();

    public abstract UserDao userDao();

    public abstract CommentDao commentDao();

}