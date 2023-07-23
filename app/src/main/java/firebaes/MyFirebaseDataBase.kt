package firebaes

import User
import UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyFirebaseDataBase constructor(

    private val databaseRef:String,
    private var adapter:UserAdapter

    ){

private var lastId:String?=""
private  var databaseReference: DatabaseReference?=null
    private var selectId:String?=null
private  var fireDatabase: FirebaseDatabase?=null
    private var list= mutableListOf<User>()


val Req_Code: Int = 123
private lateinit var firebaseAuth: FirebaseAuth



  fun initDatabase(){
    fireDatabase=FirebaseDatabase.getInstance()
    databaseReference=fireDatabase?.getReference(databaseRef)
    getId()


}

    fun updateData(user:User) {

        val user = User(selectId,user.email,user.password)

        databaseReference?.child(selectId.toString())?.setValue(user)

    }
    fun saveData(user:User) {



        val user = User(lastId,user.email,user.password)
        databaseReference?.child(lastId.toString())?.setValue(user)





    }
    fun  getId(){
        databaseReference?.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            lastId=((snapshot.childrenCount)+1).toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getLastId():String{

        return lastId!!

    }
      fun getData(){
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lastId=snapshot.childrenCount.toString()
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


     fun removeData(user:User){
         databaseReference?.child(user.userId.toString().orEmpty())?.removeValue()
     }


}