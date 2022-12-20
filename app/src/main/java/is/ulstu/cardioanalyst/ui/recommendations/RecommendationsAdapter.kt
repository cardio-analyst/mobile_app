package `is`.ulstu.cardioanalyst.ui.recommendations

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/*
class RecommendationsAdapter(
    context: Context,
    private val recommendationsList: List<GetRecommendationsResponseEntity>
) : ArrayAdapter<GetRecommendationsResponseEntity>(context, R.layout.recommendation_view, recommendationsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = if (convertView == null) RecommendationView(context) else convertView as RecommendationView
        view.setParams(recommendationsList[position])
        return view
    }
}*/

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
