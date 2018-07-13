package comint28h.github.marvelheroes.hero;

public class Hero {
    String name;
    String description;
    Thumbnail thumbnail;
    Comics comics;
    Series series;

    public Hero(String name, String description, Thumbnail thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Comics getComics() {
        return comics;
    }

    public Series getSeries() {
        return series;
    }
}
