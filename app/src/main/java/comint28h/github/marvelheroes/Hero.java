package comint28h.github.marvelheroes;

public class Hero {
    private class Thumbnail {
        private String path;
        private String extension;

        private Thumbnail(String path, String extension){
            this.extension = extension;
            this.path = path;
        }
    }

    private final String TYPE = "portrait_xlarge";

    private int id;
    private Thumbnail thumbnail;

    public int getId() {
        return id;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getImageURL(){
        Thumbnail current = getThumbnail();
        return current.path + TYPE + current.extension;
    }
}
