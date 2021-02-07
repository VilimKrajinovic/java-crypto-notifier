package com.crypto.org.cryptonotifier.api.responses;

import lombok.NonNull;
import lombok.Value;

@Value
public class ApiException {

    @NonNull
    ApiRequestError requestError;

    public static ApiException unauthorized(String exception) {
        return create(MessageId.UNAUTHORIZED, exception);
    }

    public static ApiException notFound(String exception) {
        return create(MessageId.NOT_FOUND, exception);
    }

    public static ApiException badRequest(String exception) {
        return create(MessageId.BAD_REQUEST, exception);
    }

    private static ApiException create(MessageId messageId, String exception) {
        return new ApiException(
                new ApiRequestError(
                        new ApiRequestError.ApiRequestErrorDetails(
                                messageId.name(),
                                messageId.message,
                                exception != null ? exception : ""
                        )
                )
        );
    }

    private enum MessageId {
        UNAUTHORIZED("Unauthorized access"),
        BAD_REQUEST("Bad request, please double check your request"),
        NOT_FOUND("Resource is not found"),
        ;

        private final String message;

        MessageId(String message) {
            this.message = message;
        }
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
            String message;
            @NonNull
            String exceptionMessage;
        }
    }
}
