package ru.thstdio.mypetopenweather.presentation.view.util

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.mypetopenweather.R

fun RecyclerView.addItemDecorationWithoutLastDivider() {


    if (layoutManager !is LinearLayoutManager)
        return

    addItemDecoration(object :
        DividerItemDecoration(context, (layoutManager as LinearLayoutManager).orientation) {
        var mDivider: Drawable? = ContextCompat.getDrawable(
            context,
            R.drawable.shape_decorator
        )

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            mDivider?.let { mDivider ->
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight
                val childCount = state.itemCount
                for (i in 0 until childCount - 1) {
                    val child = parent.getChildAt(i)
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + params.bottomMargin
                    val bottom = top + mDivider.intrinsicHeight
                    mDivider.setBounds(left, top, right, bottom)
                    mDivider.draw(c)
                }
            }
        }
    })
}
