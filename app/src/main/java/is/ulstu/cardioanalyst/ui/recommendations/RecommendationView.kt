package `is`.ulstu.cardioanalyst.ui.recommendations

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.RecommendationViewBinding

class RecommendationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: RecommendationViewBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.recommendation_view, this, true)
        binding = RecommendationViewBinding.bind(this)
        binding.root.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    /**
     * Params for view set method: title, whyDescription, recommendation
     */
    fun setParams(getRecommendationsResponseEntity: GetRecommendationsResponseEntity) {
        with(binding) {
            nameTextView.text = getRecommendationsResponseEntity.what
            whyTextView.text = getRecommendationsResponseEntity.why
            recommendationTextView.text = getRecommendationsResponseEntity.how
        }
    }

}