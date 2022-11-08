package team6.onandthefarmexhibitionservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import team6.onandthefarmexhibitionservice.entity.ModuleImg;

@Repository
public interface ModuleImgRepository extends CrudRepository<ModuleImg, Long> {

    List<ModuleImg> findByModule(Module module);
}
