package br.com.elfotec.service.mapper;

import br.com.elfotec.domain.MediaFile;
import br.com.elfotec.service.dto.MediaFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MediaFile} and its DTO {@link MediaFileDTO}.
 */
@Mapper(componentModel = "spring", uses = { PessoaMapper.class })
public interface MediaFileMapper extends EntityMapper<MediaFileDTO, MediaFile> {
    @Mapping(target = "fotoRosto", source = "fotoRosto", qualifiedByName = "id")
    MediaFileDTO toDto(MediaFile s);
}
