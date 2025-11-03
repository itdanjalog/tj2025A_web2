package example2.day02;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<GoodsEntity, Integer> {
    // JPA 기본 CRUD 메서드 제공
    // findAll(), findById(), save(), deleteById() 등
}