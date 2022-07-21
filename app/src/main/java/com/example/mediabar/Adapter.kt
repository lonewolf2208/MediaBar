package com.example.mediabar

import android.annotation.SuppressLint
import android.content.Context
import android.util.Range
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mediabar.databinding.CardViewBinding
import com.google.android.material.slider.RangeSlider
import java.util.EnumSet.range


class Adapter(var data: ArrayList<Content>, var context: Context) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cardViewLecturesBinding: CardViewBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.card_view, parent, false
        )
        return ViewHolder(cardViewLecturesBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.seekBar.values = data[holder.adapterPosition].values
        holder.binding.start.text = data[holder.adapterPosition].values[0].toString()
        holder.binding.end.text = data[holder.adapterPosition].values[1].toString()
        holder.binding.seekBar.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {

            }


            override fun onStopTrackingTouch(slider: RangeSlider) {
                if (!(holder.adapterPosition == 0 && slider.focusedThumbIndex == 0)) {
                    if (holder.adapterPosition == data.size - 1) {
                        var newdata = listOf<Float>(slider.values[1], 100.0f)
                        data.add(holder.adapterPosition + 1, Content(newdata, "#0099cc"))
                        notifyItemInserted(holder.adapterPosition + 1)
                    } else if ((holder.adapterPosition == 0 || holder.adapterPosition==-1) && slider.values[1] == 0F) {
                        data.clear()
                        data.add(Content(listOf<Float>(0.0F, 100F), "#0099cc"))
                        notifyDataSetChanged()
                    } else if (slider.focusedThumbIndex == 0) {
                        if (slider.values[0] < data[holder.adapterPosition - 1].values[0]) {
                            for (i in 0..holder.adapterPosition - 1) {
                                if (i >= data.size) {
                                    break;
                                } else if (slider.values[0] <= data[i].values[0]) {
                                    data.removeAt(i)
                                    notifyItemRemoved(i)
                                }
                            }
                        } else {
                            var newdata =
                                listOf<Float>(
                                    data[holder.adapterPosition - 1].values[0],
                                    slider.values[0]
                                )
                            data[holder.adapterPosition].values =
                                listOf<Float>(
                                    slider.values[0],
                                    data[holder.adapterPosition].values[1]
                                )
                            notifyItemChanged(holder.adapterPosition)
                            data[holder.adapterPosition - 1].values = newdata
                            notifyItemChanged(holder.adapterPosition - 1)

                        }
                    } else if (slider.focusedThumbIndex == 1) {
//                        Toast.makeText(context, holder.adapterPosition.toString(), Toast.LENGTH_SHORT).show()
                        if (slider.values[1] < data[holder.adapterPosition + 1].values[1]) {
                            var newdata = Content(
                                listOf<Float>(
                                    slider.values[1],
                                    data[holder.adapterPosition + 1].values[1]
                                ), "#0099cc"
                            )
                            data[holder.adapterPosition].values =
                                listOf<Float>(
                                    data[holder.adapterPosition].values[0],
                                    slider.values[1]
                                )
                            notifyItemChanged(holder.adapterPosition)
                            data[holder.adapterPosition + 1] = newdata
                            notifyItemChanged(holder.adapterPosition + 1)
                        }
                        else {
                            for (i in holder.adapterPosition + 1..data.size) {
                                if (i >= data.size) {
                                    break;
                                } else if (slider.values[1] >= data[i].values[1]) {
                                    data.removeAt(i)
                                    notifyItemRemoved(i)
                                }
                            }
                        }
                    }
//                                        else {
//                        for (i in holder.adapterPosition + 1..data.size) {
//                            if (i >= data.size) {
//                                break;
//                            } else if (slider.values[1] >= data[i].values[1]) {
//                                data.removeAt(i)
//                                notifyItemRemoved(i)
//                            }
//                        }
//                    }

                }
            }
        })
        holder.binding.seekBar.addOnChangeListener(object : RangeSlider.OnChangeListener {
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                holder.binding.start.text = slider.values[0].toString()
                holder.binding.end.text = slider.values[1].toString()
            }
        })

    }


    override fun getItemCount(): Int {
        return data.size
    }
}