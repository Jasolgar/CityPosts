package es.jasolgar.cityposts.ui.details;

import androidx.databinding.ObservableField;

import es.jasolgar.cityposts.data.model.db.Comment;

public class CommentItemViewModel {


    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> author = new ObservableField<>();

    public final ObservableField<String> body = new ObservableField<>();


    public CommentItemViewModel(Comment comment){
        title.set(comment.getName());
        author.set(comment.getEmail());
        body.set(comment.getBody());
    }

}
