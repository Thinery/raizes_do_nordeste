package raizesnordeste.exception;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/** Formato padronizado de erro exigido pelo roteiro para todas as respostas de falha. */
public record ApiError(
        String error,
        String message,
        List<ErrorDetail> details,
        String timestamp,
        String path,
        String requestId
) {
    public record ErrorDetail(String field, String issue) {
    }

    public static ApiError of(String error, String message, List<ErrorDetail> details, String path) {
        return new ApiError(error, message, details, Instant.now().toString(), path, UUID.randomUUID().toString());
    }
}
