package com.zzt.compose.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zzt.compose.fragment.BaseFragment

/**
 * @author: zeting
 * @date: 2022/6/27
 */
open class BaseFragment : Fragment() {
    companion object {
        fun newInstance(args: Bundle?): BaseFragment {
            val fragment = BaseFragment()
            fragment.arguments = args
            return fragment
        }
    }
}