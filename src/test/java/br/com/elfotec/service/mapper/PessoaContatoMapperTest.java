package br.com.elfotec.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaContatoMapperTest {

    private PessoaContatoMapper pessoaContatoMapper;

    @BeforeEach
    public void setUp() {
        pessoaContatoMapper = new PessoaContatoMapperImpl();
    }
}
