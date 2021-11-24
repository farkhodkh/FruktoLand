package com.fruktoland.app.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.common.Const
import com.fruktoland.app.data.persistence.items.CatalogHolderItem
import com.fruktoland.app.ui.elements.HomeViewItem
import com.fruktoland.app.ui.view.ModuleInteractor
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CatalogAdapter @Inject constructor(
    var interactor: ModuleInteractor
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private val picasso = Picasso.get()
    var catalogList: List<CatalogHolderItem> = emptyList()
    lateinit var onClickAction: (String, Long) -> Unit

    val backgroundColors = mapOf(
        0 to R.drawable.cercle_background_blue,
        1 to R.drawable.cercle_background_brown,
        2 to R.drawable.cercle_background_gray,
        3 to R.drawable.cercle_background_pink,
        4 to R.drawable.cercle_background_purple,
        5 to R.drawable.cercle_background_red,
        6 to R.drawable.cercle_background_white,
        7 to R.drawable.cercle_background_yellow
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogAdapter.CatalogViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.catalog_recyclerview_item, parent, false)
        return CatalogViewHolder(itemView)
    }

    override fun getItemCount(): Int = catalogList.size

    override fun onBindViewHolder(holderItem: CatalogAdapter.CatalogViewHolder, position: Int) {
        holderItem.bind(catalogList[position])
    }

    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageIcon = itemView.findViewById<ImageView>(R.id.image_icon)
        val imageTextView = itemView.findViewById<TextView>(R.id.image_text)
        val imageTextViewDescription = itemView.findViewById<TextView>(R.id.image_text_description)

        fun bind(catalogItem: CatalogHolderItem) {
            if (!catalogItem.imageAddress.isEmpty()) {
                picasso.load("${Const.BASE_URL}${catalogItem.imageAddress}").into(imageIcon)
            } else {
                imageIcon.setImageResource(R.drawable.ic_no_phote)
            }

            val imageIconBackground = getRandomBackground()
            imageIcon.setBackgroundResource(imageIconBackground)

            imageTextView.text = catalogItem.name
            imageTextViewDescription.text = catalogItem.catalogDescription

            itemView.setOnClickListener { onClickAction(catalogItem.name, catalogItem.id) }
        }
    }

    fun getRandomBackground(): Int = backgroundColors.get((0..7).random()) ?: R.drawable.cercle_background_white
}

