package com.aralacikalin.gpacalculator

import android.app.ActionBar
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    var courseCount=0
    var courseCreditList= arrayListOf<EditText>()
    var courseGradeList= arrayListOf<Spinner>()
    var courses= arrayListOf<LinearLayout>()

    var gradeOptions= arrayOf("AA","BA","")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickAddButton(view: View){
        courseCount++

        var courseLayout =LinearLayout(ContextThemeWrapper(this, R.style.courseLayout))
        courseLayout.orientation=LinearLayout.HORIZONTAL
        courseLayout.gravity=Gravity.CENTER


        var courseText= EditText(ContextThemeWrapper(this, R.style.courseText))
        var courseCredit= EditText(ContextThemeWrapper(this, R.style.courseCredit))
        var courseSpinner =Spinner(ContextThemeWrapper(this, R.style.courseSpinner))

        var closeButton=ImageView(this)
        closeButton.setImageResource(R.drawable.icon)
        closeButton.setOnClickListener {v: View -> removeButton(v)  }



        val options=resources.getStringArray(R.array.gradeOptions)

        val spinnerAd=ArrayAdapter<String>(this,R.layout.spinner_lay,options)
        //spinnerAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAd.setDropDownViewResource(R.layout.spinner_drop)

        courseSpinner.adapter=spinnerAd

        courseText.setTextAppearance(R.style.courseCredit)
        courseCredit.setTextAppearance(R.style.courseCredit)

        /*
        courseCredit.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1f)
        courseText.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1f)
        courseSpinner.layoutParams=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1f)

         */

        courseCredit.layoutParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,2f)
        courseText.layoutParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,2f)
        courseSpinner.layoutParams=LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,2f)




        courseLayout.addView(courseText)
        courseLayout.addView(courseCredit)
        courseLayout.addView(courseSpinner)
        courseLayout.addView(closeButton,LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1f))
        courseContainer.addView(courseLayout,LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
        var param=LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        param.setMargins(20,10,20,10)
        courseLayout.layoutParams=param
        courses.add(courseLayout)
        courseCreditList.add(courseCredit)
        courseGradeList.add((courseSpinner))

    }
    fun onClickSubmitButton(view: View){
        //TODO ortalama alma matematiği burda
        //TODO credit * grade / total credits
       // Log.d("creation","Function call")
        var gpa=calculateGradeSum()/calculateCredits()
        header.text="%.2f".format(gpa).toString()

    }

    fun calculateCredits(): Double{
        var sum =0.0
        //for(edit in courseCreditList){
        courseCreditList.forEach {
            if (!it.text.isNullOrBlank()){
                sum+=it.text.toString().toFloat()
                Log.d("creation",it.text.toString())
            }}
        //Log.d("creation","count:"+courseCount.toString())

        return sum
    }
    fun calculateGradeSum(): Double{
        var sum=0.0
        var i=0
        while (i<courseCount){
            if(!courseCreditList[i].text.isNullOrBlank()){
                var grade=0.0
                when (courseGradeList[i].selectedItem.toString()){
                    "AA"->grade=4*courseCreditList[i].text.toString().toDouble()
                    "BA"->grade=3.5*courseCreditList[i].text.toString().toDouble()
                    "BB"->grade=3*courseCreditList[i].text.toString().toDouble()
                    "CB"->grade=2.5*courseCreditList[i].text.toString().toDouble()
                    "CC"->grade=2*courseCreditList[i].text.toString().toDouble()
                    "DC"->grade=1.5*courseCreditList[i].text.toString().toDouble()
                    "DD"->grade=1*courseCreditList[i].text.toString().toDouble()
                    "FF"->grade=0*courseCreditList[i].text.toString().toDouble()
                }
                sum+=grade

            }
            i++
        }

        return sum

    }

    fun removeButton(view: View){
        var i =0
        for (lay in courses){
            if (view.parent==lay){
                break
            }
            i++
        }
        courseGradeList.removeAt(i)
        courseCreditList.removeAt(i)
        courseContainer.removeViewAt(i)
        courses.removeAt(i)
        courseCount--
    }

}
