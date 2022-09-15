package pt.isel.pdm.chess4android.offline.puzzle

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.databinding.ActivityPuzzleBinding
import pt.isel.pdm.chess4android.offline.history.PUZZLE_EXTRA
import pt.isel.pdm.chess4android.views.OnTileTouchListener

fun View.myPostDelayed(delayInMillis: Long, runnable: () -> Unit) {
    this.postDelayed(runnable, delayInMillis)
}

/**
 * Activity to the Puzzle solving
 */
class PuzzleActivity : AppCompatActivity() {

    companion object {
        fun buildIntent(origin: Activity, puzzle: PuzzleDTO): Intent {
            val intent = Intent(origin, PuzzleActivity::class.java)
            intent.putExtra(PUZZLE_EXTRA, puzzle)
            return intent
        }
    }

    private val binding by lazy {
        ActivityPuzzleBinding.inflate(layoutInflater)
    }

    private val viewModel: PuzzleActivityViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.finishPhrase.visibility = View.INVISIBLE

        val puzzleDTO = intent.getParcelableExtra<PuzzleDTO>(PUZZLE_EXTRA)
        if (puzzleDTO != null) viewModel.initPuzzle(puzzleDTO)

        viewModel.board.observe(this) {
            binding.boardPuzzle.drawBoard(it.board)
        }

        viewModel.paint.observe(this) {
            it?.list?.forEach { pos ->
                binding.boardPuzzle.drawSelected(pos, it.type)
            }
        }

        viewModel.puzzleState.observe(this) {
            binding.finishPhrase.visibility = View.VISIBLE
            binding.restart.isEnabled = false
            binding.root.myPostDelayed(5000) {
                //startActivity(Intent(this, CreditsActivity::class.java))
            }
        }

        binding.boardPuzzle.setListener(object : OnTileTouchListener {
            override fun onClick(x: Int, y: Int): Boolean {
                viewModel.movePiece(x, y)
                return true
            }
        })

        binding.restart.setOnClickListener {
            if (puzzleDTO != null) viewModel.restart(puzzleDTO)
        }
    }
}