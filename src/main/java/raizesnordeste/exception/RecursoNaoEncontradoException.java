package raizesnordeste.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    private final String codigoErro;

    public RecursoNaoEncontradoException(String codigoErro, String mensagem) {
        super(mensagem);
        this.codigoErro = codigoErro;
    }

    public String getCodigoErro() {
        return codigoErro;
    }
}
