package com.example.deneme

import User
import UserAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deneme.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import firebaes.MyFirebaseDataBase


class MainActivity : AppCompatActivity(),View.OnClickListener {
private lateinit var binding:ActivityMainBinding
private var list= mutableListOf<User>()
    private lateinit var backButton: Button
    private lateinit var loginButton: Button
    private lateinit var loginWithGoogleButton: Button
    private var selectId:String?=null
    private   var auth : FirebaseAuth = Firebase.auth
    private  lateinit var mail:EditText
    private  lateinit var password:EditText
    private  lateinit var saveButton:Button
    private  lateinit var uploadButton:Button
    private var adapter:UserAdapter?=null
    private   lateinit var  myfireBaseDb:MyFirebaseDataBase

    private lateinit var gso: GoogleSignInOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        myfireBaseDb.initDatabase()
        uploadButton=binding.Upload
        mail=binding.mail
        password=binding.password
        saveButton=binding.save



       myfireBaseDb.getData()
        uploadButton.setOnClickListener(this)
        saveButton.setOnClickListener(this)


    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account

private fun initRecycleView(){

    adapter=UserAdapter()
    myfireBaseDb=MyFirebaseDataBase("users",adapter!!)
    binding.apply {
        recycleView.layoutManager=LinearLayoutManager(this@MainActivity)
        recycleView.adapter=adapter

    }
    adapter?.setOnClickView {
        mail.setText(it.email)
        password.setText(it.username)
        selectId=it.userId
        println(it.userId)



    }
    adapter?.setOnClickDelete {

        selectId=it.userId
       myfireBaseDb.removeData(it)
    }
   

}




    override fun onClick(v: View?) {
        when(v!!.id){
            saveButton.id->{
                myfireBaseDb.saveData(User("",mail.text.toString(),password.text.toString()))
            }
            uploadButton.id->{
                println(myfireBaseDb.getLastId())
                myfireBaseDb.updateData(User(selectId,mail.text.toString(),password.text.toString()))

            }
        }


    }
}

