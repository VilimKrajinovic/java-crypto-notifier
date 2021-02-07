package com.crypto.org.cryptonotifier.api.responses;

import lombok.NonNull;
import lombok.Value;

@Value
public class ApiException {

    @NonNull
    ApiRequestError requestError;

    public static ApiException unauthorized(String text) {
        return create(MessageId.UNAUTHORIZED, text);
    }

    public static ApiException notFound(String text) {
        return create(MessageId.NOT_FOUND, text);
    }

    public static ApiException badRequest(String text) {
        return create(MessageId.BAD_REQUEST, text);
    }

    private static ApiException create(MessageId messageId, String text) {
        return new ApiException(
                new ApiRequestError(
                        new ApiRequestError.ApiRequestErrorDetails(
                                messageId.name(),
                                (text != null) ? text : ""
                        )
                )
        );
    }

    private enum MessageId {
        UNAUTHORIZED,
        BAD_REQUEST,
        NOT_FOUND,
    }

    @Value
    public static class ApiRequestError {
        @NonNull
        ApiRequestErrorDetails serviceException;

        @Value
        public static class ApiRequestErrorDetails {
            @NonNull
            String messageId;
            @NonNull
            String text;
        }
    }
}
