package com.example.lynxapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Alya extends AppCompatActivity
{
    DrawerLayout DL;
    public Resume resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alya);

        //Assign Variable
        DL = findViewById(R.id.drawer_layout);
    }

    public void readData() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Alya.xml"));

        doc.getDocumentElement().normalize();

        String myName = doc.getElementsByTagName("name").item(0).getTextContent();
        String myPhone = doc.getElementsByTagName("phone").item(0).getTextContent();
        String myEmail = doc.getElementsByTagName("email").item(0).getTextContent();
        String myAddress = doc.getElementsByTagName("address").item(0).getTextContent();
        String myPosition = doc.getElementsByTagName("position").item(0).getTextContent();
        String mySummary = doc.getElementsByTagName("summary").item(0).getTextContent();
        String myInterest = doc.getElementsByTagName("interest").item(0).getTextContent();

        //social
        NodeList mySocial = doc.getElementsByTagName("social");
        String[] listSocial = new String[mySocial.getLength()];
        for (int  i = 0; i < mySocial.getLength(); i++)
        {
            listSocial[i] = mySocial.item(i).getTextContent();
        }
        //Skills
        NodeList mySkills = doc.getElementsByTagName("skill");
        String[] listSkills = new String[mySkills.getLength()];
        for (int  i = 0; i < mySkills.getLength(); i++)
        {
            listSkills[i] = mySkills.item(i).getTextContent();
        }
        //Experience
        NodeList myExperience = doc.getElementsByTagName("experience");
        String[] listExperience = new String[myExperience.getLength()];
        for (int  i = 0; i < myExperience.getLength(); i++)
        {
            listExperience [i] = myExperience.item(i).getTextContent();
        }
        //Education
        NodeList myEducation = doc.getElementsByTagName("education");
        String[] listEducation = new String[myEducation.getLength()];
        for (int  i = 0; i < myEducation.getLength(); i++)
        {
            listEducation[i] = myEducation.item(i).getTextContent();
        }

        resume = new Resume(myName, myPhone, myEmail, myAddress, myPosition,listSocial, mySummary,
                listSkills, listExperience, listEducation, myInterest);

        EditText nameAlya = (EditText) findViewById(R.id.nameAlya);
        nameAlya.setText(resume.name);

        EditText PhoneAlya = (EditText) findViewById(R.id.PhoneAlya);
        PhoneAlya.setText(resume.phone);

        EditText EmailAlya = (EditText) findViewById(R.id.EmailAlya);
        EmailAlya.setText(resume.email);

        EditText AddressAlya = (EditText) findViewById(R.id.AddressAlya);
        AddressAlya.setText(resume.address);

        EditText textPositionAlya = (EditText) findViewById(R.id.textPositionAlya);
        textPositionAlya.setText(resume.position);

        Spinner spinnerSocialAlya = (Spinner) findViewById(R.id.spinnerSocialAlya);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSocialAlya.setAdapter(socialAdapter);

        EditText SummaryAlya = (EditText) findViewById(R.id.SummaryAlya);
        SummaryAlya.setText(resume.summary);

        Spinner spinSkillAlya = (Spinner) findViewById(R.id.spinSkillAlya);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinSkillAlya.setAdapter(skillAdapter);

        EditText Experience1Alya = (EditText) findViewById(R.id.Experience1Alya);
        Experience1Alya.setText(resume.experience[0] + "");

        EditText Experience2Alya = (EditText) findViewById(R.id.Experience2Alya);
        Experience2Alya.setText(resume.experience[1] + "");

        EditText Experience3Alya = (EditText) findViewById(R.id.Experience3Alya);
        Experience3Alya.setText(resume.experience[2] + "");

        Spinner spinEducationAlya = (Spinner) findViewById(R.id.spinEducationAlya);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinEducationAlya.setAdapter(educationAdapter);

        EditText textInterestAdlina = (EditText) findViewById(R.id.textInterestAdlina);
        textInterestAdlina.setText(resume.interest);
    }

    public void buttStoreAlya(View view) //store Alya data
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> resumefb = new HashMap<>();
        resumefb.put("Name", resume.name);
        resumefb.put("Phone", resume.phone);
        resumefb.put("Email", resume.email);
        resumefb.put("Address", resume.address);
        resumefb.put("Position", resume.position);
        resumefb.put("Social", Arrays.toString(resume.social));
        resumefb.put("Summary", resume.summary);
        resumefb.put("Skill", Arrays.toString(resume.skill));
        resumefb.put("Experience", Arrays.toString(resume.experience));
        resumefb.put("Education", Arrays.toString(resume.education));
        resumefb.put("Interest", resume.interest);

        db.collection("Resume")
                .add(resumefb)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Alya.this, "Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Alya.this, "Error! Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void buttUpdateAlya(View view) throws IOException, SAXException, ParserConfigurationException //update Alya data
    {
        Button buttonEditAlya = (Button) findViewById(R.id.buttonEditMadi);
        readData();
        buttonEditAlya.setText("Updated");

    }

    public void buttDeleteAlya(View view) throws ParserConfigurationException, IOException, SAXException //delete Alya data
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Alya.xml"));

        doc.getDocumentElement().normalize();

        String myName = doc.getElementsByTagName("name").item(0).getTextContent();
        String myPhone = doc.getElementsByTagName("phone").item(0).getTextContent();
        String myEmail = doc.getElementsByTagName("email").item(0).getTextContent();
        String myAddress = doc.getElementsByTagName("address").item(0).getTextContent();
        String myPosition = doc.getElementsByTagName("position").item(0).getTextContent();
        String mySummary = doc.getElementsByTagName("summary").item(0).getTextContent();
        String myInterest = doc.getElementsByTagName("interest").item(0).getTextContent();

        //social
        NodeList mySocial = doc.getElementsByTagName("social");
        String[] listSocial = new String[mySocial.getLength()];
        for (int  i = 0; i < mySocial.getLength(); i++)
        {
            listSocial[i] = mySocial.item(i).getTextContent();
        }
        //Skills
        NodeList mySkills = doc.getElementsByTagName("skill");
        String[] listSkills = new String[mySkills.getLength()];
        for (int  i = 0; i < mySkills.getLength(); i++)
        {
            listSkills[i] = mySkills.item(i).getTextContent();
        }
        //Experience
        NodeList myExperience = doc.getElementsByTagName("experience");
        String[] listExperience = new String[myExperience.getLength()];
        for (int  i = 0; i < myExperience.getLength(); i++)
        {
            listExperience [i] = myExperience.item(i).getTextContent();
        }
        //Education
        NodeList myEducation = doc.getElementsByTagName("education");
        String[] listEducation = new String[myEducation.getLength()];
        for (int  i = 0; i < myEducation.getLength(); i++)
        {
            listEducation[i] = myEducation.item(i).getTextContent();
        }

        resume = new Resume(myName, myPhone, myEmail, myAddress, myPosition,listSocial, mySummary,
                listSkills, listExperience, listEducation, myInterest);

        EditText nameAlya = (EditText) findViewById(R.id.nameAlya);
        nameAlya.setText("");

        EditText PhoneAlya = (EditText) findViewById(R.id.PhoneAlya);
        PhoneAlya.setText("");

        EditText EmailAlya = (EditText) findViewById(R.id.EmailAlya);
        EmailAlya.setText("");

        EditText AddressAlya = (EditText) findViewById(R.id.AddressAlya);
        AddressAlya.setText("");

        EditText textPositionAlya = (EditText) findViewById(R.id.textPositionAlya);
        textPositionAlya.setText("");

        Spinner spinnerSocialAlya = (Spinner) findViewById(R.id.spinnerSocialAlya);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSocialAlya.setAdapter(null);

        EditText SummaryAlya = (EditText) findViewById(R.id.SummaryAlya);
        SummaryAlya.setText("");

        Spinner spinSkillAlya = (Spinner) findViewById(R.id.spinSkillAlya);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinSkillAlya.setAdapter(null);

        EditText Experience1Alya = (EditText) findViewById(R.id.Experience1Alya);
        Experience1Alya.setText("");

        EditText Experience2Alya = (EditText) findViewById(R.id.Experience2Alya);
        Experience2Alya.setText("");

        EditText Experience3Alya = (EditText) findViewById(R.id.Experience3Alya);
        Experience3Alya.setText("");

        Spinner spinEducationAlya = (Spinner) findViewById(R.id.spinEducationAlya);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinEducationAlya.setAdapter(null);

        EditText textInterestAdlina = (EditText) findViewById(R.id.textInterestAdlina);
        textInterestAdlina.setText("");

    }

    public static void openDrawer(DrawerLayout DL)
    {
        //Open Navigation Drawer Layout
        DL.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout DL)
    {
        //Close Navigation Drawer Layout
        if(DL.isDrawerOpen(GravityCompat.START))
        {
            DL.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMenu(View view)
    {
        openDrawer(DL); //Open Navigation Drawer
    }

    public void ClickOut()
    {
        closeDrawer(DL); //Close Drawer
    }

    public void ClickHome(View view) throws IOException, SAXException, ParserConfigurationException
    {
        HomeActivity.redActivity(this, HomeActivity.class); //redirect to Main activity
    }

    public void ClickAlya(View view) throws IOException, SAXException, ParserConfigurationException //open Shafiqah Alya Navigation
    {
        String msg="Shafiqah Alya";
        recreate(); //recreate Alya activity
        Intent intent = new Intent(this, Alya.class);
        FancyToast.makeText(Alya.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickMadihah(View view) //open Madihah Hannani Navigation
    {
        String msg="Madihah Hannani";
        HomeActivity.redActivity(this,Madihah.class); //redirect to madihah activity
        Intent intent = new Intent(this, Madihah.class);
        FancyToast.makeText(Alya.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAin(View view) //open Ain Emylia Navigation
    {
        String msg="Ain Emylia";
        HomeActivity.redActivity(this,Ain.class); //redirect to Ain activity
        Intent intent = new Intent(this, Ain.class);
        FancyToast.makeText(Alya.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickSyahmina(View view) //open Syahmina Navigation
    {
        String msg="Nurul Syahmina";
        HomeActivity.redActivity(this,Syahmina.class); //redirect to syahmina activity
        Intent intent = new Intent(this, Syahmina.class);
        FancyToast.makeText(Alya.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAdlina(View view) //open Wan Adlina Navigation
    {
        String msg="Wan Adlina";
        HomeActivity.redActivity(this,Adlina.class); //redirect to Adlina activity
        Intent intent = new Intent(this, Adlina.class);
        FancyToast.makeText(Alya.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickLogout(View view)
    {
        //close app
        logout(this);
    }

    public static void logout(Activity activity)
    {
        //logout confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        //Show Dialog
        builder.show();
    }

    public static void redActivity(Activity activity, Class obj) //redirect activity
    {
        //Initialize intent
        Intent intent = new Intent(activity, obj);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(DL);
    }
}