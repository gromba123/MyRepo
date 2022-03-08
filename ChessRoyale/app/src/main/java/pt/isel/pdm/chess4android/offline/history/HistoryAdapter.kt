package pt.isel.pdm.chess4android.offline.history

import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleHistoryDTO
import java.sql.Date

/**
 * Class that represents an element of the RecyclerView
 */
class HistoryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val dateView = itemView.findViewById<TextView>(R.id.date)
    private val stateView = itemView.findViewById<TextView>(R.id.state)

    fun bindTo(puzzle: PuzzleHistoryDTO, onItemCLick: () -> Unit) {
        dateView.text = Date(puzzle.timestamp.toLong()).toString()
        stateView.setText(convertState(puzzle.state))

        itemView.setOnClickListener {
            itemView.isClickable = false
            startAnimation {
                onItemCLick()
                itemView.isClickable = true
            }
        }
    }

    /**
     * Converts the received state to the respective string
     */
    private fun convertState(state: Int): Int =
        when(state) {
            0 -> R.string.unsolved
            1 -> R.string.solving
            else -> R.string.solved
        }

    private fun startAnimation(onAnimationEnd: () -> Unit) {

        val animation = ValueAnimator.ofArgb(
            ContextCompat.getColor(itemView.context, R.color.list_item_background),
            ContextCompat.getColor(itemView.context, R.color.list_item_background_selected),
            ContextCompat.getColor(itemView.context, R.color.list_item_background)
        )

        animation.addUpdateListener { animator ->
            val background = itemView.background as GradientDrawable
            background.setColor(animator.animatedValue as Int)
        }

        animation.duration = 400
        animation.doOnEnd { onAnimationEnd() }

        animation.start()
    }
}

/**
 * Adapts a List of [PuzzleHistoryDTO] into elements of a RecyclerView
 */
class HistoryAdapter(
    private val source: List<PuzzleHistoryDTO>,
    private val onItemCLick: (PuzzleHistoryDTO) -> Unit
) : RecyclerView.Adapter<HistoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_history_view, parent, false)
        return HistoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bindTo(source[position]) {
            onItemCLick(source[position])
        }
    }

    override fun getItemCount(): Int = source.size
}