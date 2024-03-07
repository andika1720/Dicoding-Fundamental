package com.example.submissionakhir.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionakhir.ui.PageDetail

class AdapterSectionPagerForDetail(activity: FragmentActivity, private val appName: String): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PageDetail()
        fragment.arguments = Bundle().apply {
            putInt(PageDetail.ARG_SECTION_NUMBER, position +1)
            putString(PageDetail.ARG_NAME, appName)
        }
        return fragment
    }
}