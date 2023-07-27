package br.com.elfotec.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.elfotec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MediaFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaFile.class);
        MediaFile mediaFile1 = new MediaFile();
        mediaFile1.setId(1L);
        MediaFile mediaFile2 = new MediaFile();
        mediaFile2.setId(mediaFile1.getId());
        assertThat(mediaFile1).isEqualTo(mediaFile2);
        mediaFile2.setId(2L);
        assertThat(mediaFile1).isNotEqualTo(mediaFile2);
        mediaFile1.setId(null);
        assertThat(mediaFile1).isNotEqualTo(mediaFile2);
    }
}
