package br.com.elfotec.service.impl;

import br.com.elfotec.domain.PessoaContato;
import br.com.elfotec.repository.PessoaContatoRepository;
import br.com.elfotec.service.PessoaContatoService;
import br.com.elfotec.service.dto.PessoaContatoDTO;
import br.com.elfotec.service.mapper.PessoaContatoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PessoaContato}.
 */
@Service
@Transactional
public class PessoaContatoServiceImpl implements PessoaContatoService {

    private final Logger log = LoggerFactory.getLogger(PessoaContatoServiceImpl.class);

    private final PessoaContatoRepository pessoaContatoRepository;

    private final PessoaContatoMapper pessoaContatoMapper;

    public PessoaContatoServiceImpl(PessoaContatoRepository pessoaContatoRepository, PessoaContatoMapper pessoaContatoMapper) {
        this.pessoaContatoRepository = pessoaContatoRepository;
        this.pessoaContatoMapper = pessoaContatoMapper;
    }

    @Override
    public PessoaContatoDTO save(PessoaContatoDTO pessoaContatoDTO) {
        log.debug("Request to save PessoaContato : {}", pessoaContatoDTO);
        PessoaContato pessoaContato = pessoaContatoMapper.toEntity(pessoaContatoDTO);
        pessoaContato = pessoaContatoRepository.save(pessoaContato);
        return pessoaContatoMapper.toDto(pessoaContato);
    }

    @Override
    public Optional<PessoaContatoDTO> partialUpdate(PessoaContatoDTO pessoaContatoDTO) {
        log.debug("Request to partially update PessoaContato : {}", pessoaContatoDTO);

        return pessoaContatoRepository
            .findById(pessoaContatoDTO.getId())
            .map(existingPessoaContato -> {
                pessoaContatoMapper.partialUpdate(existingPessoaContato, pessoaContatoDTO);

                return existingPessoaContato;
            })
            .map(pessoaContatoRepository::save)
            .map(pessoaContatoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PessoaContatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PessoaContatoes");
        return pessoaContatoRepository.findAll(pageable).map(pessoaContatoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PessoaContatoDTO> findOne(Long id) {
        log.debug("Request to get PessoaContato : {}", id);
        return pessoaContatoRepository.findById(id).map(pessoaContatoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PessoaContato : {}", id);
        pessoaContatoRepository.deleteById(id);
    }
}
