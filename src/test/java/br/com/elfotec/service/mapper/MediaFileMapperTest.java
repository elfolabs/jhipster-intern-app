package br.com.elfotec.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MediaFileMapperTest {

    private MediaFileMapper mediaFileMapper;

    @BeforeEach
    public void setUp() {
        mediaFileMapper = new MediaFileMapperImpl();
    }
}
