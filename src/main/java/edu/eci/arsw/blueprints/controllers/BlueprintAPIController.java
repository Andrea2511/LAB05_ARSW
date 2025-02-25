/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    //Injection of the required component
    @Autowired
    private BlueprintsServices bpServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRecurso(){
        try {
            Set<Blueprint> data = bpServices.getAllBlueprints();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not Found BluePrint",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecursoByAuthor(@PathVariable("author") String author){
        try {
            Set<Blueprint> data = bpServices.getBlueprintsByAuthor(author);
            if(data.isEmpty()){
                return new ResponseEntity<>("There are no blueprint by" + author, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not Found BluePrint",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}/{bpname}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecursoByAuthor(@PathVariable("author") String author, @PathVariable("bpname") String bpname){
        try {
            Blueprint data = bpServices.getBlueprint(author, bpname);
            if(data == null){
                return new ResponseEntity<>("There are no blueprint by" + author + "Calling" + bpname, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Not Found BluePrint",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postBluePrint(@RequestBody Blueprint bp){
        try {
            bpServices.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Cannot create a new BluePrint",HttpStatus.FORBIDDEN);
        }

    }
    
    
}

