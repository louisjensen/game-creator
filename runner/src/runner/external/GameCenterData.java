package runner.external;

public class GameCenterData {
    private String myFolderName;
    private String myTitle;
    private String myImageLocation;
    private String myDescription;

    public String getFolderName() {
        return myFolderName;
    }

    public void setFolderName(String folderName) {
        myFolderName = folderName;
    }

    public String getTitle() {
        return myTitle;
    }

    public void setTitle(String title) {
        myTitle = title;
    }

    public String getImageLocation() {
        return myImageLocation;
    }

    public void setImageLocation(String imageLocation) {
        myImageLocation = imageLocation;
    }

    public String getDescription() {
        return myDescription;
    }

    public void setDescription(String description) {
        myDescription = description;
    }
}
