data class User(val userId:String?,val username: String? = null, val email: String? = null,val password: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}