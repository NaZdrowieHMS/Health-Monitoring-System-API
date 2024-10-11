package agh.edu.pl.healthmonitoringsystemapplication.tools.ai_model;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.ModelLoadingException;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.PredictionException;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.types.TFloat32;

public class ModelPredictor {

    public float[] predict(Tensor inputTensor) {
        try (SavedModelBundle model = ModelLoader.loadModel()) {
            try (Session session = model.session()) {
                Tensor output = session.runner()
                        .feed("serve_input_layer", inputTensor)  // input tensor name
                        .fetch("StatefulPartitionedCall")       // output tensor name
                        .run()
                        .get(0);

                return handleOutputTensor(output);
            }
        } catch (ModelLoadingException ex) {
            throw new PredictionException("Failed to load the model: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new PredictionException("Error during prediction: " + ex.getMessage(), ex);
        }
    }

    private float[] handleOutputTensor(Tensor output) {
        try (TFloat32 result = (TFloat32) output) {
            if (result.shape().equals(Shape.of(1, 3))) { // Handle only Tensor with [1, 3] shape
                float[] predictions = new float[3];
                for (int i = 0; i < 3; i++) {
                    predictions[i] = result.getFloat(0, i);
                }
                return predictions;
            } else {
                throw new IllegalArgumentException("Unexpected tensor shape: " + result.shape());
            }
        }
    }
}
