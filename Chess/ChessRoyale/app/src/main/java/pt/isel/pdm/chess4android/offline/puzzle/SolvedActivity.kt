package pt.isel.pdm.chess4android.offline.puzzle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.databinding.ActivitySolvedBinding
import pt.isel.pdm.chess4android.offline.history.PUZZLE_EXTRA

/**
 * Activity for the solved puzzle activity
 */
class SolvedActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(origin: Activity, puzzle: PuzzleDTO): Intent {
            val intent = Intent(origin, SolvedActivity::class.java)
            intent.putExtra(PUZZLE_EXTRA, puzzle)
            return intent
        }
    }

    private val binding by lazy { ActivitySolvedBinding.inflate(layoutInflater) }

    private val viewModel: SolvedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val puzzle = intent.getParcelableExtra<PuzzleDTO>(PUZZLE_EXTRA)
        if (puzzle != null) viewModel.buildPuzzle(puzzle)

        viewModel.board.observe(this) {
            binding.boardPuzzle.drawBoard(it.board)
        }

        binding.show.setOnClickListener {
            if (binding.show.text.toString() == resources.getString(R.string.puzzle)) {
                viewModel.loadPuzzle()
                binding.show.text = resources.getString(R.string.solution)
            }
            else {
                viewModel.loadSolution()
                binding.show.text = resources.getString(R.string.puzzle)
            }
        }

        binding.retry.setOnClickListener {
            if (puzzle != null)
                startActivity(PuzzleActivity.buildIntent(this, puzzle))
        }
    }
}