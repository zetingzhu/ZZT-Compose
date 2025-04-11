package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zzt.adapter.StartActivityRecyclerAdapter
import com.zzt.compose.databinding.ActivityMainBinding
import com.zzt.entity.StartActivityDao

class ComposeLayoutAct : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ComposeLayoutAct::class.java)
            context.startActivity(starter)
        }
    }

    var viewBinging: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinging?.root)
        initView()
    }

    fun addBundle(index: String): Bundle {
        var bundle = Bundle()
        bundle.putString("index", index)
        return bundle;
    }

    private fun initView() {
        var dataList = arrayListOf<StartActivityDao>();

        dataList.add(
            StartActivityDao(
                "Layout 自定义",
                " ",
                ComposeLayoutV1::class.java,
                addBundle("1")
            )
        )

        viewBinging?.rvList?.apply {
            StartActivityRecyclerAdapter.setAdapterData(
                this,
                RecyclerView.VERTICAL,
                dataList
            ) { itemView, position, data ->
            }
        }
    }
}