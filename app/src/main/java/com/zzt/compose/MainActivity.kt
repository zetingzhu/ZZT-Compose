package com.zzt.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zzt.adapter.StartActivityRecyclerAdapter
import com.zzt.compose.act.*
import com.zzt.compose.databinding.ActivityMainBinding
import com.zzt.entity.StartActivityDao

/**
 *
 */
class MainActivity : AppCompatActivity() {
    var viewBinging: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinging?.root)
        initView()
    }

    fun initView() {
        var dataList = arrayListOf<StartActivityDao>();
        dataList.add(StartActivityDao(1L, "Compost text", "这个是文本的使用和各种api"))
        dataList.add(StartActivityDao(2L, "Compost Button", ""))
        dataList.add(StartActivityDao(3L, "Compost Scaffold ", ""))
        dataList.add(StartActivityDao(4L, "Compost Scaffold message", ""))
        dataList.add(StartActivityDao(5, "Compost Drawer", ""))
        dataList.add(StartActivityDao(6, "Compost Layout", "这玩意完全没看明白"))
        dataList.add(StartActivityDao(10, "Compost Layout2", "自定义布局"))
        dataList.add(StartActivityDao(12, "Compost Layout3", "自定义网格列表"))
        dataList.add(StartActivityDao(13, "Compost Layout4", "自定义布局了解修饰符"))
        dataList.add(StartActivityDao(7, "AlignmentLine", "自定义对齐线"))
        dataList.add(StartActivityDao(8, "列表", "列表"))
        dataList.add(StartActivityDao(9, "列表2", "列表"))
        dataList.add(StartActivityDao(14, "ConstraintLayout ", ""))
        dataList.add(StartActivityDao(15, "ConstraintLayout 2", ""))
        dataList.add(StartActivityDao(16, "Nav v1", "底部导航栏"))

        dataList.add(getStart("compost 默认初始化", "1"))
        dataList.add(getStart("compost v2", "2"))
        dataList.add(getStart("compost 创建两个TextView", "3"))
        dataList.add(getStart("compost 创建列表两个TextView 纵向", "4"))
        dataList.add(getStart("compost 随意布局3", "5"))
        dataList.add(getStart("compost 随意布局4", "6"))
        dataList.add(getStart("compost 随意布局5", "7"))
        dataList.add(getStart("compost Box 使用", "8"))
        dataList.add(getStart("compost weight 使用", "9"))
        dataList.add(getStart("compost 点击手势", "10"))
        dataList.add(getStart("compost ConstraintLayout 约束布局 ", "11"))


        viewBinging?.rvList?.apply {
            StartActivityRecyclerAdapter.setAdapterData(
                this,
                RecyclerView.VERTICAL,
                dataList
            ) { itemView, position, data ->
                when (data.id) {
                    1L -> {
                        ComposeActText.start(baseContext)
                    }

                    2L -> {
                        ComposeActV2.start(baseContext)
                    }

                    3L -> {
                        ComposeScaffoldV1.start(baseContext)
                    }

                    4L -> {
                        ComposeScaffoldV2.start(baseContext)
                    }

                    5L -> {
                        ComposeDrawerV1.start(baseContext)
                    }

                    6L -> {
                        ComposeLayoutV1.start(baseContext)
                    }

                    7L -> {
                        ComposeAlignmentLineV1.start(baseContext)
                    }

                    8L -> {
                        ComposeListV1.start(baseContext)
                    }

                    9L -> {
                        ComposeListV2.start(baseContext)
                    }

                    10L -> {
                        ComposeLayoutV2.start(baseContext)
                    }


                    12L -> {
                        ComposeLayoutV3.start(baseContext)
                    }

                    13L -> {
                        ComposeLayoutV4.start(baseContext)
                    }

                    14L -> {
                        ComposeConstraintV1.start(baseContext)
                    }

                    15L -> {
                        ComposeConstraintV2.start(baseContext)
                    }

                    16L -> {
                        ComposeNavV1.start(baseContext)
                    }
                }
            }
        }
    }

    fun getStart(title: String, type: String): StartActivityDao {
        return StartActivityDao(title, "", ActivityCompostMain::class.java, getBundle(type))
    }

    private fun getBundle(type: String): Bundle {
        var bundle = Bundle()
        bundle.putString("type", type)
        return bundle
    }
}