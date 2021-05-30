package de.adrianbuch.reverse.proxy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.adrianbuch.reverse.proxy.domain.decoy.Decoy;

@Repository
public interface DecoyRepository extends CrudRepository<Decoy, String> {
}
