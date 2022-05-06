package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Transactional
    @Query("SELECT o FROM Offer o" +
            " JOIN o.agent ag" +
            " JOIN o.apartment ap" +
            " WHERE ap.apartmentType = :apartmentType" +
            " ORDER BY ap.area DESC, o.price ASC")
    List<Offer> findAllCustomQuery(ApartmentType apartmentType);
}
