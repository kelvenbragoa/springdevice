package com.cornelder.devicemanager.controllers;

import com.cornelder.devicemanager.dao.DeviceRepository;
import com.cornelder.devicemanager.models.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    DeviceRepository deviceRepository;

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> index(@RequestParam(required = false) String name){
        List<Device> devices = new ArrayList<Device>();
        try{
            if(name == null){
                devices.addAll(deviceRepository.findAll());
            }else{
                devices.addAll(deviceRepository.findByNameContaining(name));
            }
            if(devices.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(devices, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> show(@PathVariable("id") int id){
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){
            return new ResponseEntity<>(device.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> store(@RequestBody Device device){
        try{
            Device _device = deviceRepository
                    .save(new Device(device.getId(), device.getName(), device.getMake(), device.getModel()));
            return new ResponseEntity<>(_device, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
