package ui.Windows;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.EntityField;
import ui.LevelField;
import ui.Propertable;
import ui.manager.AssetManager;
import ui.panes.AssetImageSubPane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageManager extends AssetManager {
    private ImageView mySelectedImageView;
    private Propertable myProp;
    private static final String EXTENSION_KEY = "AcceptableImageExtensions";
    private static final String TITLE_KEY = "ImagesTitle";
    private static final String ASSET_IMAGE_FOLDER_PATH = GENERAL_RESOURCES.getString("images_filepath");

    public ImageManager(){
        super(ASSET_IMAGE_FOLDER_PATH, TITLE_KEY, EXTENSION_KEY);
        mySelectedImageView = null;
        myProp = null;
    }

    /**
     * This constructor is needed when a user has already created an object and the image
     * is tied to a properties object
     * @param prop
     */
    public ImageManager(Propertable prop){
        this();
        myProp = prop;
    }

    /**
     * Gets the ImageView associated with the selected image
     * @return ImageView of the selected image
     */
    public ImageView getImageView(){
        ImageView imageView = mySelectedImageView;
        return imageView;
    }

    @Override
    protected void handleClose(){
        mySelectedImageView = null;
        mySelectedAssetName = null;
        this.close();
    }

    @Override
    protected AssetImageSubPane createSubPane(File temp) {
        ImageView imageView = createImageView(temp);
        AssetImageSubPane subPane = new AssetImageSubPane(temp.getName().split("\\.")[0], imageView);
        subPane.setOnMouseClicked(mouseEvent -> {
            mySelectedAssetName = temp.getName();
            mySelectedImageView = imageView;
        });
        return subPane;
    }

    @Override
    protected void saveAsset(File selectedFile) {
        try {
            BufferedImage image = ImageIO.read(selectedFile);
            File saveToFile = new File(ASSET_IMAGE_FOLDER_PATH + File.separator + selectedFile.getName());
            File otherSaveToFile = new File("Images/" + selectedFile.getName());
            String[] split = selectedFile.getPath().split("\\.");
            String extension = split[split.length-1];
            ImageIO.write(image, extension, saveToFile);
            ImageIO.write(image, extension, otherSaveToFile);

        } catch (IOException e) {
            handleSavingException();
        }
    }

    @Override
    protected void handleApply() {
        if(!mySelectedAssetName.equals("")){
            if(myProp != null && myProp.getPropertyMap().containsKey(EntityField.IMAGE)){
                myProp.getPropertyMap().put(EntityField.IMAGE, mySelectedAssetName);
            }
            else if(myProp != null && myProp.getPropertyMap().containsKey(LevelField.BACKGROUND)){
                myProp.getPropertyMap().put(LevelField.BACKGROUND, mySelectedAssetName);
            }
            this.close();
        }
        else{
            handleNoAssetSelected();
        }
    }

    private ImageView createImageView(File temp) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image( new FileInputStream(temp.getPath()));
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            //The program is iterating through files found in the Assets folder
            //If a file is not found, it shouldn't be included so returning a blank ImageView is ok
        }
        return imageView;
    }

}
