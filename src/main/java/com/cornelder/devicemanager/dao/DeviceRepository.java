package com.cornelder.devicemanager.dao;

import com.cornelder.devicemanager.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device,Integer> {
    List<Device> findByNameContaining(String name);
}
