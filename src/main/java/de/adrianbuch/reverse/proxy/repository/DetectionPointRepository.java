package de.adrianbuch.reverse.proxy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.adrianbuch.reverse.proxy.domain.detection.DetectionPoint;

@Repository
public interface DetectionPointRepository extends CrudRepository<DetectionPoint, String> {
}
