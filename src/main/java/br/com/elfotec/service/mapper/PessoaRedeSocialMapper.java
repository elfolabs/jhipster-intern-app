package br.com.elfotec.service.mapper;

import br.com.elfotec.domain.PessoaRedeSocial;
import br.com.elfotec.service.dto.PessoaRedeSocialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PessoaRedeSocial} and its DTO {@link PessoaRedeSocialDTO}.
 */
@Mapper(componentModel = "spring", uses = { PessoaMapper.class })
public interface PessoaRedeSocialMapper extends EntityMapper<PessoaRedeSocialDTO, PessoaRedeSocial> {
    @Mapping(target = "redeSocial", source = "redeSocial", qualifiedByName = "id")
    PessoaRedeSocialDTO toDto(PessoaRedeSocial s);
}
