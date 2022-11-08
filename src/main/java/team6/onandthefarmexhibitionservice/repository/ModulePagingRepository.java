package team6.onandthefarmexhibitionservice.repository;

import team6.onandthefarmexhibitionservice.entity.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulePagingRepository extends PagingAndSortingRepository<Module, Long> {

    @Query(value = "select m from Module m",
        countQuery = "select count(m) from Module m")
    Page<Module> findModuleOrderBy(PageRequest pageRequest);

}
