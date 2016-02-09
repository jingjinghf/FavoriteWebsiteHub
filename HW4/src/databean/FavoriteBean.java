package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("favorite_id")
public class FavoriteBean {
    private int favorite_id;
    private String url;
    private String comment;
    private int user_id;
    private int click;
    public int getFavorite_id() {
        return favorite_id;
    }
    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getClick() {
        return click;
    }
    public void setClick(int click) {
        this.click = click;
    }   
}

