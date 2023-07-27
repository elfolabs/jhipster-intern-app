package br.com.elfotec.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaRedeSocialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaRedeSocialDTO.class);
        PessoaRedeSocialDTO pessoaRedeSocialDTO1 = new PessoaRedeSocialDTO();
        pessoaRedeSocialDTO1.setId(1L);
        PessoaRedeSocialDTO pessoaRedeSocialDTO2 = new PessoaRedeSocialDTO();
        assertThat(pessoaRedeSocialDTO1).isNotEqualTo(pessoaRedeSocialDTO2);
        pessoaRedeSocialDTO2.setId(pessoaRedeSocialDTO1.getId());
        assertThat(pessoaRedeSocialDTO1).isEqualTo(pessoaRedeSocialDTO2);
        pessoaRedeSocialDTO2.setId(2L);
        assertThat(pessoaRedeSocialDTO1).isNotEqualTo(pessoaRedeSocialDTO2);
        pessoaRedeSocialDTO1.setId(null);
        assertThat(pessoaRedeSocialDTO1).isNotEqualTo(pessoaRedeSocialDTO2);
    }
}
