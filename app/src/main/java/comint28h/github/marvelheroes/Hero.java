package comint28h.github.marvelheroes;

public class Hero {
    private static class Thumbnail {
        private String path;
        private String type = "portrait_xlarge";
        private String extension = "jpg";

        private Thumbnail(String path){
            this.path = path;
        }
    }

    private int id;
    private Thumbnail thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
