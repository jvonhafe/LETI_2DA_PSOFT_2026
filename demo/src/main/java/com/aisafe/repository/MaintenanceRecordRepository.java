package com.aisafe.repository;

import com.aisafe.model.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, String> {
    List<MaintenanceRecord> findByAircraftRegistration(String aircraftRegistration);

    @Query("SELECT COALESCE(SUM(m.expectedDurationHours), 0) FROM MaintenanceRecord m WHERE m.aircraftRegistration = :registration")
    Double totalMaintenanceHoursByAircraft(@Param("registration") String registration);

    @Query("SELECT COALESCE(SUM(m.expectedDurationHours), 0) FROM MaintenanceRecord m")
    Double totalMaintenanceHoursForFleet();
}
