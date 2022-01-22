package com.example.workoutapp

data class exercise (
    private var id:Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
    ){
    fun getid(): Int{
        return id;
    }
   fun setid(id:Int){
     this.id=id
   }
    fun getname(): String{
        return name
    }
    fun setname(name: String){
        this.name=name
    }
    fun getImage(): Int{
        return image
    }
    fun setImage(image: Int)
    {
        this.image=image
    }
    fun getisCompleted(): Boolean{
        return isCompleted
    }
    fun setisCompleted(completed:Boolean){
        isCompleted=completed
    }
    fun getisselected(): Boolean{
        return isSelected
    }
    fun setisselected(selected:Boolean){
        this.isSelected=selected
    }
}
