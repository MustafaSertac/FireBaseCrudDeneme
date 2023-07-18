package com.example.deneme
import User
import UserAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deneme.R
import com.example.deneme.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(),View.OnClickListener {
private lateinit var binding:ActivityMainBinding
private var list= mutableListOf<User>()
    private lateinit var backButton: Button
    private lateinit var loginButton: Button
    private lateinit var loginWithGoogleButton: Button
    private  var databaseReference: DatabaseReference?=null
    private  var fireDatabase:FirebaseDatabase?=null
    private   var auth : FirebaseAuth = Firebase.auth
    private  lateinit var mail:EditText
    private  lateinit var password:EditText
    private  lateinit var saveButton:Button
    private  lateinit var uploadButton:Button
    private var adapter:UserAdapter?=null
    private var selectId:String?=null

    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
        uploadButton=binding.Upload
        mail=binding.mail
        password=binding.password
        saveButton=binding.save

       fireDatabase=FirebaseDatabase.getInstance()
        databaseReference=fireDatabase?.getReference("users")
        getData()
        uploadButton.setOnClickListener(this)
        saveButton.setOnClickListener(this)


    }

    // onActivityResult() function : this is where
    // we provide the task and data for the Google Account

private fun initRecycleView(){
    adapter=UserAdapter()
    binding.apply {
        recycleView.layoutManager=LinearLayoutManager(this@MainActivity)
        recycleView.adapter=adapter

    }
    adapter?.setOnClickView {
        mail.setText(it.email)
        password.setText(it.username)
        selectId=it.userId



    }
    adapter?.setOnClickDelete {

        selectId=it.userId
        databaseReference?.child(selectId.toString().orEmpty())?.removeValue()
    }
   

}
    fun updateData() {

        val user = User(selectId,mail.text.toString(),password.text.toString())

        databaseReference?.child(selectId.toString())?.setValue(user)

    }
    fun saveData() {

        val user = User(getRandomString(3),mail.text.toString(),password.text.toString())
        databaseReference?.child(user.userId.toString())?.setValue(user)

    }
     private fun getData(){
         databaseReference?.addValueEventListener(object : ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {

                 list.clear()
           for (ds in snapshot.children){
               val user=User(ds.key,ds.child("email").value.toString(),ds.child("username").value.toString())
list.add(user)
           }
                adapter?.setItems(list)
             }

             override fun onCancelled(error: DatabaseError) {
              println(error.message.toString())
             }

         })
adapter?.setItems(list)

     }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            saveButton.id->{
                saveData()
            }
            uploadButton.id->{
                updateData()

            }
        }


    }
}

