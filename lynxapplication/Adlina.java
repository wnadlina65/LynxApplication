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

public class Adlina extends AppCompatActivity
{
    DrawerLayout DL;
    public Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adlina);

        //Assign Variable
        DL = findViewById(R.id.drawer_layout);

    }

    public void readData() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Adlina.xml"));

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

        EditText textNameAdlina = (EditText) findViewById(R.id.textNameAdlina);
        textNameAdlina.setText(resume.name);

        EditText textPhoneAdlina = (EditText) findViewById(R.id.textPhoneAdlina);
        textPhoneAdlina.setText(resume.phone);

        EditText textEmailAdlina = (EditText) findViewById(R.id.textEmailAdlina);
        textEmailAdlina.setText(resume.email);

        EditText textAddressAdlina = (EditText) findViewById(R.id.textAddressAdlina);
        textAddressAdlina.setText(resume.address);

        EditText textPositionAdlina = (EditText) findViewById(R.id.textPositionAdlina);
        textPositionAdlina.setText(resume.position);

        Spinner spinnerSocialAdlina = (Spinner) findViewById(R.id.spinnerSocialAdlina);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSocialAdlina.setAdapter(socialAdapter);

        EditText textSummaryAdlina = (EditText) findViewById(R.id.textSummaryAdlina);
        textSummaryAdlina.setText(resume.summary);

        Spinner spinnerSkillsAdlina = (Spinner) findViewById(R.id.spinnerSkillsAin);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSkillsAdlina.setAdapter(skillAdapter);

        Spinner spinnerExperienceAdlina = (Spinner) findViewById(R.id.spinnerExperienceAdlina);
        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.experience);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerExperienceAdlina.setAdapter(experienceAdapter);

        Spinner spinnerEducationAdlina = (Spinner) findViewById(R.id.spinnerEducationAdlina);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEducationAdlina.setAdapter(experienceAdapter);

        EditText textInterestAdlina = (EditText) findViewById(R.id.textInterestAdlina);
        textInterestAdlina.setText(resume.interest);
    }

    public void buttStoreLina(View view) //store Adlina data
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
                        Toast.makeText(Adlina.this, "Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Adlina.this, "Error! Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void buttUpdateLina(View view) throws IOException, SAXException, ParserConfigurationException //update Adlina data
    {
        Button buttonUpdateAdlina = (Button) findViewById(R.id.buttonUpdateMina);
        readData();
        buttonUpdateAdlina.setText("Updated");

    }

    public void buttDeleteLina(View view) //delete Adlina data
    {
        EditText textNameAdlina = (EditText) findViewById(R.id.textNameAdlina);
        textNameAdlina.setText("");

        EditText textPhoneAdlina = (EditText) findViewById(R.id.textPhoneAdlina);
        textPhoneAdlina.setText("");

        EditText textEmailAdlina = (EditText) findViewById(R.id.textEmailAdlina);
        textEmailAdlina.setText("");

        EditText textAddressAdlina = (EditText) findViewById(R.id.textAddressAdlina);
        textAddressAdlina.setText("");

        EditText textPositionAdlina = (EditText) findViewById(R.id.textPositionAdlina);
        textPositionAdlina.setText("");

        Spinner spinnerSocialAdlina = (Spinner) findViewById(R.id.spinnerSocialAdlina);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSocialAdlina.setAdapter(null);

        EditText textSummaryAdlina = (EditText) findViewById(R.id.textSummaryAdlina);
        textSummaryAdlina.setText("");

        Spinner spinnerSkillsAdlina = (Spinner) findViewById(R.id.spinnerSkillsAin);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSkillsAdlina.setAdapter(null);

        Spinner spinnerExperienceAdlina = (Spinner) findViewById(R.id.spinnerExperienceAdlina);
        ArrayAdapter<String> experienceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.experience);
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerExperienceAdlina.setAdapter(null);

        Spinner spinnerEducationAdlina = (Spinner) findViewById(R.id.spinnerEducationAdlina);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEducationAdlina.setAdapter(null);

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
        HomeActivity.redActivity(this, Alya.class); //redirect to Alya activity
        Intent intent = new Intent(this, Alya.class);
        FancyToast.makeText(Adlina.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickMadihah(View view) //open Madihah Hannani Navigation
    {
        String msg="Madihah Hannani";
        HomeActivity.redActivity(this,Madihah.class); //redirect to madihah activity
        Intent intent = new Intent(this, Madihah.class);
        FancyToast.makeText(Adlina.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAin(View view) //open Ain Emylia Navigation
    {
        String msg="Ain Emylia";
        HomeActivity.redActivity(this,Ain.class); //redirect to Ain activity
        Intent intent = new Intent(this, Ain.class);
        FancyToast.makeText(Adlina.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickSyahmina(View view) //open Syahmina Navigation
    {
        String msg="Nurul Syahmina";
        HomeActivity.redActivity(this,Syahmina.class); //redirect to syahmina activity
        Intent intent = new Intent(this, Syahmina.class);
        FancyToast.makeText(Adlina.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAdlina(View view) //open Wan Adlina Navigation
    {
        String msg="Wan Adlina";
        recreate(); //recreate adlina activity
        Intent intent = new Intent(this, Adlina.class);
        FancyToast.makeText(Adlina.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
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