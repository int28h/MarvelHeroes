package comint28h.github.marvelheroes.hero;

public class APIResponse {
    String code;
    String status;
    Data data;

    public APIResponse(String code, String status, Data data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
