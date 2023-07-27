package br.com.elfotec.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaContatoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaContato.class);
        PessoaContato pessoaContato1 = new PessoaContato();
        pessoaContato1.setId(1L);
        PessoaContato pessoaContato2 = new PessoaContato();
        pessoaContato2.setId(pessoaContato1.getId());
        assertThat(pessoaContato1).isEqualTo(pessoaContato2);
        pessoaContato2.setId(2L);
        assertThat(pessoaContato1).isNotEqualTo(pessoaContato2);
        pessoaContato1.setId(null);
        assertThat(pessoaContato1).isNotEqualTo(pessoaContato2);
    }
}
