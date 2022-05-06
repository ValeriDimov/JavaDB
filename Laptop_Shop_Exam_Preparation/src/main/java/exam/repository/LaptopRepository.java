package exam.repository;

import exam.model.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
    Optional<Laptop> findByMacAddress(String macAddress);


    @Transactional
    @Query("SELECT l FROM Laptop l" +
            " JOIN l.shop s" +
            " JOIN s.town t" +
            " ORDER BY l.cpuSpeed DESC, l.ram DESC, l.storage DESC, l.macAddress ASC")
    List<Laptop> findAllCustomRequest();
}