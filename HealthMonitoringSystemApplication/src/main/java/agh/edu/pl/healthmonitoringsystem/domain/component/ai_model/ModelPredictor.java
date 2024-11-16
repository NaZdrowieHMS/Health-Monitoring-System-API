//package agh.edu.pl.healthmonitoringsystem.domain.component.ai_model;
//import agh.edu.pl.healthmonitoringsystem.domain.exception.InvalidTensorShapeException;
//import agh.edu.pl.healthmonitoringsystem.domain.exception.ModelLoadingException;
//import agh.edu.pl.healthmonitoringsystem.domain.exception.PredictionException;
//import org.springframework.stereotype.Component;
//import org.tensorflow.SavedModelBundle;
//import org.tensorflow.Session;
//import org.tensorflow.Tensor;
//import org.tensorflow.ndarray.Shape;
//import org.tensorflow.types.TFloat32;
//
//@Component
//public class ModelPredictor {
//
//    private static final String INPUT_TENSOR_NAME = "serve_input_layer";
//    private static final String OUTPUT_TENSOR_NAME = "StatefulPartitionedCall";
//
//    public float[] predict(Tensor inputTensor) {
//        try (SavedModelBundle model = ModelLoader.loadModel()) {
//            try (Session session = model.session()) {
//                Tensor output = session.runner()
//                        .feed(INPUT_TENSOR_NAME, inputTensor)
//                        .fetch(OUTPUT_TENSOR_NAME)
//                        .run()
//                        .get(0);
//
//                return handleOutputTensor(output);
//            }
//        } catch (ModelLoadingException ex) {
//            throw new PredictionException("Failed to load the model: " + ex.getMessage(), ex);
//        } catch (Exception ex) {
//            throw new PredictionException("Error during prediction: " + ex.getMessage(), ex);
//        }
//    }
//
//    private float[] handleOutputTensor(Tensor output) {
//        try (TFloat32 result = (TFloat32) output) {
//            if (result.shape().equals(Shape.of(1, 3))) { // Handle only Tensor with [1, 3] shape
//                float[] predictions = new float[3];
//                for (int i = 0; i < 3; i++) {
//                    predictions[i] = result.getFloat(0, i);
//                }
//                return predictions;
//            } else {
//                throw new InvalidTensorShapeException("Unexpected tensor shape: " + result.shape());
//            }
//        }
//    }
//}
