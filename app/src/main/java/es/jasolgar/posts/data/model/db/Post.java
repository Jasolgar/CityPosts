package es.jasolgar.posts.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "posts",
        primaryKeys = {"id"},
        indices = {@Index(value = {"id", "user_id"}, unique = true)},
        foreignKeys ={
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = CASCADE,
                        onUpdate = CASCADE )
})
public class Post {

    @Expose
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private long id;

    @Expose
    @SerializedName("userId")
    @ColumnInfo(name = "user_id")
    private long userId;

    @Expose
    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title;

    @Expose
    @SerializedName("body")
    @ColumnInfo(name = "body")
    private String body;

    @Expose
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    public Post(){}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("id", id).append("title", title).append("body", body).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Post)) {
            return false;
        }
        Post rhs = ((Post) other);
        return new EqualsBuilder().append(id, rhs.id).append(title, rhs.title).append(body, rhs.body).append(userId, rhs.userId).isEquals();
    }
}
