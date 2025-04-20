package org.msa.one.notification.repository;

import org.msa.one.notification.entity.SentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentNotificationRepository extends JpaRepository<SentNotification, Long> {
}