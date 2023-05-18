package com.example.recommendations.presentation

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recommendations.domain.entities.GetRecommendationsResponseEntity

class RecommendationsAdapter(
    private val context: Context,
    private val recommendationsList: List<GetRecommendationsResponseEntity>
) : RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {

    class RecommendationsViewHolder(itemView: RecommendationView) :
        RecyclerView.ViewHolder(itemView) {
        fun setParams(getRecommendationsResponseEntity: GetRecommendationsResponseEntity) =
            (itemView as RecommendationView).setParams(getRecommendationsResponseEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder =
        RecommendationsViewHolder(RecommendationView(context))

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        holder.setParams(recommendationsList[position])
    }

    override fun getItemCount(): Int = recommendationsList.size

}
