package agh.edu.pl.healthmonitoringsystem;

import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FunctionalTestExecutor {

    private final RestTemplate restTemplate;
    private final String BASE_TEST_URL;

    public FunctionalTestExecutor() {
        restTemplate = new RestTemplate();
        BASE_TEST_URL = TestEnv.getBaseUrl();
    }

    public ResponseEntity<Comment> addCommentApiCall(CommentRequest requestBody) {
        String url = BASE_TEST_URL + "api/health";
        return restTemplate.postForEntity(url, requestBody, Comment.class);
    }
}
