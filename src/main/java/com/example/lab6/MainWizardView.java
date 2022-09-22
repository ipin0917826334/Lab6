package com.example.lab6;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("MyView2")
@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout{
    private TextField nameTf, moneyTf;
    private RadioButtonGroup checkbox;
    private Button prevBtn, createBtn, updateBtn, delBtn, nextBtn;
    private HorizontalLayout layout;
    public int index;

    private ComboBox<String> positionBox, schoolBox, houseBox;
    public MainWizardView() {
        nameTf = new TextField();
        moneyTf = new TextField("Dollars");
        checkbox = new RadioButtonGroup<>("Gender :");
        prevBtn = new Button("<<");
        nextBtn = new Button(">>");
        createBtn = new Button("Create");
        updateBtn = new Button("Update");
        delBtn = new Button("Delete");
        positionBox = new ComboBox<>();
        schoolBox = new ComboBox<>();
        houseBox = new ComboBox<>();
        layout = new HorizontalLayout();
        nameTf.setPlaceholder("Fullname");
        moneyTf.setPlaceholder("$");
        checkbox.setItems("Male", "Female");
        positionBox.setItems("Student", "Teacher");
        positionBox.setPlaceholder("Position");
        schoolBox.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        schoolBox.setPlaceholder("School");
        houseBox.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        houseBox.setPlaceholder("House");
        layout.setPadding(true);
        layout.add(prevBtn, createBtn, updateBtn, delBtn, nextBtn);
        add(nameTf, checkbox, positionBox, moneyTf, schoolBox, houseBox, layout);
        index = 0;
        ArrayList[] out = {WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(ArrayList.class)
                .block()};
//        Wizards wizards = new Wizards();
//        wizards.wizards = new ArrayList<String>(out[0]);
////        wizards.wizards = String.valueOf(out[0]);
////        System.out.println(wizards.wizards);
//        System.out.println(out[0]);
//        ArrayList<String> arr = new ArrayList<>();
//        System.out.println(wizards.wizards.get(index));
        JSONObject jsonObject = new JSONObject((Map<String, ?>) out[0].get(index));
        System.out.println(out[0]);
        nameTf.setValue((String) jsonObject.get("name"));
        String sex = (String) jsonObject.get("sex");
        if(sex.equals("m")){
            checkbox.setValue("Male");
        }
        else{
            checkbox.setValue("Female");
        }
        positionBox.setValue((String) jsonObject.get("position"));
        schoolBox.setValue((String) jsonObject.get("school"));
        houseBox.setValue((String) jsonObject.get("house"));
        moneyTf.setValue(String.valueOf((Integer) jsonObject.get("money")));
//        System.out.println(wizards.wizards);
        prevBtn.addClickListener(event -> {
            if(index == 0){
                index = index;
            }
            else{
                index -= 1;
                JSONObject jsonObject1 = new JSONObject((Map<String, ?>) out[0].get(index));
                System.out.println(jsonObject1.get("sex"));
                nameTf.setValue((String) jsonObject1.get("name"));
                String sex1 = (String) jsonObject1.get("sex");
                if(sex1.equals("m")){
                    checkbox.setValue("Male");
                }
                else{
                    checkbox.setValue("Female");
                }
                positionBox.setValue((String) jsonObject1.get("position"));
                schoolBox.setValue((String) jsonObject1.get("school"));
                houseBox.setValue((String) jsonObject1.get("house"));
                moneyTf.setValue(String.valueOf((Integer) jsonObject1.get("money")));
            }
        });
        nextBtn.addClickListener(event -> {
//            out[0] = new ArrayList<String>(out[0]);
            if(index == out[0].size()-1){
                index = index;
            }
            else{
                index += 1;
                JSONObject jsonObject1 = new JSONObject((Map<String, ?>) out[0].get(index));
                System.out.println(jsonObject1.get("sex"));
                nameTf.setValue((String) jsonObject1.get("name"));
                String sex1 = (String) jsonObject1.get("sex");
                if(sex1.equals("m")){
                    checkbox.setValue("Male");
                }
                else{
                    checkbox.setValue("Female");
                }
                positionBox.setValue((String) jsonObject1.get("position"));
                schoolBox.setValue((String) jsonObject1.get("school"));
                houseBox.setValue((String) jsonObject1.get("house"));
                moneyTf.setValue(String.valueOf((Integer) jsonObject1.get("money")));
            }
            System.out.println(index);
        });
        createBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            if(checkbox.getValue().equals("Male")){
                checkbox.setValue("m");
            }
            else{
                checkbox.setValue("f");
            }
            formData.add("sex", (String) checkbox.getValue());
            formData.add("name", nameTf.getValue());
            formData.add("school", schoolBox.getValue());
            formData.add("house", houseBox.getValue());
            formData.add("money", moneyTf.getValue());
            formData.add("position", positionBox.getValue());
//                System.out.println(formData);
            ArrayList[] out1 = {WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block()};
            out[0] = new ArrayList<String>(out1[0]);
            System.out.println(out[0]);
        });
        updateBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            if(checkbox.getValue().equals("Male")){
                checkbox.setValue("m");
            }
            else{
                checkbox.setValue("f");
            }
            JSONObject jsonObject2 = new JSONObject((Map<String, ?>) out[0].get(index));
            formData.add("_id", (String) jsonObject2.get("_id"));
            formData.add("sexNew", (String) checkbox.getValue());
            formData.add("nameNew", nameTf.getValue());
            formData.add("schoolNew", schoolBox.getValue());
            formData.add("houseNew", houseBox.getValue());
            formData.add("moneyNew", moneyTf.getValue());
            formData.add("positionNew", positionBox.getValue());
//                System.out.println(formData);
            ArrayList[] out1 = {WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block()};
            out[0] = new ArrayList<String>(out1[0]);
            System.out.println(out[0]);
        });
        delBtn.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            JSONObject jsonObject2 = new JSONObject((Map<String, ?>) out[0].get(index));
            formData.add("_id", (String) jsonObject2.get("_id"));
            System.out.println((String) jsonObject.get("_id"));
            formData.add("_id", (String) jsonObject.get("_id"));
//                System.out.println(formData);
            ArrayList[] out1 = {WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block()};
            out[0] = new ArrayList<String>(out1[0]);
            System.out.println(out1[0]);
        });
    }

}
