package kz.ya.adventureworks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author yerlana
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductReviewNotFoundException extends RuntimeException {

    public ProductReviewNotFoundException(String message) {
        super(message);
    }

    public ProductReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
