package controls;
import javafx.scene.control.TextField;

public class InformativeField extends TextField {
    private String informationRequested;
    private static final String DEFAULT = "";
    private CredentialValidator myField = this::getTextEntered;
    public InformativeField(String message){

        informationRequested = message;
        this.setText(informationRequested);
        this.setOnMouseClicked(mouseEvent -> handleDisplay());
    }
    /**
     * Provides access to values of InformativeField, which displays information concerning what the user should input
     * and displays whatever they have input so far, in order to validate their credentials
     * @author Anna Darwish
     */
    public CredentialValidator accessValue(){
        return myField;
    }
    private void handleDisplay(){
        if (this.getText().equals(informationRequested)){
            this.setText(DEFAULT);
        }
    }
    private String getTextEntered(){
        return this.getText();
    }


}
