package br.com.elfotec.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaRedeSocialMapperTest {

    private PessoaRedeSocialMapper pessoaRedeSocialMapper;

    @BeforeEach
    public void setUp() {
        pessoaRedeSocialMapper = new PessoaRedeSocialMapperImpl();
    }
}
