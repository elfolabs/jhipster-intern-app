package br.com.elfotec.service.impl;

import br.com.elfotec.domain.MediaFile;
import br.com.elfotec.repository.MediaFileRepository;
import br.com.elfotec.service.MediaFileService;
import br.com.elfotec.service.dto.MediaFileDTO;
import br.com.elfotec.service.mapper.MediaFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MediaFile}.
 */
@Service
@Transactional
public class MediaFileServiceImpl implements MediaFileService {

    private final Logger log = LoggerFactory.getLogger(MediaFileServiceImpl.class);

    private final MediaFileRepository mediaFileRepository;

    private final MediaFileMapper mediaFileMapper;

    public MediaFileServiceImpl(MediaFileRepository mediaFileRepository, MediaFileMapper mediaFileMapper) {
        this.mediaFileRepository = mediaFileRepository;
        this.mediaFileMapper = mediaFileMapper;
    }

    @Override
    public MediaFileDTO save(MediaFileDTO mediaFileDTO) {
        log.debug("Request to save MediaFile : {}", mediaFileDTO);
        MediaFile mediaFile = mediaFileMapper.toEntity(mediaFileDTO);
        mediaFile = mediaFileRepository.save(mediaFile);
        return mediaFileMapper.toDto(mediaFile);
    }

    @Override
    public Optional<MediaFileDTO> partialUpdate(MediaFileDTO mediaFileDTO) {
        log.debug("Request to partially update MediaFile : {}", mediaFileDTO);

        return mediaFileRepository
            .findById(mediaFileDTO.getId())
            .map(existingMediaFile -> {
                mediaFileMapper.partialUpdate(existingMediaFile, mediaFileDTO);

                return existingMediaFile;
            })
            .map(mediaFileRepository::save)
            .map(mediaFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MediaFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MediaFiles");
        return mediaFileRepository.findAll(pageable).map(mediaFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MediaFileDTO> findOne(Long id) {
        log.debug("Request to get MediaFile : {}", id);
        return mediaFileRepository.findById(id).map(mediaFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MediaFile : {}", id);
        mediaFileRepository.deleteById(id);
    }
}
