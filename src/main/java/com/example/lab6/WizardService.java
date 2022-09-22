package com.example.lab6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;
    public WizardService(WizardRepository repository) {
        this.repository = repository;
    }
    public List<Wizard> retrieveWizard() {
        return repository.findAll();
    }
    public Optional<Wizard> retrieveWizardById(String _id) {
        return repository.findById(_id);
    }
    public Wizard createWizard(Wizard wizard) {
        return repository.save(wizard);
    }
    public void deleteWizard(String _id) {
//        System.out.println("kuy");
        repository.deleteById(_id);
    }
    public Wizard updateWizard(Wizard wizard) {
        return repository.save(wizard);
    } }
