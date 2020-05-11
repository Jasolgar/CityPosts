package es.jasolgar.posts.data.model.others;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MailEmoji {

    @SerializedName("ends_with")
    @Expose
    private String endsWith;

    @SerializedName("emoji")
    @Expose
    private String emoji;

    public MailEmoji(){

    }

    public String getEmojiByUnicode(){
        return new String(Character.toChars(Integer.parseInt(emoji.substring(2), 16)));
    }

    public String getEndsWith() {
        return endsWith;
    }

}
