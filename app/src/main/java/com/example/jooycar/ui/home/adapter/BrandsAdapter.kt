package com.example.jooycar.ui.home.adapter

import android.content.Context
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jooycar.R
import com.example.jooycar.data.model.response.BrandsResponse
import com.example.jooycar.data.model.response.ModelsResponse
import com.example.jooycar.databinding.BrandListItemBinding
import com.example.jooycar.databinding.ModelsItemBinding


class BrandsAdapter(
    private val arrayList: MutableList<BrandsResponse> = arrayListOf(),
    private val context: Context
) : RecyclerView.Adapter<BrandsAdapter.BrandHolder>() {

    var onItemClick: ((BrandsResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandHolder {
        val rootView = BrandListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BrandHolder(rootView)
    }

    override fun onBindViewHolder(holder: BrandHolder, position: Int) {
        holder.bind(arrayList[position], context)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(arrayList[position])
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class BrandHolder(val view: BrandListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(brand: BrandsResponse?, context: Context) {
            view.tvId.text = brand?.id
            view.tvName.text = "Marca. ${brand?.name}"
            if(brand?.models?.isEmpty() == true){
                view.imgDownLottie.visibility = GONE
            }else{
                view.imgDownLottie.visibility = VISIBLE
                brand?.models?.let {
                    for(model in it){
                        val modelView = ModelsItemBinding.inflate(
                            LayoutInflater.from(context), null, false
                        )
                        modelView.tvId.text = model.id
                        modelView.tvName.text = "Model. ${model.name}"
                        view.lnModels.addView(modelView.root)
                    }
                }
            }
            val imageUrl: String = brand?.img ?: ""
            if(imageUrl.isEmpty()){
                Glide.with(context)
                    .load(R.drawable.jooycar)
                    .into(view.imgCar)
            }else{
                Glide.with(context)
                    .load(imageUrl)
                    .into(view.imgCar)
            }
            view.imgDownLottie.setOnClickListener{
                if(view.lnModels.isVisible){
                    val transition: Transition = Fade()
                    transition.duration = 600
                    transition.addTarget(view.lnModels)
                    TransitionManager.beginDelayedTransition(view.root,transition)
                    view.lnModels.visibility = GONE
                }else{
                    val transition: Transition = Fade()
                    transition.duration = 600
                    transition.addTarget(view.lnModels)
                    TransitionManager.beginDelayedTransition(view.root,transition)
                    view.lnModels.visibility = VISIBLE
                }
            }
        }
    }


    fun addList(list: MutableList<BrandsResponse> = arrayListOf()) {
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    fun addModels(modelsResponse: MutableList<ModelsResponse> = arrayListOf()) {
        arrayList.find { it.id == modelsResponse.first().brandId }?.let { brandsResponse->
            brandsResponse.models = modelsResponse
        }
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: BrandHolder) {
        holder.setIsRecyclable(false)
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: BrandHolder) {
        holder.setIsRecyclable(false)
        super.onViewDetachedFromWindow(holder)
    }
}