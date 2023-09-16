package br.com.elfotec.repository;

import br.com.elfotec.domain.MediaFile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    @Query("SELECT mf FROM MediaFile mf WHERE mf.dataExclusao IS NULL")
    List<MediaFile> findAllWithNullDataExclusao();
}
