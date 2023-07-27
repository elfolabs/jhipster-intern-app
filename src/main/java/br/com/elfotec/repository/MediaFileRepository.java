package br.com.elfotec.repository;

import br.com.elfotec.domain.MediaFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MediaFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {}
