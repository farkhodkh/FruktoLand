package com.fruktoland.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fruktoland.app.R
import com.fruktoland.app.data.persistence.items.BasketItem
import com.fruktoland.app.data.persistence.model.toBasketModule
import com.fruktoland.app.ui.view.DataBaseInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

@Singleton
class OrderAdapter @Inject constructor(
    private var mainInteractor: DataBaseInteractor
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val _totalSumm = MutableStateFlow("0.00 р.")
    val totalSumm: StateFlow<String> = _totalSumm

    val decFormatter = DecimalFormat("#,###.##")

    var orderList: List<BasketItem> = emptyList()
    set(value) {
        field = value
        _totalSumm.tryEmit(decFormatter.format(getSumm()) + " р.")
    }

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

    fun getSumm(): Double = orderList.sumOf { it.price * it.qtty }

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtVTotal: TextView = itemView.findViewById(R.id.txtVTotal)
        var txtVUnit: TextView = itemView.findViewById(R.id.txtVUnit)
        var txtViewQtty: TextView = itemView.findViewById(R.id.txtViewQtty)
        var btnMinus: ImageButton = itemView.findViewById(R.id.btnMinus)
        var btnPlus: ImageButton = itemView.findViewById(R.id.btnPlus)

        fun bind(basketItem: BasketItem) {
            txtName.text = basketItem.name
            txtVUnit.text = basketItem.unit
            txtVTotal.text = (basketItem.price * basketItem.qtty).toString()
            txtViewQtty.text = basketItem.qtty.toString()

            btnPlus.setOnClickListener {
                basketItem.qtty = basketItem.qtty + 1.0
                txtVTotal.text = decFormatter.format(basketItem.price * basketItem.qtty)

                txtViewQtty.text = basketItem.qtty.toString()
                _totalSumm.tryEmit(decFormatter.format(getSumm()) + " р.")

                mainInteractor.addToBasket(basketItem)
            }

            btnMinus.setOnClickListener {
                basketItem.qtty = maxOf(basketItem.qtty - 1.0, 0.0)
                txtVTotal.text = decFormatter.format(basketItem.price * basketItem.qtty)

                txtViewQtty.text = basketItem.qtty.toString()
                _totalSumm.tryEmit(decFormatter.format(getSumm()) + " р.")

                mainInteractor.addToBasket(basketItem)
            }
        }
    }
}