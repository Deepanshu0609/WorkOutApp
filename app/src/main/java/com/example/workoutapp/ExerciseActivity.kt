package com.example.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.workoutapp.databinding.ActivityExerciseBinding
import com.example.workoutapp.databinding.CustomDialogBackBinding

import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding?=null
    private var resttimer: CountDownTimer?=null
    private var restProgress =0
    private var exercisetimer: CountDownTimer? = null
    private var exerciseprogress=0
    private var exerciselist: ArrayList<exercise>?=null
    private var currentposi=-1
    private var tts: TextToSpeech?=null
    private var player: MediaPlayer?=null

    private var exerciseAdapter: ExerciseStatusAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.exctool)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
            binding?.exctool?.setNavigationOnClickListener{
           onCustomdialog()
       }
        tts= TextToSpeech(this,this)

      exerciselist= constants.defaultExerciseList()
      setuprestview()
        setupExerciseStatusRecyclerView()
    }
    private fun onCustomdialog(){
        val customDialog =Dialog(this)
        val dialogbinding= CustomDialogBackBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogbinding.root)
        dialogbinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogbinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
          onCustomdialog()
    }
    private fun setupExerciseStatusRecyclerView(){
        binding?.recyclerView?.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciselist!!)
        binding?.recyclerView?.adapter=exerciseAdapter
    }
    private fun setupexerciseview(){
        binding?.flprogress?.visibility=View.INVISIBLE
        binding?.tvexercise?.visibility= View.INVISIBLE
        binding?.upcomingexerciseName?.visibility=View.INVISIBLE
        binding?.upcomingexercise?.visibility=View.INVISIBLE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flexercise?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        if(exercisetimer!=null){
            exercisetimer?.cancel()
            exerciseprogress=0
        }
        binding?.ivImage?.setImageResource(exerciselist!![currentposi].getImage())
        binding?.tvExerciseName?.text=exerciselist!![currentposi].getname()
        setupexerciseTimer()
    }
      private fun setuprestview(){


          binding?.flprogress?.visibility=View.VISIBLE
          binding?.tvexercise?.visibility= View.VISIBLE
          binding?.upcomingexerciseName?.visibility=View.VISIBLE
          binding?.upcomingexercise?.visibility=View.VISIBLE
          binding?.tvExerciseName?.visibility=View.INVISIBLE
          binding?.flexercise?.visibility=View.INVISIBLE
          binding?.ivImage?.visibility=View.INVISIBLE
          if(resttimer!=null){
             resttimer?.cancel()
              restProgress=0
          }
          binding?.upcomingexerciseName?.text=exerciselist!![currentposi+1].getname()
        setuprestTimer()
      }
    fun setuprestTimer(){
        try{
            val soundURI = Uri.parse("android.resource://com.example.workoutapp/" + R.raw.buddy)
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping =false
            player?.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        binding?.progressbar?.progress=restProgress


        resttimer= object: CountDownTimer(10000,1000){
            override fun onTick(timeleft: Long) {
                restProgress++
                binding?.progressbar?.progress=10-restProgress
                binding?.tvtimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentposi++

                exerciselist!![currentposi].setisselected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupexerciseview()
            }

        }.start()
    }
    fun setupexerciseTimer(){

        speakup(exerciselist!![currentposi].getname())
        binding?.exerciseprogressbar?.progress=exerciseprogress
        exercisetimer= object: CountDownTimer(30000,1000){
            override fun onTick(timeleft: Long) {
                exerciseprogress++
                binding?.exerciseprogressbar?.progress=30-exerciseprogress
                binding?.tvexeercisetimer?.text=(30-exerciseprogress).toString()
            }

            override fun onFinish() {


                if (currentposi < exerciselist?.size!! - 1)
                {   exerciselist!![currentposi].setisselected(false)

                    exerciselist!![currentposi].setisCompleted(true)

                    exerciseAdapter!!.notifyDataSetChanged()
                    setuprestview()

                }
                else {
                   finish()
                    val intent=Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
    override fun onInit(Status: Int) {
        if(Status== TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.ENGLISH)
            if(result== TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS","Language specified is not supported")

        }else{
            Log.e("TTS","Initialization Failed")
        }
    }
    private fun speakup(name: String){
        tts?.speak(name,TextToSpeech.QUEUE_FLUSH,null,"")
    }
    override fun onDestroy() {
        super.onDestroy()
        if(resttimer!=null)
        {
            resttimer?.cancel()
            restProgress=0
        }
        if(exercisetimer!=null){
            exercisetimer?.cancel()
            exerciseprogress=0
        }
        if(tts!=null)
        {   tts?.stop()
            tts?.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
        binding=null
    }


}