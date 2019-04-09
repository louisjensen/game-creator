package ui.panes;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.AuthoringEntity;
import ui.ErrorBox;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

public class ImageWithEntity extends ImageView {
    private AuthoringEntity myAuthoringEntity;
    private static final ResourceBundle myResources = ResourceBundle.getBundle("image_with_entity");

    public ImageWithEntity(FileInputStream s, AuthoringEntity authoringEntity, double width, double height) {
        super(new Image(s, width, height, false, false));
        myAuthoringEntity = authoringEntity;
        myAuthoringEntity.getPropertyMap().addListener((MapChangeListener<Enum, String>) change -> {handleChange(change);
            System.out.println("Change observed");});
    }

    private void handleChange(MapChangeListener.Change<? extends Enum,? extends String> change) {
        if(change.wasAdded() && myResources.containsKey(change.getKey().toString())){
            System.out.println("Change was added");
            String methodName = myResources.getString(change.getKey().toString());
            try {
                Method method = this.getClass().getDeclaredMethod(methodName, String.class);
                method.invoke(this, change.getValueAdded());
            } catch (Exception e) {
                //TODO: get rid of the stack trace once confirmed working
                e.printStackTrace();
                String header = myResources.getString("ErrorHeader");
                String content = myResources.getString("ErrorContent");
                ErrorBox errorBox = new ErrorBox(header, content);
                errorBox.display();
            }
        }
    }

    private void updateX(String x){
        this.setTranslateX(Double.parseDouble(x));
    }

    private void updateY(String y){
        this.setTranslateY(Double.parseDouble(y));
    }

    private void updateWidth(String width){
        System.out.println("updateWidth called");
    }

    private void updateHeight(String height){
        System.out.println("updateHeight called");
    }

    private void updateImage(String image){
        System.out.println("updateImage called");
    }
    /**
     * Returns AuthoringEntity associated with this object
     * @return AuthoringEntity
     */
    public AuthoringEntity getAuthoringEntity(){
        return myAuthoringEntity;
    }

}
