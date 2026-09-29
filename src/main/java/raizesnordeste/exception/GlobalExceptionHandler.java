package raizesnordeste.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidacao(MethodArgumentNotValidException ex, WebRequest request) {
        List<ApiError.ErrorDetail> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ApiError.ErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .toList();
        ApiError body = ApiError.of("DADOS_INVALIDOS", "Um ou mais campos estao invalidos.", detalhes, path(request));
        return ResponseEntity.unprocessableEntity().body(body);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleNaoEncontrado(RecursoNaoEncontradoException ex, WebRequest request) {
        ApiError body = ApiError.of(ex.getCodigoErro(), ex.getMessage(), List.of(), path(request));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiError> handleNegocio(NegocioException ex, WebRequest request) {
        ApiError body = ApiError.of(ex.getCodigoErro(), ex.getMessage(), List.of(), path(request));
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleCredenciais(BadCredentialsException ex, WebRequest request) {
        ApiError body = ApiError.of("CREDENCIAIS_INVALIDAS", "E-mail ou senha invalidos.", List.of(), path(request));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAcessoNegado(AccessDeniedException ex, WebRequest request) {
        ApiError body = ApiError.of("SEM_PERMISSAO", "Voce nao tem permissao para executar esta acao.", List.of(), path(request));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenerico(Exception ex, WebRequest request) {
        ApiError body = ApiError.of("ERRO_INTERNO", "Ocorreu um erro inesperado.", List.of(), path(request));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String path(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
