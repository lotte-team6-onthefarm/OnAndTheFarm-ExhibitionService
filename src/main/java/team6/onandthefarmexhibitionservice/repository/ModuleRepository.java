package team6.onandthefarmexhibitionservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team6.onandthefarmexhibitionservice.entity.Module;

@Repository
public interface ModuleRepository extends CrudRepository<Module, Long> {
}
