package fathi.reza.been_tech.LoginAndRegister

object TokenHolder {
     var token:String?=null
    private set
     var refresh_token:String?=null
    private set

    fun updateToken(token:String?,refresh_token:String?){
        this.token=token
        this.refresh_token=refresh_token

    }
}