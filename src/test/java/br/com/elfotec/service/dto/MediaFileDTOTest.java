package br.com.elfotec.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaFileDTO.class);
        MediaFileDTO mediaFileDTO1 = new MediaFileDTO();
        mediaFileDTO1.setId(1L);
        MediaFileDTO mediaFileDTO2 = new MediaFileDTO();
        assertThat(mediaFileDTO1).isNotEqualTo(mediaFileDTO2);
        mediaFileDTO2.setId(mediaFileDTO1.getId());
        assertThat(mediaFileDTO1).isEqualTo(mediaFileDTO2);
        mediaFileDTO2.setId(2L);
        assertThat(mediaFileDTO1).isNotEqualTo(mediaFileDTO2);
        mediaFileDTO1.setId(null);
        assertThat(mediaFileDTO1).isNotEqualTo(mediaFileDTO2);
    }
}
