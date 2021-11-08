package com.fruktoland.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.data.persistence.items.BasketItem
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderAdapter @Inject constructor() : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    var orderList: List<BasketItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.OrderViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.order_recyclerview_item, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount() = orderList.size

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val decFormatter = DecimalFormat("#,###.##")
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtVTotal: TextView = itemView.findViewById(R.id.txtVTotal)
        var txtVUnit: TextView = itemView.findViewById(R.id.txtVUnit)
        var etxtQtty: EditText = itemView.findViewById(R.id.etxtQtty)

        fun bind(basketItem: BasketItem) {
            txtName.text = basketItem.name
            txtVUnit.text = basketItem.unit
            txtVTotal.text = (basketItem.price * basketItem.qtty).toString()
            etxtQtty.setText(basketItem.qtty.toString())
            etxtQtty.doOnTextChanged { text, start, before, count ->
                when {
                    text.isNullOrEmpty() -> {

                    }
                    else -> {
                        basketItem.qtty = text.toString().toDouble()
                        txtVTotal.text = decFormatter.format(basketItem.price * basketItem.qtty)
                    }
                }
            }
        }
    }
}