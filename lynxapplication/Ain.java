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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Ain extends AppCompatActivity
{
    public Resume resume;
    DrawerLayout DL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ain);

        //Assign Variable
        DL = findViewById(R.id.drawer_layout);


    }

    public void readData() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Ain.xml"));

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

        EditText txtNameAin = (EditText) findViewById(R.id.txtNameMadi);
        txtNameAin.setText(resume.name);

        EditText txtPhoneAin = (EditText) findViewById(R.id.txtPhoneMadi);
        txtPhoneAin.setText(resume.phone);

        EditText txtEmailAin = (EditText) findViewById(R.id.txtEmailMadi);
        txtEmailAin.setText(resume.email);

        EditText txtAddressAin = (EditText) findViewById(R.id.txtAddressMadi);
        txtAddressAin.setText(resume.address);

        EditText txtPositionAin = (EditText) findViewById(R.id.txtPositionMadi);
        txtPositionAin.setText(resume.position);

        EditText txtSocialAin = (EditText) findViewById(R.id.txtSocialAin);
        txtSocialAin.setText(resume.social[0] + "");

        EditText txtSummaryAin = (EditText) findViewById(R.id.txtSummaryMadi);
        txtSummaryAin.setText(resume.summary);

        Spinner spinnerSkillsAin = (Spinner) findViewById(R.id.spinnerSkillsAin);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSkillsAin.setAdapter(skillAdapter);

        EditText txtExperienceAin = (EditText) findViewById(R.id.txtExperienceAin);
        txtExperienceAin.setText(resume.experience[0] + "");

        Spinner spinnerEduAin = (Spinner) findViewById(R.id.spinnerEduAin);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEduAin.setAdapter(educationAdapter);

        EditText txtInterestAin = (EditText) findViewById(R.id.txtInterestAin);
        txtInterestAin.setText(resume.interest);
    }

    public void buttStoreAin(View view) //store Ain data
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
                        Toast.makeText(Ain.this, "Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Ain.this, "Error! Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void buttUpdateAin(View view) throws IOException, SAXException, ParserConfigurationException //update Ain data
    {
        Button buttonUpdateAin = (Button) findViewById(R.id.buttonUpdateMina);
        readData();
        buttonUpdateAin.setText("Updated");

    }

    public void buttDeleteAin(View view) throws ParserConfigurationException, IOException, SAXException //delete Ain data
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Ain.xml"));

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

        EditText txtNameAin = (EditText) findViewById(R.id.txtNameMadi);
        txtNameAin.setText("");

        EditText txtPhoneAin = (EditText) findViewById(R.id.txtPhoneMadi);
        txtPhoneAin.setText("");

        EditText txtEmailAin = (EditText) findViewById(R.id.txtEmailMadi);
        txtEmailAin.setText("");

        EditText txtAddressAin = (EditText) findViewById(R.id.txtAddressMadi);
        txtAddressAin.setText("");

        EditText txtPositionAin = (EditText) findViewById(R.id.txtPositionMadi);
        txtPositionAin.setText("");

        Spinner spinnerSocialAin = (Spinner) findViewById(R.id.spinnerSocialAin);
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.social);
        socialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSocialAin.setAdapter(null);

        EditText txtSummaryAin = (EditText) findViewById(R.id.txtSummaryMadi);
        txtSummaryAin.setText("");

        Spinner spinnerSkillsAin = (Spinner) findViewById(R.id.spinnerSkillsAin);
        ArrayAdapter<String> skillAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.skill);
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSkillsAin.setAdapter(null);

        EditText txtExperienceAin = (EditText) findViewById(R.id.txtExperienceAin);
        txtExperienceAin.setText("");

        Spinner spinnerEduAin = (Spinner) findViewById(R.id.spinnerEduAin);
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, resume.education);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEduAin.setAdapter(null);

        EditText txtInterestAin = (EditText) findViewById(R.id.txtInterestAin);
        txtInterestAin.setText("");

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
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickMadihah(View view) //open Madihah Hannani Navigation
    {
        String msg="Madihah Hannani";
        HomeActivity.redActivity(this,Madihah.class); //redirect to madihah activity
        Intent intent = new Intent(this, Madihah.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAin(View view) //open Ain Emylia Navigation
    {
        String msg="Ain Emylia";
        recreate(); //recreate Ain activity
        Intent intent = new Intent(this, Ain.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickSyahmina(View view) //open Syahmina Navigation
    {
        String msg="Nurul Syahmina";
        HomeActivity.redActivity(this,Syahmina.class); //redirect to syahmina activity
        Intent intent = new Intent(this, Syahmina.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAdlina(View view) //open Wan Adlina Navigation
    {
        String msg="Wan Adlina";
        HomeActivity.redActivity(this,Adlina.class); //redirect to adlina activity
        Intent intent = new Intent(this, Adlina.class);
        FancyToast.makeText(Ain.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
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