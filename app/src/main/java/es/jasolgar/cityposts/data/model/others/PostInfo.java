package es.jasolgar.cityposts.data.model.others;

import es.jasolgar.cityposts.data.model.db.Post;

public class PostInfo {

    private final long id;
    private final long userId;


    private final String title;
    private final String imageUrl;

    private String body = "";
    private String authorName = "";

    private String avatar = "";
    private String postId;

    public PostInfo(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.imageUrl = post.getImageUrl();
        this.title = post.getTitle();
        this.body = post.getBody();
    }

    public String getPostId() { return String.valueOf(id);  }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


}
