package pl.finances.finances_app.services;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
public class ReceiptOcrService {
    public String extractTextFromImage(MultipartFile file) throws Exception {
        ByteString byteString = ByteString.copyFrom(file.getBytes());

        Image image = Image.newBuilder().setContent(byteString).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                .addFeatures(feature)
                .setImage(image)
                .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            List<AnnotateImageResponse> responses = client.batchAnnotateImages(List.of(request)).getResponsesList();
            if (responses.isEmpty() || responses.get(0).hasError()) {
                throw new RuntimeException("Error from Vision API: " + responses.get(0).getError().getMessage());
            }
            return responses.get(0).getFullTextAnnotation().getText();
        }
    }
}
