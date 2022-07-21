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


class Adapter(var data: ArrayList<Content>, var context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>(){
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

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.binding.seekBar.values = data[position].values
        holder.binding.start.text=data[position].values[0].toString()
        holder.binding.end.text=data[position].values[1].toString()
        holder.binding.seekBar.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {

            }


            override fun onStopTrackingTouch(slider: RangeSlider) {
                    if(position==0 && slider.values[1]==0F)
                    {
                        data.clear()
                        data.add(Content(listOf<Float>(0.0F,100F),"#0099cc"))
                        notifyDataSetChanged()
                    }
                    if(position==data.size-1)
                    {
                        var newdata= listOf<Float>(slider.values[1],100.0f)
                        data.add(position+1, Content(newdata,"#0099cc"))
                        notifyItemInserted(position+1)
                    }
                    else if(slider.focusedThumbIndex==0)
                    {
                        if(slider.values[0]<data[position-1].values[0])
                        {
                            for(i in 0..position-1)
                            {
                                if(i>=data.size)
                                {
                                    break;
                                }
                                else if(slider.values[0]<data[i].values[0])
                                {
                                    data.removeAt(i)
                                    notifyItemRemoved(i)
                                }
                            }
                        }
                        else {
                            var newdata =
                                listOf<Float>(data[position - 1].values[0], slider.values[0])
                            data[position].values= listOf<Float>(slider.values[0],data[position].values[1])
                            data[position - 1].values = newdata
                            notifyItemChanged(position - 1)
                        }
                    }
                    else if(slider.focusedThumbIndex==1)
                    {
                        if(position==data.size-1)
                        {
                            var newdata= listOf<Float>(slider.values[1],100.0f)
                            data.add(position+1, Content(newdata,"#0099cc"))
                            notifyItemInserted(position+1)
                        }
                        if(slider.values[1]<data[position+1].values[1] ) {
                            var newdata =Content(
                                listOf<Float>(slider.values[1], data[position + 1].values[1]),"#0099cc")
                            data[position].values= listOf<Float>(data[position].values[0],slider.values[1])
                            data[position + 1]=newdata
                            notifyItemChanged(position + 1)
                        }
                    }
                else
                    {
                        for(i in position+1..data.size)
                        {
                            if(i>=data.size)
                            {
                                break;
                            }
                            else if(slider.values[1]>=data[i].values[1])
                            {
                                data.removeAt(i)
                                notifyItemRemoved(i)
                            }
                        }
                    }

            }

        })
        holder.binding.seekBar.addOnChangeListener(object : RangeSlider.OnChangeListener {
            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                holder.binding.start.text=slider.values[0].toString()
                holder.binding.end.text=slider.values[1].toString()
            }
        })

    }


    override fun getItemCount(): Int {
        return data.size
    }
}