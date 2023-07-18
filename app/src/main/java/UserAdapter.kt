import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.R
import com.example.deneme.databinding.ItemrowBinding

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var userList=mutableListOf<User>()
    private  var onClickView:((User)->Unit)?=null
    private  var onClickDelete:((User)->Unit)?=null
class UserViewHolder( val binding: ItemrowBinding):RecyclerView.ViewHolder(binding.root){
    private var tvName:TextView?=null
    private var tvMail:TextView?=null
    private var actionView:ImageView?=null
    private var deleteView:ImageView?=null
    private  var onClickView:((User)->Unit)?=null
    private  var onClickDelete:((User)->Unit)?=null



    fun setItem(data:User){
        tvName=binding.name
        tvMail=binding.mail
        tvName?.text=data.username
        tvMail?.text=data.email
        actionView=binding.dZenle
        deleteView=binding.remove
        deleteView?.setOnClickListener{
            onClickDelete?.invoke(data)
        }
        actionView?.setOnClickListener{
            onClickView?.invoke(data)
        }


    }
    fun setOnClickDelete(callback:(User)->Unit){
        this.onClickDelete=callback
    }
    fun setOnClickView(callback:(User)->Unit){
        this.onClickView=callback
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemrowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return userList.size
    }
    fun setItems(list:MutableList<User>){
        this.userList=list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user=userList[position]
        holder.setItem(user)
        holder.setOnClickView {
           onClickView?.invoke(it)

        }
        holder.setOnClickDelete {
            onClickDelete?.invoke(it)
        }
    }

    fun setOnClickView(callback:(User)->Unit){
        this.onClickView=callback
    }
    fun setOnClickDelete(callback:(User)->Unit){
        this.onClickDelete=callback
    }

}