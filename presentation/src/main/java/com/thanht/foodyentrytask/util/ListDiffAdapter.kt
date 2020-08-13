package com.thanht.foodyentrytask.util

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

abstract class ListDiffAdapter<T, H : RecyclerView.ViewHolder> : RecyclerView.Adapter<H>() {

    protected val data = mutableListOf<T>()

    private lateinit var recyclerView: RecyclerView
    private var job: Job? = null
    private val handlerException = CoroutineExceptionHandler { _, throwable ->
        Log.e("ListDiffAdapter", "Exception: ${throwable.localizedMessage}")
    }

   private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job?.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = data.size

    fun submitList(newList: List<T>, currentPos: Int = RecyclerView.NO_POSITION) {
        job?.cancel()
        job = coroutineScope.launch(handlerException) {
            when {
                data.isEmpty() -> {
                    withContext(Dispatchers.Main){
                        data.addAll(newList)
                        notifyItemRangeInserted(0, newList.size)
                    }
                }
                newList.isEmpty() -> {
                    withContext(Dispatchers.Main){
                        data.clear()
                        notifyDataSetChanged()
                    }
                }
                else -> {
                    val result = DiffUtil.calculateDiff(getDiffUtilCallback(data, newList))
                    withContext(Dispatchers.Main) {
                        data.clear()
                        data.addAll(newList)
                        result.dispatchUpdatesTo(this@ListDiffAdapter)
                        if (currentPos != RecyclerView.NO_POSITION) {
                            recyclerView.scrollToPosition(currentPos)
                        }
                    }
                }
            }
        }
    }

    abstract fun getDiffUtilCallback(oldList: List<T>, newList: List<T>): DiffUtil.Callback
}