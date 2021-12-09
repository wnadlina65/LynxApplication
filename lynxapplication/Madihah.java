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

public class Madihah extends AppCompatActivity
{
    DrawerLayout DL;
    public Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madihah);

        //Assign Variable
        DL = findViewById(R.id.drawer_layout);
    }

    public void readData() throws ParserConfigurationException, IOException, SAXException
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Madihah.xml"));

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

        EditText txtNameMadi = (EditText) findViewById(R.id.txtNameMadi);
        txtNameMadi.setText(resume.name);

        EditText txtPhoneMadi = (EditText) findViewById(R.id.txtPhoneMadi);
        txtPhoneMadi.setText(resume.phone);

        EditText txtEmailMadi = (EditText) findViewById(R.id.txtEmailMadi);
        txtEmailMadi.setText(resume.email);

        EditText txtAddressMadi = (EditText) findViewById(R.id.txtAddressMadi);
        txtAddressMadi.setText(resume.address);

        EditText txtPositionMadi = (EditText) findViewById(R.id.txtPositionMadi);
        txtPositionMadi.setText(resume.position);

        EditText txtSocialMadi = (EditText) findViewById(R.id.txtSocialMadi);
        txtSocialMadi.setText(resume.social[0] + "");

        EditText txtSummaryMadi = (EditText) findViewById(R.id.txtSummaryMadi);
        txtSummaryMadi.setText(resume.summary);

        EditText txtSkill1Madi = (EditText) findViewById(R.id.txtSkill1Madi);
        txtSkill1Madi.setText(resume.skill[0] + "");

        EditText txtSkill2Madi = (EditText) findViewById(R.id.txtSkill2Madi);
        txtSkill2Madi.setText(resume.skill[1] + "");

        EditText txtSkill3Madi = (EditText) findViewById(R.id.txtSkill3Madi);
        txtSkill3Madi.setText(resume.skill[2] + "");

        EditText txtExperience1Madi = (EditText) findViewById(R.id.txtExperience1Madi);
        txtExperience1Madi.setText(resume.experience[0] + "");

        EditText txtExperience2Madi = (EditText) findViewById(R.id.txtExperience2Madi);
        txtExperience2Madi.setText(resume.experience[1] + "");

        EditText txtExperience3Madi = (EditText) findViewById(R.id.txtExperience3Madi);
        txtExperience3Madi.setText(resume.experience[2] + "");

        EditText txtEdu1Madi = (EditText) findViewById(R.id.txtEdu1Madi);
        txtEdu1Madi.setText(resume.education[0] + "");

        EditText txtEdu2Madi = (EditText) findViewById(R.id.txtEdu2Madi);
        txtEdu2Madi.setText(resume.education[1] + "");

        EditText txtEdu3Madi = (EditText) findViewById(R.id.txtEdu3Madi);
        txtEdu3Madi.setText(resume.education[2] + "");

        EditText txtInterest1Madi = (EditText) findViewById(R.id.txtInterest1Madi);
        txtInterest1Madi.setText(resume.interest);
    }

    public void buttStoreMadi(View view) //store Madi data
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
                        Toast.makeText(Madihah.this, "Accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Madihah.this, "Error! Try again", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void buttUpdateMadi(View view) throws IOException, SAXException, ParserConfigurationException //update Madi data
    {
        Button buttonEditMadi = (Button) findViewById(R.id.buttonEditMadi);
        readData();
        buttonEditMadi.setText("Updated");

    }

    public void buttDeleteMadi(View view) throws ParserConfigurationException, IOException, SAXException //delete Madi data
    {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factor.newDocumentBuilder();
        Document doc = dBuilder.parse(getAssets().open("Madihah.xml"));

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

        EditText txtNameMadi = (EditText) findViewById(R.id.txtNameMadi);
        txtNameMadi.setText("");

        EditText txtPhoneMadi = (EditText) findViewById(R.id.txtPhoneMadi);
        txtPhoneMadi.setText("");

        EditText txtEmailMadi = (EditText) findViewById(R.id.txtEmailMadi);
        txtEmailMadi.setText("");

        EditText txtAddressMadi = (EditText) findViewById(R.id.txtAddressMadi);
        txtAddressMadi.setText("");

        EditText txtPositionMadi = (EditText) findViewById(R.id.txtPositionMadi);
        txtPositionMadi.setText("");

        EditText txtSocialMadi = (EditText) findViewById(R.id.txtSocialMadi);
        txtSocialMadi.setText("");

        EditText txtSummaryMadi = (EditText) findViewById(R.id.txtSummaryMadi);
        txtSummaryMadi.setText("");

        EditText txtSkill1Madi = (EditText) findViewById(R.id.txtSkill1Madi);
        txtSkill1Madi.setText("");

        EditText txtSkill2Madi = (EditText) findViewById(R.id.txtSkill2Madi);
        txtSkill2Madi.setText("");

        EditText txtSkill3Madi = (EditText) findViewById(R.id.txtSkill3Madi);
        txtSkill3Madi.setText("");

        EditText txtExperience1Madi = (EditText) findViewById(R.id.txtExperience1Madi);
        txtExperience1Madi.setText("");

        EditText txtExperience2Madi = (EditText) findViewById(R.id.txtExperience2Madi);
        txtExperience2Madi.setText("");

        EditText txtExperience3Madi = (EditText) findViewById(R.id.txtExperience3Madi);
        txtExperience3Madi.setText("");

        EditText txtEdu1Madi = (EditText) findViewById(R.id.txtEdu1Madi);
        txtEdu1Madi.setText("");

        EditText txtEdu2Madi = (EditText) findViewById(R.id.txtEdu2Madi);
        txtEdu2Madi.setText("");

        EditText txtEdu3Madi = (EditText) findViewById(R.id.txtEdu3Madi);
        txtEdu3Madi.setText("");

        EditText txtInterest1Madi = (EditText) findViewById(R.id.txtInterest1Madi);
        txtInterest1Madi.setText("");

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
        FancyToast.makeText(Madihah.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickMadihah(View view) //open Madihah Hannani Navigation
    {
        String msg="Madihah Hannani";
        recreate(); //recreate Madihah activity
        Intent intent = new Intent(this, Madihah.class);
        FancyToast.makeText(Madihah.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAin(View view) //open Ain Emylia Navigation
    {
        String msg="Ain Emylia";
        HomeActivity.redActivity(this,Ain.class); //redirect to Ain activity
        Intent intent = new Intent(this, Ain.class);
        FancyToast.makeText(Madihah.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickSyahmina(View view) //open Syahmina Navigation
    {
        String msg="Nurul Syahmina";
        HomeActivity.redActivity(this,Syahmina.class); //redirect to Syahmina activity
        Intent intent = new Intent(this, Syahmina.class);
        FancyToast.makeText(Madihah.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
    }

    public void ClickAdlina(View view) //open Wan Adlina Navigation
    {
        String msg="Wan Adlina";
        HomeActivity.redActivity(this,Adlina.class); //redirect to Adlina activity
        Intent intent = new Intent(this, Adlina.class);
        FancyToast.makeText(Madihah.this, msg, FancyToast.LENGTH_LONG, FancyToast.DEFAULT, true).show();
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