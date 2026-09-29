package raizesnordeste.audit;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "logs_auditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** E-mail do usuario ou "anonimo" quando a acao nao exige login. */
    private String usuario;

    /** Ex.: LOGIN, MOVIMENTACAO_ESTOQUE, CADASTRO_CLIENTE */
    private String acao;

    @Column(length = 500)
    private String detalhes;

    private LocalDateTime dataHora = LocalDateTime.now();

    public LogAuditoria() {
    }

    public LogAuditoria(String usuario, String acao, String detalhes) {
        this.usuario = usuario;
        this.acao = acao;
        this.detalhes = detalhes;
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAcao() {
        return acao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
