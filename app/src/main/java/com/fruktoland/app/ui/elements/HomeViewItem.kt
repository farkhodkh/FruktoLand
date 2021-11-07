package com.fruktoland.app.ui.elements

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.fruktoland.app.R

class HomeViewItem(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.home_view_item, this, true)

        attrs?.let {
            val typedArray: TypedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.HomeViewItem)

            val imageIcon = view.findViewById<ImageView>(R.id.image_icon)
            val imageTextView = view.findViewById<TextView>(R.id.image_text)
            val imageTextViewDescription = view.findViewById<TextView>(R.id.image_text_description)

            val imageIconId = typedArray.getResourceId(R.styleable.HomeViewItem_imageIcon, 0)
            if (imageIconId > 0)
                imageIcon.setImageResource(imageIconId)

            val imageIconBackground =
                typedArray.getResourceId(R.styleable.HomeViewItem_imageIconBackground, 0)
            if (imageIconBackground > 0)
                imageIcon.setBackgroundResource(imageIconBackground)

            val textColorId =
                typedArray.getResourceId(R.styleable.HomeViewItem_android_textColor, 0)
            if (textColorId > 0)
                imageTextView.setTextColor(resources.getColor(textColorId))

            val imageText = typedArray.getString(R.styleable.HomeViewItem_imageText)
            imageText?.let {
                imageTextView.text = it
            }

            val imageTextDescription =
                typedArray.getString(R.styleable.HomeViewItem_imageTextDescription)
            imageTextDescription?.let {
                imageTextViewDescription.text = it
            }

            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = 160
        val desiredHeight = 220

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width

        //Measure Width
        width = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height

        //Measure Height
        height = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS

        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }
}