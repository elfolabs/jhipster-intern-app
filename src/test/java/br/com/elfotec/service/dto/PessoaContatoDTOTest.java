package br.com.elfotec.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaContatoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaContatoDTO.class);
        PessoaContatoDTO pessoaContatoDTO1 = new PessoaContatoDTO();
        pessoaContatoDTO1.setId(1L);
        PessoaContatoDTO pessoaContatoDTO2 = new PessoaContatoDTO();
        assertThat(pessoaContatoDTO1).isNotEqualTo(pessoaContatoDTO2);
        pessoaContatoDTO2.setId(pessoaContatoDTO1.getId());
        assertThat(pessoaContatoDTO1).isEqualTo(pessoaContatoDTO2);
        pessoaContatoDTO2.setId(2L);
        assertThat(pessoaContatoDTO1).isNotEqualTo(pessoaContatoDTO2);
        pessoaContatoDTO1.setId(null);
        assertThat(pessoaContatoDTO1).isNotEqualTo(pessoaContatoDTO2);
    }
}
