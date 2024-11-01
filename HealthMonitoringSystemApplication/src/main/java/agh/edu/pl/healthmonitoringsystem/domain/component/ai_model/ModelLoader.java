package agh.edu.pl.healthmonitoringsystem.domain.component.ai_model;

import agh.edu.pl.healthmonitoringsystem.domain.exception.ModelLoadingException;
import org.tensorflow.SavedModelBundle;

import java.io.File;
import java.net.URL;

public class ModelLoader {

    private static final String MODEL_PATH = "models/ai_model"; //Locally
    private static final String DOCKER_MODEL_PATH = "/app/models/ai_model"; //In docker/deployment


    public static SavedModelBundle loadModel() {
        try {
//            File modelDir = getModelFile();                                    //locally uncomment this
//            return SavedModelBundle.load(modelDir.getAbsolutePath(), "serve"); //locally uncomment this
            return SavedModelBundle.load(DOCKER_MODEL_PATH, "serve");      //locally comment this
        } catch (Exception ex) {
            throw new ModelLoadingException(String.format("Error loading model from path: %s", DOCKER_MODEL_PATH), ex);
        }
    }

    private static File getModelFile() throws Exception {
        URL modelUrl = ModelLoader.class.getClassLoader().getResource(MODEL_PATH);
        if (modelUrl == null) {
            throw new ModelLoadingException("Model not found at path: " + MODEL_PATH);
        }
        return new File(modelUrl.toURI());
    }
}
