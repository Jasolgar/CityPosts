package es.jasolgar.posts.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "comments",
        primaryKeys = {"id"},
        foreignKeys = @ForeignKey(
                entity = Post.class,
                parentColumns = "id",
                childColumns = "post_id",
                onDelete = CASCADE,
                onUpdate = CASCADE ))
public class Comment {

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private long id;

    @SerializedName("postId")
    @ColumnInfo(name = "post_id")
    @Expose
    private long postId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    @Expose
    private String email;

    @SerializedName("body")
    @ColumnInfo(name = "body")
    @Expose
    private String body;

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("postId", postId).append("id", id).append("name", name).append("email", email).append("body", body).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Comment)) {
            return false;
        }
        Comment rhs = ((Comment) other);
        return new EqualsBuilder().append(name, rhs.name).append(postId, rhs.postId).append(id, rhs.id).append(body, rhs.body).append(email, rhs.email).isEquals();
    }

}
