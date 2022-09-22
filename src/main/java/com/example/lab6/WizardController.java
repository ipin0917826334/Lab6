package com.example.lab6;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class WizardController {
    @Autowired
    private WizardService WizardService ;
    private Wizards wiz = new Wizards();
    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizard() {
        wiz.wizards = WizardService.retrieveWizard();
//        System.out.println(wiz.wizards);
        return ResponseEntity.ok(wiz.wizards);
// การสร้าง ResponseEntity (คือ Object ข้อมลู ที)จะคืนค่าออกไป) และกาํ หนด status เป็น 200OK
    }
    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> createWizard(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        Wizard w = WizardService.createWizard(new Wizard(null,d.get("sex"),d.get("name"),d.get("school"),d.get("house"),Integer.parseInt(d.get("money")),d.get("position")));
        wiz.wizards = WizardService.retrieveWizard();
        return ResponseEntity.ok(wiz.wizards);
    }

    @RequestMapping(value = "/updateWizard",
            method = RequestMethod.POST)
    public ResponseEntity<?> updateWizard(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        Optional<Wizard> wizard = WizardService.retrieveWizardById(d.get("_id"));
        System.out.println(wizard);
        if(wizard != null) {
            WizardService.updateWizard(new Wizard(d.get("_id"), d.get("sexNew"), d.get("nameNew"), d.get("schoolNew"), d.get("houseNew"), Integer.parseInt(d.get("moneyNew")), d.get("positionNew")));
            wiz.wizards = WizardService.retrieveWizard();
            return ResponseEntity.ok(wiz.wizards);
        }else {
            return ResponseEntity.ok("");
        }}
    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public ResponseEntity<?> deleteWizard(@RequestBody MultiValueMap<String, String> n) {
        Map<String, String> d = n.toSingleValueMap();
        Optional<Wizard> wizard = WizardService.retrieveWizardById(d.get("_id"));
        System.out.println(wizard.get().get_id());
        WizardService.deleteWizard(wizard.get().get_id());
        wiz.wizards = WizardService.retrieveWizard();
        System.out.println(wiz.wizards);
        return ResponseEntity.ok(wiz.wizards);
    }
}

