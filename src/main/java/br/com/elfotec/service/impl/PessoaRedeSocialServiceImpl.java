package br.com.elfotec.service.impl;

import br.com.elfotec.domain.PessoaRedeSocial;
import br.com.elfotec.repository.PessoaRedeSocialRepository;
import br.com.elfotec.service.PessoaRedeSocialService;
import br.com.elfotec.service.dto.PessoaRedeSocialDTO;
import br.com.elfotec.service.mapper.PessoaRedeSocialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PessoaRedeSocial}.
 */
@Service
@Transactional
public class PessoaRedeSocialServiceImpl implements PessoaRedeSocialService {

    private final Logger log = LoggerFactory.getLogger(PessoaRedeSocialServiceImpl.class);

    private final PessoaRedeSocialRepository pessoaRedeSocialRepository;

    private final PessoaRedeSocialMapper pessoaRedeSocialMapper;

    public PessoaRedeSocialServiceImpl(
        PessoaRedeSocialRepository pessoaRedeSocialRepository,
        PessoaRedeSocialMapper pessoaRedeSocialMapper
    ) {
        this.pessoaRedeSocialRepository = pessoaRedeSocialRepository;
        this.pessoaRedeSocialMapper = pessoaRedeSocialMapper;
    }

    @Override
    public PessoaRedeSocialDTO save(PessoaRedeSocialDTO pessoaRedeSocialDTO) {
        log.debug("Request to save PessoaRedeSocial : {}", pessoaRedeSocialDTO);
        PessoaRedeSocial pessoaRedeSocial = pessoaRedeSocialMapper.toEntity(pessoaRedeSocialDTO);
        pessoaRedeSocial = pessoaRedeSocialRepository.save(pessoaRedeSocial);
        return pessoaRedeSocialMapper.toDto(pessoaRedeSocial);
    }

    @Override
    public Optional<PessoaRedeSocialDTO> partialUpdate(PessoaRedeSocialDTO pessoaRedeSocialDTO) {
        log.debug("Request to partially update PessoaRedeSocial : {}", pessoaRedeSocialDTO);

        return pessoaRedeSocialRepository
            .findById(pessoaRedeSocialDTO.getId())
            .map(existingPessoaRedeSocial -> {
                pessoaRedeSocialMapper.partialUpdate(existingPessoaRedeSocial, pessoaRedeSocialDTO);

                return existingPessoaRedeSocial;
            })
            .map(pessoaRedeSocialRepository::save)
            .map(pessoaRedeSocialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PessoaRedeSocialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PessoaRedeSocials");
        return pessoaRedeSocialRepository.findAll(pageable).map(pessoaRedeSocialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PessoaRedeSocialDTO> findOne(Long id) {
        log.debug("Request to get PessoaRedeSocial : {}", id);
        return pessoaRedeSocialRepository.findById(id).map(pessoaRedeSocialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PessoaRedeSocial : {}", id);
        pessoaRedeSocialRepository.deleteById(id);
    }
}
