package comint28h.github.marvelheroes.model;

import java.util.List;

public class Series {
    List<Items> items;

    public Series(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }
}
