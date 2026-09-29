package raizesnordeste.exception;

import org.springframework.http.HttpStatus;

/** Erro de regra de negocio (ex.: estoque insuficiente) - por padrao vira HTTP 409. */
public class NegocioException extends RuntimeException {

    private final String codigoErro;
    private final HttpStatus status;

    public NegocioException(String codigoErro, String mensagem) {
        this(codigoErro, mensagem, HttpStatus.CONFLICT);
    }

    public NegocioException(String codigoErro, String mensagem, HttpStatus status) {
        super(mensagem);
        this.codigoErro = codigoErro;
        this.status = status;
    }

    public String getCodigoErro() {
        return codigoErro;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
