package com.example.zt_language.util


import android.app.Activity
import java.lang.ref.WeakReference
import java.util.Collections
import java.util.LinkedList

/**
 * @author: zeting
 * @date: 2025/6/27
 *
 * 管理语言更改后需要刷新的 Activity。
 */
object LanguageChangeManager {
    // 使用 WeakReference 防止内存泄漏
    private val activityRefs = Collections.synchronizedList(LinkedList<WeakReference<Activity>>())

    fun addActivity(activity: Activity) {
        // 避免重复添加
        if (activityRefs.none { it.get() == activity }) {
            activityRefs.add(WeakReference(activity))
        }
    }

    fun removeActivity(activity: Activity) {
        activityRefs.removeAll { it.get() == null || it.get() == activity }
    }

    /**
     * 当语言发生变化时调用此方法。
     * 这会尝试 recreate 所有已注册且仍然存活的 Activity。
     */
    fun onLanguageChanged() {
        val activitiesToRecreate = LinkedList<Activity>()
        // 创建副本以避免并发修改问题，并过滤掉已回收的 Activity
        synchronized(activityRefs) {
            val iterator = activityRefs.iterator()
            while (iterator.hasNext()) {
                val weakRef = iterator.next()
                val activity = weakRef.get()
                if (activity == null || activity.isFinishing || activity.isDestroyed) {
                    iterator.remove() // 清理无效的引用
                } else {
                    activitiesToRecreate.add(activity)
                }
            }
        }

        // 重新创建 Activity
        // 注意：直接在这里 recreate 可能会导致一些问题，
        // 特别是如果当前 Activity 正在进行某些操作。
        // 一个更安全的方式可能是设置一个标志，让 Activity 在 onResume 时检查。
        // 但如果目标是“立即”刷新所有（尽管会有视觉上的连续 recreate），可以这样做：
        for (activity in activitiesToRecreate) {
            if (!activity.isFinishing && !activity.isDestroyed) {
                // 确保 Activity 仍然是有效的
                activity.runOnUiThread { // 确保在UI线程执行
                    activity.recreate()
                }
            }
        }
    }
}