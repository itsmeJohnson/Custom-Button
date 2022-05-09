package com.example.custombutton

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import androidx.core.content.ContextCompat

class CustomButton:androidx.appcompat.widget.AppCompatButton {

    private var buttonWidth = 0
    private var buttonHeight = 0

    private var buttonTextColor = 0
    private var buttonBackgroundColor = 0
    private var buttonPressedColor = 0
    private var buttonBorderColor = 0

    private var buttonType = 0
    private var buttonShape = 0

    private lateinit var gradientDrawable: GradientDrawable

    constructor(context: Context):super(context)

    constructor(context: Context, attrs:AttributeSet):super(context, attrs){
        init(context,attrs)
    }



    constructor(context: Context,attrs: AttributeSet, defStyleAttrs: Int):super(context, attrs,defStyleAttrs){
        init(context,attrs)
    }
    private fun init(context: Context, attrs: AttributeSet) {
        if(isInEditMode){
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomButton)
        buttonTextColor = typedArray.getColor(R.styleable.CustomButton_button_text_color,ContextCompat.getColor(context,R.color.default_text_color))

        buttonBackgroundColor = typedArray.getColor(
            R.styleable.CustomButton_button_background_color,
            ContextCompat.getColor(context,R.color.default_background_color)
        )

        buttonPressedColor = typedArray.getColor(
            R.styleable.CustomButton_button_pressed_color,
            ContextCompat.getColor(context,R.color.default_pressed_color)
        )
        buttonBorderColor = typedArray.getColor(
            R.styleable.CustomButton_button_border_color,
            ContextCompat.getColor(context,R.color.default_border_color)
        )
        buttonType = typedArray.getInt(R.styleable.CustomButton_button_type,0)
        buttonShape = typedArray.getInt(R.styleable.CustomButton_button_shape,0)

        typedArray.recycle()

        this.isClickable = true
        this.isAllCaps = false

        this.setTextColor(buttonTextColor)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        buttonWidth = w
        buttonHeight = h

        createDrawble()
        this.background = gradientDrawable
    }

    private fun createDrawble() {
        gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE

        when(buttonShape){
            //Rectangle
            0->{
                gradientDrawable.cornerRadius = 0F
            }
            //Curved
            1->{
                gradientDrawable.cornerRadius = buttonHeight.toFloat() / 2
            }
            //Fillet
            2->{
                gradientDrawable.cornerRadius = 8F
            }
        }
        updateButtonColor()
    }

    private fun updateButtonColor(pressed:Boolean = false) {
        when(buttonType){
            //Solid
            0->{
                if(pressed){
                    gradientDrawable.setColor(buttonPressedColor)
                }else{
                    gradientDrawable.setColor(buttonBackgroundColor)
                }
            }
            //Outline
            1->{
                gradientDrawable.setColor(ContextCompat.getColor(context,R.color.transparent))
                if(pressed){
                    gradientDrawable.setStroke(3,buttonPressedColor)
                }else{
                    gradientDrawable.setStroke(3,buttonBackgroundColor)
                }
            }

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        when(event?.action) {
            MotionEvent.ACTION_DOWN->{
                updateButtonColor(true)
                return true
            }
            MotionEvent.ACTION_UP-> {
                updateButtonColor()
                performClick()
                return true
            }
        }
        return false
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}