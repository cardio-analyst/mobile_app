package `is`.ulstu.cardioanalyst.ui.basic_indicators

import `is`.ulstu.foundation.ARG_SCREEN
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * Adapter for [ViewPager2] on [BasicIndicatorsAdapter] tab
 * contains list of [BasicIndicatorsRecordFragment]
 * based on [FragmentStateAdapter]
 * @param fragmentManager [FragmentManager]
 * @param lifecycle [Lifecycle]
 */
class BasicIndicatorsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: MutableList<BasicIndicatorsRecordFragment> = mutableListOf()

    fun addFragment(basicIndicatorsRecordFragment: BasicIndicatorsRecordFragment) {
        val screen = BasicIndicatorsRecordFragment.Screen()
        basicIndicatorsRecordFragment.arguments = bundleOf(ARG_SCREEN to screen)
        fragmentList.add(basicIndicatorsRecordFragment)
    }

    fun getFragment(position: Int) = fragmentList[position]

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}