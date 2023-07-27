package br.com.elfotec.service.mapper;

import br.com.elfotec.domain.Pessoa;
import br.com.elfotec.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PessoaDTO toDtoId(Pessoa pessoa);
}
