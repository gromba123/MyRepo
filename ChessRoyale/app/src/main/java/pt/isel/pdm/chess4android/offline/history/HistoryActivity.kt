package pt.isel.pdm.chess4android.offline.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import pt.isel.pdm.chess4android.databinding.ActivityHistoryBinding
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleActivity
import pt.isel.pdm.chess4android.offline.puzzle.SolvedActivity

const val PUZZLE_EXTRA = "HistoryActivity.Extra.Puzzle"

/**
 * Activity to the Puzzle history
 */
class HistoryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<HistoryActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.puzzleList.layoutManager = LinearLayoutManager(this)

        (viewModel.history).observe(this) {
            binding.puzzleList.adapter = HistoryAdapter(it) { puzzle ->
                viewModel.loadPuzzle(puzzle).observe(this) { dto ->
                    if (dto != null) {
                        if (puzzle.state != 2)
                            startActivity(PuzzleActivity.buildIntent(this, dto))
                        else startActivity(SolvedActivity.buildIntent(this, dto))
                    }
                }
            }
        }
    }
}