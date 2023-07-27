package br.com.elfotec.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaRedeSocialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaRedeSocial.class);
        PessoaRedeSocial pessoaRedeSocial1 = new PessoaRedeSocial();
        pessoaRedeSocial1.setId(1L);
        PessoaRedeSocial pessoaRedeSocial2 = new PessoaRedeSocial();
        pessoaRedeSocial2.setId(pessoaRedeSocial1.getId());
        assertThat(pessoaRedeSocial1).isEqualTo(pessoaRedeSocial2);
        pessoaRedeSocial2.setId(2L);
        assertThat(pessoaRedeSocial1).isNotEqualTo(pessoaRedeSocial2);
        pessoaRedeSocial1.setId(null);
        assertThat(pessoaRedeSocial1).isNotEqualTo(pessoaRedeSocial2);
    }
}
