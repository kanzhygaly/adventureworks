/*
 * Common enity class which enables JPA Auditing in child entities
 */
package kz.ya.adventureworks.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 *
 * @author yerlana
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class AuditEntity implements Serializable {
}
