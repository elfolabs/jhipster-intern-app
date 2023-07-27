package br.com.elfotec.service.mapper;

import br.com.elfotec.domain.PessoaContato;
import br.com.elfotec.service.dto.PessoaContatoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PessoaContato} and its DTO {@link PessoaContatoDTO}.
 */
@Mapper(componentModel = "spring", uses = { PessoaMapper.class })
public interface PessoaContatoMapper extends EntityMapper<PessoaContatoDTO, PessoaContato> {
    @Mapping(target = "contato", source = "contato", qualifiedByName = "id")
    PessoaContatoDTO toDto(PessoaContato s);
}
