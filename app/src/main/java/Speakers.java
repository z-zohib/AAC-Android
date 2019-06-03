import org.json.JSONException;
import org.json.JSONObject;

public class Speakers {
    private int id;
    private String imageURL;
    private String imageName;
    private String name;
    private String description;
    private String sessionIDs;
    private String speakerTitle;

    public Speakers(JSONObject event) throws JSONException {
        this.id = event.getInt("id");
        this.imageURL = event.getString("imageURL");
        this.imageName = event.getString("imageName");
        this.name = event.getString("name");
        this.description = event.getString("personDescription");
        this.sessionIDs = event.getString("sessionIDs");
        this.speakerTitle = event.getString("peopleTitle");
    }

    public int getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSessionIDs() {
        return sessionIDs;
    }

    public String getSpeakerTitle() {
        return speakerTitle;
    }

}
