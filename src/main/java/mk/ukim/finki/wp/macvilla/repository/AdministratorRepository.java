package mk.ukim.finki.wp.macvilla.repository;

import mk.ukim.finki.wp.macvilla.model.Administrator;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends UserRepository {

}
